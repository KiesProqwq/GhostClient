package cn.KiesPro.module.blatant;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import cn.KiesPro.Client;
import cn.KiesPro.event.eventapi.EventTarget;
import cn.KiesPro.event.events.EventPreUpdate;
import cn.KiesPro.event.events.EventUpdate;
import cn.KiesPro.module.Category;
import cn.KiesPro.module.Module;
import cn.KiesPro.settings.Setting;
import cn.KiesPro.utils.MathUtils;
import cn.KiesPro.utils.RotationUtil;
import cn.KiesPro.utils.TimerUtils;
import cn.KiesPro.utils.raven.RavenUtils;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;

public class KillAura extends Module {
	
	//Setting
	//单数是Setting 复数是ArrayList
    ArrayList<String> modes = new ArrayList<String>();
    ArrayList<String> rotaions = new ArrayList<String>();
    
	public static Setting mode, rotaion;
	
    public static Setting range;
    public static Setting blockRange;
    public static Setting hitChance;
    public static Setting switchDelay;
    public static Setting maxCps;
    public static Setting minCps;
    
	public static Setting autoblock;
    public static Setting raytrace;
    public static Setting onClick;
    public static Setting swordOnly;
    public static Setting axeOnly;
    
    public static Setting player;
    public static Setting mobs;
    public static Setting animals;
    public static Setting invis;
    
    //KillAura
    private TimerUtils switchTimer = new TimerUtils();
    private TimerUtils attackTimer = new TimerUtils();
    
    public EntityLivingBase target;
    private List targets = new ArrayList();
    
    private int currentIndex;
    
    public static boolean isAttackTick;
    public static boolean isRotationTick;
    private boolean canBlock;
    private boolean isBlocking;
    
	public KillAura() {
		super("KillAura", "Attack people.", Category.BLATANT);
        modes.add("Switch");
        modes.add("Single");
        modes.add("Multi");
        registerSetting(mode = new Setting("Mode", this,"Switch",modes));
        
        rotaions.add("Blatant");
        rotaions.add("Smooth");
        rotaions.add("Legit");
        rotaions.add("None");
        registerSetting(rotaion = new Setting("Rotaions", this,"Blatant",rotaions));
        registerSetting(range =new Setting("Range", this, 4.20D, 1.00D, 6.00D, false));
        registerSetting(blockRange = new Setting("BlockRange", this, 5.00D, 1.00D, 10.00D, false));
        registerSetting(hitChance = new Setting("HitChance", this, 100.0D, 1.0D, 100.0D, true));
        registerSetting(switchDelay = new Setting("SwitchDelay", this, 200.0D, 1.0D, 1000.0D, true));
        registerSetting(maxCps = new Setting("MaxCPS", this, 10.0D, 2.0D, 20.0D, false));
        registerSetting(minCps = new Setting("MinCPS", this, 10.0D, 2.0D, 20.0D, false));
        registerSetting(autoblock = new Setting("Autoblock", this, false));
        registerSetting(raytrace = new Setting("Raytrace", this, false));
        registerSetting(onClick = new Setting("OnClick", this, false));
        registerSetting(swordOnly = new Setting("SwordOnly", this, false));
        registerSetting(axeOnly = new Setting("AxeOnly", this, false));
        
        registerSetting(player = new Setting("Players", this, false));
        registerSetting(mobs = new Setting("Mobs", this, false));
        registerSetting(animals = new Setting("Animals", this, false));
        registerSetting(invis = new Setting("Invisibles", this, false));
	}
	
