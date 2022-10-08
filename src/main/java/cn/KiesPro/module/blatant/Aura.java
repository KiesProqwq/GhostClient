package cn.KiesPro.module.blatant;

import java.util.ArrayList;

import cn.KiesPro.event.eventapi.EventTarget;
import cn.KiesPro.event.events.EventPostUpdate;
import cn.KiesPro.event.events.EventPreUpdate;
import cn.KiesPro.module.Category;
import cn.KiesPro.module.Module;
import cn.KiesPro.settings.Setting;
import cn.KiesPro.utils.MathUtils;
import cn.KiesPro.utils.RotationUtil;
import cn.KiesPro.utils.TimerUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.util.MathHelper;

public class Aura extends Module {
	
    public static float[] facing;
    
    public static EntityLivingBase curTarget = null;
	public TimerUtils timer = new TimerUtils();
	
	private TimerUtils blockTimer = new TimerUtils();
    private boolean doBlock = false;
    private boolean unBlock = false;
    
    ArrayList<String> rotaions = new ArrayList<String>();
    
    public static Setting rotaion;
    
    public static Setting range;
    public static Setting maxCps;
    public static Setting minCps;
    
    public static Setting autoblock;
    public static Setting keepsprint;
    
	public Aura() {
		super("Aura", "Attack Entitys", Category.BLATANT);
        rotaions.add("Blatant");
        rotaions.add("Smooth");
        rotaions.add("None");
        
        registerSetting(rotaion = new Setting("Rotaions", this,"Blatant",rotaions));
        registerSetting(range =new Setting("Range", this, 4.20D, 1.00D, 6.00D, false));
        registerSetting(maxCps = new Setting("MaxCPS", this, 10.0D, 2.0D, 20.0D, false));
        registerSetting(minCps = new Setting("MinCPS", this, 10.0D, 2.0D, 20.0D, false));
        registerSetting(autoblock = new Setting("Autoblock", this, false));
        registerSetting(keepsprint = new Setting("KeepSprint", this, false));
	}
	
	@EventTarget
	public void onPre(EventPreUpdate e) {
		EntityLivingBase target = getTarget(range.getValDouble());
		if (target == null) {
	    	mc.gameSettings.keyBindUseItem.pressed = false;
			return;
		}
		//Rotations
        switch (rotaion.getValString()) {
        	case "Blatant":
                float[] rot = RotationUtil.getRotations(target);
//        		System.out.println("Blatantttttttttttttttt");
                //work!!!!!
                facing = getRotationsToEnt(target);
                facing[0] += MathUtils.randomNumber(1, 5);
                facing[1] += MathUtils.randomNumber(1, 5);
                facing[0] = (float) (facing[0] + MathUtils.randomNumber(1.98f, -1.98f));

                e.setYaw(facing[0]);
                e.setPitch(facing[1]);

                mc.thePlayer.rotationYawHead = mc.thePlayer.renderYawOffset = facing[0];
//                mc.thePlayer.renderYawOffset = yaw;
//                //head
//                mc.thePlayer.rotationYawHead = yaw;
//                mc.thePlayer.rotationPitch = pitch;
//        		e.setYaw(rot[0]);
//        		e.setPitch(rot[1]);
        		break;
        	case "Safe":
        }

		if (target != null && shouldAttack()) {
	        mc.thePlayer.swingItem();
	    	mc.gameSettings.keyBindUseItem.pressed = true;

	        //keepsprint
            if (!keepsprint.isEnabled()) {
                mc.playerController.attackEntity((EntityPlayer)mc.thePlayer, target);
            } else {
                mc.getNetHandler().addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
            }
			timer.reset();
		}
	}
	
    private float[] getRotationsToEnt(Entity ent) {
        final double differenceX = ent.posX - mc.thePlayer.posX;
        final double differenceY = (ent.posY + ent.height) - (mc.thePlayer.posY + mc.thePlayer.height) - 0.5;
        final double differenceZ = ent.posZ - mc.thePlayer.posZ;
        final float rotationYaw = (float) (Math.atan2(differenceZ, differenceX) * 180.0D / Math.PI) - 90.0f;
        final float rotationPitch = (float) (Math.atan2(differenceY, mc.thePlayer.getDistanceToEntity(ent)) * 180.0D
                / Math.PI);
        final float finishedYaw = mc.thePlayer.rotationYaw
                + MathHelper.wrapAngleTo180_float(rotationYaw - mc.thePlayer.rotationYaw);
        final float finishedPitch = mc.thePlayer.rotationPitch
                + MathHelper.wrapAngleTo180_float(rotationPitch - mc.thePlayer.rotationPitch);
        return new float[]{finishedYaw, -MathHelper.clamp_float(finishedPitch, -90, 90)};
    }
    
    @EventTarget
    public void onPost(EventPostUpdate e) {
        if(curTarget == null)
            return;
    }

    private boolean shouldAttack() {
        return this.timer.hasReached(1000.0 / (MathUtils.randomNumber(maxCps.getValDouble(), minCps.getValDouble())));
    }
    
	public EntityLivingBase getTarget(double range) {
		EntityLivingBase target = null;
		double cDist = 100;
		for (Entity e : mc.theWorld.loadedEntityList) {
			double dist = mc.thePlayer.getDistanceToEntity(e);
			if (dist < range && e != mc.thePlayer && (e instanceof EntityPlayer ||  e instanceof EntityWither)) {
				if (cDist > dist) {
					target = (EntityLivingBase) e;
					cDist = dist;
				}
			}
		}
		return target;
	}
	
}
