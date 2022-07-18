package cn.KiesPro.module.combat;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

import cn.KiesPro.Client;
import cn.KiesPro.module.Category;
import cn.KiesPro.module.Module;
import cn.KiesPro.settings.Setting;
import cn.KiesPro.utils.raven.Utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;
public class AimAssist extends Module {

    public static Setting Players,Inv,Mobs,Animals;

    public static Setting speed, compliment,showaimobject;
    public static Setting fov;
    public static Setting distance;
    public static Setting clickAim;
    public static Setting weaponOnly;
    public static Setting breakBlocks;
    public static Setting blatantMode;
    public static Setting ignoreFriends;
    public static ArrayList<Entity> friends = new ArrayList<>();
    
	public AimAssist() {
		super("AimAssist", "Auto Aim", Category.COMBAT);
        this.registerSetting(speed = new Setting("Speed 1",this, 45.0D, 5.0D, 100.0D, true));
        this.registerSetting(compliment = new Setting("Speed 2", this,15.0D, 2D, 97.0D, true));
        this.registerSetting(fov = new Setting("FOV", this,90.0D, 15.0D, 360.0D, true));
        this.registerSetting(distance = new Setting("Distance", this,4.5D, 1.0D, 10.0D, false));
        this.registerSetting(clickAim = new Setting("ClickAim", this,true));
        this.registerSetting(breakBlocks = new Setting("BreakBlocks", this,true));
        this.registerSetting(ignoreFriends = new Setting("Ignore Friends", this,true));
        this.registerSetting(weaponOnly = new Setting("Weapon only", this,false));
        this.registerSetting(blatantMode = new Setting("Blatant mode", this,false));
        this.registerSetting(Players = new Setting("Players", this, true));
        this.registerSetting(Inv = new Setting("Inv", this, true));
        this.registerSetting(Mobs = new Setting("Mobs", this, true));
        this.registerSetting(Animals = new Setting("Animals", this, true));
	}

	@SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
		
		if (Client.instance.destructed) {
			return;
		}
		
        if(!Utils.currentScreenMinecraft()){
            return;
        }
        if(!Utils.isPlayerInGame()) return;

        if (breakBlocks.isEnabled() && mc.objectMouseOver != null) {
            BlockPos p = mc.objectMouseOver.getBlockPos();
            if (p != null) {
                Block bl = mc.theWorld.getBlockState(p).getBlock();
                if (bl != Blocks.air && !(bl instanceof BlockLiquid) && bl instanceof  Block) {
                    return;
                }
            }
        }


        if (!weaponOnly.isEnabled() || Utils.isPlayerHoldingWeapon()) {

            if (!clickAim.isEnabled() || Mouse.isButtonDown(0)) {
                Entity en = this.getEnemy();
                if (en != null) {

                    if (blatantMode.isEnabled()) {
                        Utils.aim(en, 0.0F, false);
                    } else {
                        double n = Utils.fovFromEntity(en);
                        if (n > 1.0D || n < -1.0D) {
                            double complimentSpeed = n*(ThreadLocalRandom.current().nextDouble(compliment.getValDouble() - 1.47328, compliment.getValDouble() + 2.48293)/100);
                            double val2 = complimentSpeed + ThreadLocalRandom.current().nextDouble(speed.getValDouble() - 4.723847, speed.getValDouble());
                            float val = (float)(-(complimentSpeed + n / (101.0D - (float)ThreadLocalRandom.current().nextDouble(speed.getValDouble() - 4.723847, speed.getValDouble()))));
                            mc.thePlayer.rotationYaw += val;
                        }
                    }
                }
            }
        }
    }

    public static boolean isAFriend(Entity entity) {
        if(entity == mc.thePlayer) return true;

        for (Entity wut : friends){
            if (wut.equals(entity))
                return true;
        }
        try {
            EntityPlayer bruhentity = (EntityPlayer) entity;
            if(mc.thePlayer.isOnSameTeam((EntityLivingBase) entity) || mc.thePlayer.getDisplayName().getUnformattedText().startsWith(bruhentity.getDisplayName().getUnformattedText().substring(0, 2))) return true;
        } catch (Exception fhwhfhwe) {

        }



        return false;
    }

    public Entity getEnemy() {
        int fov2 = (int) (fov.getValDouble());
        Iterator var2 = mc.theWorld.loadedEntityList.iterator();

        Entity en;
        do {
                do {
                    do {
                        do {
                            do {
                                do {
                                    if (!var2.hasNext()) {
                                        return null;
                                    }

                                    en = (Entity) var2.next();
                                } while (ignoreFriends.isEnabled() && isAFriend(en));
                            } while(en == mc.thePlayer);
                        } while(en.isDead != false);
                    } while(!Inv.isEnabled() && en.isInvisible());
                } while((double)mc.thePlayer.getDistanceToEntity(en) > distance.getValDouble());
        	} while(!blatantMode.isEnabled() && !Utils.fov(en, (float)fov2));

        if (en instanceof EntityPlayer){
            if (Players.isEnabled()) {
                return en;
            }
        }
        if (en instanceof EntityAnimal){
            if (Animals.isEnabled()) {
                return en;
            }
        }
        if (en instanceof EntityMob){
            if (Mobs.isEnabled()) {
                return en;
            }
        }

        return null;

    }

    public static void addFriend(Entity entityPlayer) {
        friends.add(entityPlayer);
    }

    public static boolean addFriend(String name) {
        boolean found = false;
        for (Entity entity:mc.theWorld.getLoadedEntityList()) {
            if (entity.getName().equalsIgnoreCase(name) || entity.getCustomNameTag().equalsIgnoreCase(name)) {
                if(!isAFriend(entity)) {
                    addFriend(entity);
                    found = true;
                }
            }
        }

        return found;
    }

    public static boolean removeFriend(String name) {
        boolean removed = false;
        boolean found = false;
        for (NetworkPlayerInfo networkPlayerInfo : new ArrayList<NetworkPlayerInfo>(mc.getNetHandler().getPlayerInfoMap())) {
            Entity entity = mc.theWorld.getPlayerEntityByName(networkPlayerInfo.getDisplayName().getUnformattedText());
            if (entity.getName().equalsIgnoreCase(name) || entity.getCustomNameTag().equalsIgnoreCase(name)) {
                removed = removeFriend(entity);
                found = true;
            }
        }

        return found && removed;
    }

    public static boolean removeFriend(Entity entityPlayer) {
        try{
            friends.remove(entityPlayer);
        } catch (Exception eeeeee){
            eeeeee.printStackTrace();
            return false;
        }
        return true;
    }

    public static ArrayList<Entity> getFriends() {
        return friends;
    }
}