    @EventTarget
	public void onPreUpdate(EventPreUpdate e) {
        isRotationTick = false;
        this.setTargets();

        if (!RavenUtils.isPlayerInGame())
            return;
        
            if (!onClick.getValBoolean() || mc.gameSettings.keyBindAttack.isKeyDown()) {
                this.isBlocking = mc.thePlayer.isUsingItem() && mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword;
                if (this.target != null) {

                    this.rotate(e);
                    double d0 = (double) mc.thePlayer.getDistanceToEntity(this.target);

                    if (d0 <= blockRange.getValDouble() && d0 <= range.getValDouble() && autoblock.getValBoolean() && mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) {
                        this.canBlock = true;
                    } else {
                        this.canBlock = false;
                    }

                    if (this.canBlock && !this.isBlocking) {
                        this.block();
                    }

                    if (d0 <= range.getValDouble() && attackTimer.hasReached(1000.0D / MathUtils.randomNumber(maxCps.getValDouble(), minCps.getValDouble()))) {
                    	
                        mc.thePlayer.swingItem();
                        if (ThreadLocalRandom.current().nextInt(0, 100) <= hitChance.getValDouble() && (mc.objectMouseOver.entityHit != null && (mc.objectMouseOver.entityHit == this.target || mode.getValString() == "Blatant") || !raytrace.getValBoolean()) && this.canHit()) {
                            if (this.isBlocking) {
                                this.unBlock();
                            }

                            isAttackTick = true;
                            this.attack();
                            if (this.canBlock) {
                                this.block();
                            }
                        }

                        this.attackTimer.reset();
                    } else {
                        isAttackTick = false;
                    }
                } else if (this.isBlocking) {
                    this.unBlock();
                }
        }
    }
	
    private boolean canHit() {
        boolean flag = Client.instance.settingsManager.getSettingByName(this, "Range").getValBoolean();
        boolean flag1 = Client.instance.settingsManager.getSettingByName(this, "BlockRange").getValBoolean();

        if (flag || flag1) {
            if (mc.thePlayer.getCurrentEquippedItem() == null) {
                return false;
            }

            if (flag && !flag1 && !(mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword)) {
                return false;
            }

            if (!flag && flag1 && !(mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemAxe)) {
                return false;
            }

            if (flag && flag1 && !(mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemAxe) && !(mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword)) {
                return false;
            }
        }

        return true;
    }
    
    private void rotate(EventPreUpdate e) {
        if (canHit()) {
            float[] afloat = RotationUtil.getRotations(this.target);
            if (rotaion.getValString() == "Blatant") {
            	e.setYaw(afloat[0]);
            	e.setPitch(afloat[1]);
//				mc.thePlayer.rotationYaw = afloat[0];
//				mc.thePlayer.rotationPitch = afloat[1];
				isRotationTick = true;
			} else {
                double d0;
                double d1;
                float f;
                float f1;
                
                if (rotaion.getValString() == "Smooth") {
                	d0 = ThreadLocalRandom.current().nextDouble(7.5D, 10.0D);
                    f = RotationUtil.getYawChange(mc.thePlayer.prevRotationYaw, this.target.posX + ThreadLocalRandom.current().nextDouble(-1.0D, 1.0D) * 0.05D, this.target.posZ + ThreadLocalRandom.current().nextDouble(-1.0D, 1.0D) * 0.05D);
                    f1 = (float) ((double) f / d0);
                   mc.thePlayer.rotationYaw = mc.thePlayer.prevRotationYaw + f1;
                    d1 = (double) (afloat[1] - mc.thePlayer.rotationPitch);
                    mc.thePlayer.rotationPitch = (float) ((double) mc.thePlayer.rotationPitch + d1 / d0);
                    isRotationTick = true;
                } else if (rotaion.getValString() == "Legit") {
                	//还没写好
                }
			}
        	}
        if (mc.thePlayer.rotationPitch > 90.0F) {
            mc.thePlayer.rotationPitch = 90.0F;
        } else if (mc.thePlayer.rotationPitch < -90.0F) {
            mc.thePlayer.rotationPitch = -90.0F;
        }
        }
	
    public void onEnable() {
        this.targets.clear();
        super.onEnable();
    }

