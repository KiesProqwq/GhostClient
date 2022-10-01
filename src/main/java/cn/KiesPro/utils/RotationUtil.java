package cn.KiesPro.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

public class RotationUtil {
	
    static Minecraft mc = Minecraft.getMinecraft();
    
    public static float[] getRotations(Entity entity) {
        if (entity == null) {
            return null;
        } else {
            double d0 = entity.posX - mc.thePlayer.posX;
            double d1 = entity.posZ - mc.thePlayer.posZ;
            EntityLivingBase entitylivingbase = (EntityLivingBase) entity;
            double d2 = entitylivingbase.posY + ((double) entitylivingbase.getEyeHeight() - 0.4D) - (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight());
            double d3 = (double) MathHelper.sqrt_double(d0 * d0 + d1 * d1);
            float f = (float) (Math.atan2(d1, d0) * 180.0D / Math.PI) - 90.0F;
            float f1 = (float) (-(Math.atan2(d2, d3) * 180.0D / Math.PI));
            return new float[] { f, f1};
        }
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
    
}
