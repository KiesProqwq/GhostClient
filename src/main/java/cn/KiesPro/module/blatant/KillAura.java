package cn.KiesPro.module.blatant;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import cn.KiesPro.Client;
import cn.KiesPro.module.Category;
import cn.KiesPro.module.Module;
import cn.KiesPro.settings.Setting;
import cn.KiesPro.utils.CombatUtil;
import cn.KiesPro.utils.TimerUtils;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
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
    
    //KillAura
    private TimerUtils switchTimer = new TimerUtils();
    private TimerUtils attackTimer = new TimerUtils();
    
    public EntityLivingBase target;
    private List targets = new ArrayList();
    
    private int currentIndex;
    
    public static boolean isAttackTick;
    public static boolean isRotationTick;
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
        registerSetting(autoblock = new Setting("Autoblock", this, 10.0D, 2.0D, 20.0D, false));
        registerSetting(raytrace = new Setting("Raytrace", this, 10.0D, 2.0D, 20.0D, false));
        registerSetting(onClick = new Setting("OnClick", this, 10.0D, 2.0D, 20.0D, false));
        registerSetting(swordOnly = new Setting("SwordOnly", this, 10.0D, 2.0D, 20.0D, false));
        registerSetting(axeOnly = new Setting("AxeOnly", this, 10.0D, 2.0D, 20.0D, false));
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
//            if (!this.mode.equalsIgnoreCase(modes.) && this.switchTimer.hasReached((double) this.switchDelay.getValDouble()) && (!this.mode.equalsIgnoreCase(Killaura.llIlllI[32]) || this.targets.size() != 1)) {
        	if (mode.getValString() != "Switch" && this.switchTimer.hasReached((double) this.switchDelay.getValDouble()) && (!this.mode.equalsIgnoreCase(Killaura.llIlllI[32]) || this.targets.size() != 1)) {
                if (this.mode.equalsIgnoreCase(llIlllI[33])) {
                    if (this.target != null && this.isValid(this.target) && this.targets.size() == 1) {
                        return;
                    }

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
                    } else {
                        this.target = null;
                    }
                } else if (this.mode.equalsIgnoreCase(Killaura.llIlllI[34])) {
                    if (this.targets.isEmpty()) {
                        this.target = null;
                    } else if (this.target != null && !this.targets.contains(this.target) || this.target == null) {
                        this.target = (EntityLivingBase) this.targets.get(ThreadLocalRandom.current().nextInt(this.targets.size() - 1));
                    }
                }
            } else {
                if (this.target != null && this.isValid(this.target)) {
                    return;
                }

                this.target = (EntityLivingBase) this.targets.get(0);
            }

        } else {
            this.target = null;
        }
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

    private boolean isValid(Entity entity) {
        return entity instanceof EntityLivingBase && entity != mc.thePlayer && entity.isEntityAlive() && !(entity instanceof EntityArmorStand) && CombatUtil.canTarget(entity, true) ? (double) mc.thePlayer.getDistanceToEntity(entity) <= Math.max(range.getValDouble(), blockRange.getValDouble()) : false;
    }
	
    private boolean attack() {
        if (this.target == null) {
            return false;
        } else {
            if (mode.equals(rotaion.getValString())) {
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