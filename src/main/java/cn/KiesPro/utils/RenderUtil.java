package cn.KiesPro.utils;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public class RenderUtil {
	
    private static final Minecraft mc = Minecraft.getMinecraft();
    
    public static void rect(float x1, float y1, float x2, float y2, int fill) {
        GlStateManager.color(0, 0, 0);
        GL11.glColor4f(0, 0, 0, 0);

        float f = (fill >> 24 & 0xFF) / 255.0F;
        float f1 = (fill >> 16 & 0xFF) / 255.0F;
        float f2 = (fill >> 8 & 0xFF) / 255.0F;
        float f3 = (fill & 0xFF) / 255.0F;

        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);

        GL11.glPushMatrix();
        GL11.glColor4f(f1, f2, f3, f);
        GL11.glBegin(7);
        GL11.glVertex2d(x2, y1);
        GL11.glVertex2d(x1, y1);
        GL11.glVertex2d(x1, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
        GL11.glPopMatrix();

        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }
    
    public static void startClip(float x1, float y1, float x2, float y2) {
        float temp;
        if (y1 > y2) {
            temp = y2;
            y2 = y1;
            y1 = temp;
        }

        GL11.glScissor((int) x1, (int) (Display.getHeight() - y2), (int) (x2 - x1), (int) (y2 - y1));
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
    }

    public static void enableGL2D() {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(true);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
    }

    public static void disableGL2D() {
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }

    public static void endClip() {
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }
    
	public static void drawBorderedCorneredRect(final float x, final float y, final float x2, final float y2, final float lineWidth, final int lineColor, final int bgColor) {
        rect(x, y, x2, y2, bgColor);
        final float f = (lineColor >> 24 & 0xFF) / 255.0f;
        final float f2 = (lineColor >> 16 & 0xFF) / 255.0f;
        final float f3 = (lineColor >> 8 & 0xFF) / 255.0f;
        final float f4 = (lineColor & 0xFF) / 255.0f;
        rect(x - 1.0f, y - 1.0f, x2 + 1.0f, y, lineColor);
        rect(x - 1.0f, y, x, y2, lineColor);
        rect(x - 1.0f, y2, x2 + 1.0f, y2 + 1.0f, lineColor);
        rect(x2, y, x2 + 1.0f, y2, lineColor);
    }
	
	/*
	 * skid by Explict
	 */
	public static void blockESPBox(BlockPos blockpos, float f, float f1, float f2, float f3) {
        double d0 = (double) blockpos.getX() - mc.getRenderManager().viewerPosX;
        double d1 = (double) blockpos.getY() - mc.getRenderManager().viewerPosY;
        double d2 = (double) blockpos.getZ() - mc.getRenderManager().viewerPosZ;

        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(f3);
        GL11.glColor4d(0.0D, 1.0D, 0.0D, 0.15000000596046448D);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4d((double) f, (double) f1, (double) f2, 1000.0D);
        RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(d0, d1, d2, d0 + 1.0D, d1 + 1.0D, d2 + 1.0D));
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
    }
	
	/*
	 * Fenix Client
	 * Minecraft Gui Method
	 */
    public static void drawRect(double left, double top, double right, double bottom, int color) {
        if (left < right) {
            double i = left;
            left = right;
            right = i;
        }
        if (top < bottom) {
            double j = top;
            top = bottom;
            bottom = j;
        }
        float f3 = (float)(color >> 24 & 0xFF) / 255.0f;
        float f = (float)(color >> 16 & 0xFF) / 255.0f;
        float f1 = (float)(color >> 8 & 0xFF) / 255.0f;
        float f2 = (float)(color & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        GlStateManager.color((float)f, (float)f1, (float)f2, (float)f3);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(left, bottom, 0.0).endVertex();
        worldrenderer.pos(right, bottom, 0.0).endVertex();
        worldrenderer.pos(right, top, 0.0).endVertex();
        worldrenderer.pos(left, top, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    
    /*
     * Fenix ChestESP
     */
    public static void renderChest(BlockPos blockPos) {
        double x = (double)blockPos.getX() - mc.getRenderManager().viewerPosX;
        double y = (double)blockPos.getY() - mc.getRenderManager().viewerPosY;
        double z = (double)blockPos.getZ() - mc.getRenderManager().viewerPosZ;
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glLineWidth((float)1.0f);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glColor4d((double)255.0, (double)170.0, (double)0.0, (double)1.0);
        RenderGlobal.drawSelectionBoundingBox((AxisAlignedBB)new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glPopMatrix();
    }

    public static void renderEnderChest(BlockPos blockPos) {
        double x = (double)blockPos.getX() - mc.getRenderManager().viewerPosX;
        double y = (double)blockPos.getY() - mc.getRenderManager().viewerPosY;
        double z = (double)blockPos.getZ() - mc.getRenderManager().viewerPosZ;
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glLineWidth((float)1.0f);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glColor4d((double)170.0, (double)0.0, (double)170.0, (double)1.0);
        RenderGlobal.drawSelectionBoundingBox((AxisAlignedBB)new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glPopMatrix();
    }
}