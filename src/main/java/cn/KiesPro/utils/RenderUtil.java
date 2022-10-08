package cn.KiesPro.utils;

import java.awt.Color;
import java.util.Objects;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;

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
        rect(x - 1.0f, y - 1.0f, x2 + 1.0f, y, lineColor);
        rect(x - 1.0f, y, x, y2, lineColor);
        rect(x - 1.0f, y2, x2 + 1.0f, y2 + 1.0f, lineColor);
        rect(x2, y, x2 + 1.0f, y2, lineColor);
    }
	
	/*
	 * skid by Explict
	 */
	public static void blockESPBox(BlockPos blockpos, float r, float g, float b, float a) {
        double d0 = (double) blockpos.getX() - mc.getRenderManager().viewerPosX;
        double d1 = (double) blockpos.getY() - mc.getRenderManager().viewerPosY;
        double d2 = (double) blockpos.getZ() - mc.getRenderManager().viewerPosZ;

        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(a);
        GL11.glColor4d(0.0D, 1.0D, 0.0D, 0.15000000596046448D);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4d((double) r, (double) g, (double) b, 1000.0D);
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
    
    public static void drawBorderedRect(float x, float y, float x1, float y1, float width, int internalColor, int borderColor) {
        RenderUtil.enableGL2D();
        RenderUtil.drawRect(x + width, y + width, x1 - width, y1 - width, internalColor);
        GL11.glPushMatrix();
        RenderUtil.drawRect(x + width, y, x1 - width, y + width, borderColor);
        RenderUtil.drawRect(x, y, x + width, y1, borderColor);
        RenderUtil.drawRect(x1 - width, y, x1, y1, borderColor);
        RenderUtil.drawRect(x + width, y1 - width, x1 - width, y1, borderColor);
        GL11.glPopMatrix();
        RenderUtil.disableGL2D();
    }
    public static void drawOutlinedRoundedRect(float x, float y, float width, float height, float radius, float linewidth, int color) {
        int i;
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        double x1 = x + width;
        double y1 = y + height;
        float f = (float)(color >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(color >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(color >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(color & 0xFF) / 255.0f;
        GL11.glPushAttrib(0);
        GL11.glScaled(0.5, 0.5, 0.5);
        x *= 2.0f;
        y *= 2.0f;
        x1 *= 2.0;
        y1 *= 2.0;
        GL11.glLineWidth(linewidth);
        GL11.glDisable(3553);
        GL11.glColor4f(f1, f2, f3, f);
        GL11.glEnable(2848);
        GL11.glBegin(2);
        for (i = 0; i <= 90; i += 3) {
            GL11.glVertex2d((double)(x + radius) + Math.sin((double)i * Math.PI / 180.0) * (double)(radius * -1.0f), (double)(y + radius) + Math.cos((double)i * Math.PI / 180.0) * (double)(radius * -1.0f));
        }
        for (i = 90; i <= 180; i += 3) {
            GL11.glVertex2d((double)(x + radius) + Math.sin((double)i * Math.PI / 180.0) * (double)(radius * -1.0f), y1 - (double)radius + Math.cos((double)i * Math.PI / 180.0) * (double)(radius * -1.0f));
        }
        for (i = 0; i <= 90; i += 3) {
            GL11.glVertex2d(x1 - (double)radius + Math.sin((double)i * Math.PI / 180.0) * (double)radius, y1 - (double)radius + Math.cos((double)i * Math.PI / 180.0) * (double)radius);
        }
        for (i = 90; i <= 180; i += 3) {
            GL11.glVertex2d(x1 - (double)radius + Math.sin((double)i * Math.PI / 180.0) * (double)radius, (double)(y + radius) + Math.cos((double)i * Math.PI / 180.0) * (double)radius);
        }
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glScaled(2.0, 2.0, 2.0);
        GL11.glPopAttrib();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    
    public static void drawRoundRect(double xPosition, double yPosition, double endX, double endY, float radius, int color) {
    	double width = endX - xPosition;
    	double height = endY - yPosition;
    	drawRect(xPosition + radius, yPosition + radius, xPosition + width - radius, yPosition + height - radius, color);
    	drawRect(xPosition, yPosition + radius, xPosition + radius, yPosition + height - radius, color);
    	drawRect(xPosition + width - radius, yPosition + radius, xPosition + width, yPosition + height - radius, color);
    	drawRect(xPosition + radius, yPosition, xPosition + width - radius, yPosition + radius, color);
    	drawRect(xPosition + radius, yPosition + height - radius, xPosition + width - radius, yPosition + height, color);
    	drawFilledCircle(xPosition + radius, yPosition + radius, radius, color, 1);
    	drawFilledCircle(xPosition + radius, yPosition + height - radius, radius, color, 2);
    	drawFilledCircle(xPosition + width - radius, yPosition + radius, radius, color, 3);
    	drawFilledCircle(xPosition + width - radius, yPosition + height - radius, radius, color, 4);
}
    
    public static void drawFilledCircle(double x, double y, double r, int c, int id) {
        float f = (float) (c >> 24 & 0xff) / 255F;
        float f1 = (float) (c >> 16 & 0xff) / 255F;
        float f2 = (float) (c >> 8 & 0xff) / 255F;
        float f3 = (float) (c & 0xff) / 255F;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glColor4f(f1, f2, f3, f);
        GL11.glBegin(GL11.GL_POLYGON);
        if (id == 1) {
        	GL11.glVertex2d(x, y);
            for (int i = 0; i <= 90; i++) {
                double x2 = Math.sin((i * 3.141526D / 180)) * r;
                double y2 = Math.cos((i * 3.141526D / 180)) * r;
                GL11.glVertex2d(x - x2, y - y2);
            }
        } else if (id == 2) {
        	GL11.glVertex2d(x, y);
            for (int i = 90; i <= 180; i++) {
                double x2 = Math.sin((i * 3.141526D / 180)) * r;
                double y2 = Math.cos((i * 3.141526D / 180)) * r;
                GL11.glVertex2d(x - x2, y - y2);
            }
        } else if (id == 3) {
        	GL11.glVertex2d(x, y);
            for (int i = 270; i <= 360; i++) {
                double x2 = Math.sin((i * 3.141526D / 180)) * r;
                double y2 = Math.cos((i * 3.141526D / 180)) * r;
                GL11.glVertex2d(x - x2, y - y2);
            }
        } else if (id == 4) {
        	GL11. glVertex2d(x, y);
            for (int i = 180; i <= 270; i++) {
                double x2 = Math.sin((i * 3.141526D / 180)) * r;
                double y2 = Math.cos((i * 3.141526D / 180)) * r;
                GL11.glVertex2d(x - x2, y - y2);
            }
        } else {
            for (int i = 0; i <= 360; i++) {
                double x2 = Math.sin((i * 3.141526D / 180)) * r;
                double y2 = Math.cos((i * 3.141526D / 180)) * r;
                GL11.glVertex2f((float) (x - x2), (float) (y - y2));
            }
        }
        GL11.glEnd();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }
    
    public static void drawBorderedRoundedRect(float x, float y, float width, float height, float radius, float linewidth, int insideC, int borderC) {
        RenderUtil.drawRoundRect(x, y, x + width, y + height, radius, insideC);
    	drawRect(radius, linewidth, height, insideC, borderC);
        RenderUtil.drawOutlinedRoundedRect(x, y, width, height, radius, linewidth, borderC);
    }
    /*
     * Fans 3.1-beta ChestESP util 
     */
     public static void chestESPBox(TileEntity entity, int mode, Color color) {
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glLineWidth((float)2.0f);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        double x = entity.getPos().getX();
        double y = entity.getPos().getY();
        double z = entity.getPos().getZ();
        GlStateManager.translate((double)(x - mc.getRenderManager().renderPosX), (double)(y - mc.getRenderManager().renderPosY), (double)(z - mc.getRenderManager().renderPosZ));
        GlStateManager.translate((double)(-(x - mc.getRenderManager().renderPosX)), (double)(-(y - mc.getRenderManager().renderPosY)), (double)(-(z - mc.getRenderManager().renderPosZ)));
        GL11.glEnable((int)2848);
        GlStateManager.color((float)((float)((Color)Objects.requireNonNull((Object)color)).getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f));
        RenderGlobal.drawSelectionBoundingBox((AxisAlignedBB)new AxisAlignedBB(x - 0.0 + 0.05 - x + (x - mc.getRenderManager().renderPosX), y - y + (y - mc.getRenderManager().renderPosY), z - 0.0 + 0.05 - z + (z - mc.getRenderManager().renderPosZ), x + 1.0 - 0.05 - x + (x - mc.getRenderManager().renderPosX), y + 1.0 - 0.05 - y + (y - mc.getRenderManager().renderPosY), z + 1.0 - 0.05 - z + (z - mc.getRenderManager().renderPosZ)));
        GlStateManager.translate((double)(x - mc.getRenderManager().renderPosX), (double)(y - mc.getRenderManager().renderPosY), (double)(z - mc.getRenderManager().renderPosZ));
        GlStateManager.translate((double)(-(x - mc.getRenderManager().renderPosX)), (double)(-(y - mc.getRenderManager().renderPosY)), (double)(-(z - mc.getRenderManager().renderPosZ)));
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
    }
     
     /*
      * Fairy Soul ESP
      */
     public static void fairySoulESP(BlockPos bp, int color) {
         if (bp == null) {
             return;
         }
         double x = (double)bp.getX() - mc.getRenderManager().viewerPosX;
         double y = (double)bp.getY() - mc.getRenderManager().viewerPosY;
         double z = (double)bp.getZ() - mc.getRenderManager().viewerPosZ;
         GL11.glBlendFunc((int)770, (int)771);
         GL11.glEnable((int)3042);
         GL11.glLineWidth((float)2.0f);
         GL11.glDisable((int)3553);
         GL11.glDisable((int)2929);
         GL11.glDepthMask((boolean)false);
         float a = (float)(color >> 24 & 0xFF) / 255.0f;
         float r = (float)(color >> 16 & 0xFF) / 255.0f;
         float g = (float)(color >> 8 & 0xFF) / 255.0f;
         float b = (float)(color & 0xFF) / 255.0f;
         GL11.glColor4d((double)r, (double)g, (double)b, (double)a);
         RenderGlobal.drawSelectionBoundingBox((AxisAlignedBB)new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
         dbb(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0), r, g, b);
         GL11.glEnable((int)3553);
         GL11.glEnable((int)2929);
         GL11.glDepthMask((boolean)true);
         GL11.glDisable((int)3042);
     }

     public static void dbb(AxisAlignedBB abb, float r, float g, float b) {
         float a = 0.25f;
         Tessellator ts = Tessellator.getInstance();
         WorldRenderer vb = ts.getWorldRenderer();
         vb.begin(7, DefaultVertexFormats.POSITION_COLOR);
         vb.pos(abb.minX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
         vb.pos(abb.minX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
         vb.pos(abb.maxX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
         vb.pos(abb.maxX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
         vb.pos(abb.maxX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
         vb.pos(abb.maxX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
         vb.pos(abb.minX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
         vb.pos(abb.minX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
         ts.draw();
         vb.begin(7, DefaultVertexFormats.POSITION_COLOR);
         vb.pos(abb.maxX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
         vb.pos(abb.maxX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
         vb.pos(abb.minX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
         vb.pos(abb.minX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
         vb.pos(abb.minX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
         vb.pos(abb.minX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
         vb.pos(abb.maxX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
         vb.pos(abb.maxX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
         ts.draw();
         vb.begin(7, DefaultVertexFormats.POSITION_COLOR);
         vb.pos(abb.minX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
         vb.pos(abb.maxX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
         vb.pos(abb.maxX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
         vb.pos(abb.minX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
         vb.pos(abb.minX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
         vb.pos(abb.minX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
         vb.pos(abb.maxX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
         vb.pos(abb.maxX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
         ts.draw();
         vb.begin(7, DefaultVertexFormats.POSITION_COLOR);
         vb.pos(abb.minX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
         vb.pos(abb.maxX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
         vb.pos(abb.maxX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
         vb.pos(abb.minX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
         vb.pos(abb.minX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
         vb.pos(abb.minX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
         vb.pos(abb.maxX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
         vb.pos(abb.maxX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
         ts.draw();
         vb.begin(7, DefaultVertexFormats.POSITION_COLOR);
         vb.pos(abb.minX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
         vb.pos(abb.minX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
         vb.pos(abb.minX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
         vb.pos(abb.minX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
         vb.pos(abb.maxX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
         vb.pos(abb.maxX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
         vb.pos(abb.maxX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
         vb.pos(abb.maxX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
         ts.draw();
         vb.begin(7, DefaultVertexFormats.POSITION_COLOR);
         vb.pos(abb.minX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
         vb.pos(abb.minX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
         vb.pos(abb.minX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
         vb.pos(abb.minX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
         vb.pos(abb.maxX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
         vb.pos(abb.maxX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
         vb.pos(abb.maxX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
         vb.pos(abb.maxX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
         ts.draw();
     }
    
     /**
      * Draws an image
      * @param loc The image location
      * @param x The x position
      * @param y The y position
      * @param width The width
      * @param height The height
      */
     public static void drawImage(ResourceLocation image, double x, double y, double width, double height) {
         drawImage(image, x, y, width, height, -1);
     }

     public static void drawImage(ResourceLocation image, double x, double y, double width, double height, int color) {
         GlStateManager.pushMatrix();
         GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
         GL11.glDisable(2929);
         GL11.glEnable(3042);
         GL11.glDepthMask(false);
         OpenGlHelper.glBlendFunc(770, 771, 1, 0);
         Color color1 = new Color(color);
         GL11.glColor4f((float)color1.getRed() / 255.0f, (float)color1.getGreen() / 255.0f, (float)color1.getBlue() / 255.0f, (float)color1.getAlpha() / 255.0f);
         Minecraft.getMinecraft().getTextureManager().bindTexture(image);
         drawModalRectWithCustomSizedTexture((float)x, (float)y, 0.0f, 0.0f, (float)width, (float)height, (float)width, (float)height);
         GL11.glDepthMask(true);
         GL11.glDisable(3042);
         GL11.glEnable(2929);
         GlStateManager.popMatrix();
     }
     
     public static void drawModalRectWithCustomSizedTexture(float x, float y, float u, float v, float width, float height, float textureWidth, float textureHeight) {
         float f = 1.0f / textureWidth;
         float f1 = 1.0f / textureHeight;
         Tessellator tessellator = Tessellator.getInstance();
         WorldRenderer worldrenderer = tessellator.getWorldRenderer();
         worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
         worldrenderer.pos(x, y + height, 0.0).tex(u * f, (v + height) * f1).endVertex();
         worldrenderer.pos(x + width, y + height, 0.0).tex((u + width) * f, (v + height) * f1).endVertex();
         worldrenderer.pos(x + width, y, 0.0).tex((u + width) * f, v * f1).endVertex();
         worldrenderer.pos(x, y, 0.0).tex(u * f, v * f1).endVertex();
         tessellator.draw();
     }
}