    public void onDisable() {
        if (this.isBlocking) {
            this.unBlock();
        }

        isAttackTick = false;
        isRotationTick = false;
        super.onDisable();
    }
    
    private void setTargets() {
    	
        this.targets = this.getTargets();
        if (!this.targets.isEmpty() && (this.target == null || this.targets.contains(this.target))) {
        //Single
            if (mode.getValString() == "Single") {
                if (this.target != null && this.isValid(this.target) && this.targets.size() == 1) {
                    return;
                }
            }
            if (mode.getValString() == "Switch") {
                if (this.target == null) {
                    this.target = (EntityLivingBase) this.targets.get(0);
                } else if (this.targets.size() > 1) {
                    int i = this.targets.size() - 1;

                    if (this.currentIndex >= i) {
                        this.currentIndex = 0;
                    } else {
                        ++this.currentIndex;
                    }

                    if (this.targets.get(this.currentIndex) != null && this.targets.get(this.currentIndex) != this.target) {
                        this.target = (EntityLivingBase) this.targets.get(this.currentIndex);
                        this.switchTimer.reset();
                    }
                }
                if (mode.getValString() == "Multi") {
                    if (this.targets.isEmpty()) {
                        this.target = null;
                    } else if (this.target != null && !this.targets.contains(this.target) || this.target == null) {
                        this.target = (EntityLivingBase) this.targets.get(ThreadLocalRandom.current().nextInt(this.targets.size() - 1));
                    }
                }
                
            }
        }
                if (this.target != null && this.isValid(this.target)) {
                    return;
                } else {
                    this.target = null;
                }
                this.target = (EntityLivingBase) this.targets.get(0);

    }
    
    
    private int getTargetInt() {
        for (int i = 0; i < this.targets.size(); ++i) {
            if (this.targets.get(i) == this.target) {
                return i;
            }
        }
        return -1;
    }
	
    private List getTargets() {
    	
        ArrayList arraylist = new ArrayList();
        Iterator iterator = mc.theWorld.loadedEntityList.iterator();

        while (iterator.hasNext()) {
            Entity entity = (Entity) iterator.next();

            if (this.isValid(entity)) {
                arraylist.add((EntityLivingBase) entity);
            }
        }

        return arraylist;
    }

    private boolean isValid(Entity e) {
    	
//        return entity instanceof EntityLivingBase
//        		&& entity != mc.thePlayer 
//        		&& entity.isEntityAlive() 
//        		&& !(entity instanceof EntityArmorStand) 
//        		&& CombatUtil.canTarget(entity, true) ? (double) mc.thePlayer.getDistanceToEntity(entity) <= Math.max(range.getValDouble(), blockRange.getValDouble()) : false;

        if (e == mc.thePlayer) {
            return false;
        }
        if (!e.isEntityAlive()) {
            return false;
        }
        if (e instanceof EntityPlayer && player.getValBoolean() && mc.thePlayer.isOnSameTeam((EntityLivingBase)e)) {
            return true;
        }
        if (e instanceof EntityMob && mobs.getValBoolean()) {
            return true;
        }
        if (e instanceof EntityAnimal && animals.getValBoolean()) {
            return true;
        }
        if (e.isInvisible() && !invis.getValBoolean()) {
            return true;
        }
        return false;
    }
	
    private boolean attack() {
        if (this.target == null) {
            return false;
        } else {
            if (rotaion.getValString() == "Smooth") {
                Iterator iterator = this.targets.iterator();

                while (iterator.hasNext()) {
                    EntityLivingBase entitylivingbase = (EntityLivingBase) iterator.next();

                    mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(entitylivingbase, Action.ATTACK));
                }
            } else {
                mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(this.target, Action.ATTACK));
            }

            return true;
        }
    }
    
    private void unBlock() {
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
        mc.playerController.onStoppedUsingItem(mc.thePlayer);
    }

    private void block() {
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
        if (mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem())) {
             mc.getItemRenderer().resetEquippedProgress2();
        }
    }
}