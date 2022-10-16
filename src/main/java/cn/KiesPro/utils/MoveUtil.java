package cn.KiesPro.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;

public class MoveUtil {
	
	protected static Minecraft mc = Minecraft.getMinecraft();
	
    public static boolean isMoving() {
        return mc.thePlayer.movementInput.moveForward != 0.0F || mc.thePlayer.movementInput.moveStrafe != 0.0F;
    }

    public static double getBaseMovementSpeed() {
        double d0 = 0.2873D;

        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            int i = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();

            d0 *= 1.0D + 0.2D * (double) (i + 1);
        }

        return d0;
    }

    public static float getSpeed() {
        float f = (float) Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ);

        return f;
    }
    /*
     * 好像mc.thePlayer有
     */
    public static boolean isOnGround(double height) {
        return !mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, -height, 0.0D)).isEmpty();
    }

    public static int getJumpEffect() {
        return mc.thePlayer.isPotionActive(Potion.jump) ? mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1 : 0;
    }

    public static int getSpeedEffect() {
        return mc.thePlayer.isPotionActive(Potion.moveSpeed) ? mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1 : 0;
    }

    public static float getDirection() {
        float f = mc.thePlayer.rotationYaw;

        if (mc.thePlayer.moveForward < 0.0F) {
            f += 180.0F;
        }

        float f1 = 1.0F;

        if (mc.thePlayer.moveForward < 0.0F) {
            f1 = -0.5F;
        } else if (mc.thePlayer.moveForward > 0.0F) {
            f1 = 0.5F;
        } else {
            f1 = 1.0F;
        }

        if (mc.thePlayer.moveStrafing > 0.0F) {
            f -= 90.0F * f1;
        }

        if (mc.thePlayer.moveStrafing < 0.0F) {
            f += 90.0F * f1;
        }

        f *= 0.017453292F;
        return f;
    }

}
