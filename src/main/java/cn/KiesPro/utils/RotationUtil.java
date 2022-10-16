package cn.KiesPro.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

public class RotationUtil {
	
    static Minecraft mc = Minecraft.getMinecraft();
    
    public static float[] getRotations(Entity entity) {
        double diffY;
        if (entity == null) {
            return null;
        }
        double diffX = entity.posX - mc.thePlayer.posX;
        double diffZ = entity.posZ - mc.thePlayer.posZ;
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase elb = (EntityLivingBase) entity;
            diffY = elb.posY + ((double) elb.getEyeHeight() - 0.4) - (mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight());
        } else {
            diffY = (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0 - (mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight());
        }
        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float) (-Math.atan2(diffY, dist) * 180.0 / Math.PI);
        return new float[]{yaw, pitch};
    }
    
    public static float getYawChange(float f, double d0, double d1) {
        double d2 = d0 - mc.thePlayer.posX;
        double d3 = d1 - mc.thePlayer.posZ;
        double d4 = 0.0D;

        if (d3 < 0.0D && d2 < 0.0D) {
            if (d2 != 0.0D) {
                d4 = 90.0D + Math.toDegrees(Math.atan(d3 / d2));
            }
        } else if (d3 < 0.0D && d2 > 0.0D) {
            if (d2 != 0.0D) {
                d4 = -90.0D + Math.toDegrees(Math.atan(d3 / d2));
            }
        } else if (d3 != 0.0D) {
            d4 = Math.toDegrees(-Math.atan(d2 / d3));
        }

        return MathHelper.wrapAngleTo180_float(-(f - (float) d4));
    }
    
    public static float[] getRotationsToEnt(Entity ent) {
        final double differenceX = ent.posX - mc.thePlayer.posX;
        final double differenceY = (ent.posY + ent.height) - (mc.thePlayer.posY + mc.thePlayer.height) - 0.5;
        final double differenceZ = ent.posZ - mc.thePlayer.posZ;
        final float rotationYaw = (float) (Math.atan2(differenceZ, differenceX) * 180.0D / Math.PI) - 90.0f;
        final float rotationPitch = (float) (Math.atan2(differenceY, mc.thePlayer.getDistanceToEntity(ent)) * 180.0D / Math.PI);
        final float finishedYaw = mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(rotationYaw - mc.thePlayer.rotationYaw);
        final float finishedPitch = mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(rotationPitch - mc.thePlayer.rotationPitch);
        return new float[]{finishedYaw, -MathHelper.clamp_float(finishedPitch, -90, 90)};
    }
    
}
