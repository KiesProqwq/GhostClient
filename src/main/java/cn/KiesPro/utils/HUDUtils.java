package cn.KiesPro.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.lang.reflect.Field;

public class HUDUtils {
   private static final Minecraft mc = Minecraft.getMinecraft();
   public static Field timerField = null;
   public static float delta;

   public static final ScaledResolution getScaledRes() {
      final ScaledResolution scaledRes = new ScaledResolution(Minecraft.getMinecraft());
      return scaledRes;
   }

   public static void glColor(int hex) {
      float alpha = (hex >> 24 & 0xFF) / 255.0F;
      float red = (hex >> 16 & 0xFF) / 255.0F;
      float green = (hex >> 8 & 0xFF) / 255.0F;
      float blue = (hex & 0xFF) / 255.0F;
      GL11.glColor4f(red, green, blue, alpha);
   }

   public static void pre3D() {
      GL11.glPushMatrix();
      GL11.glEnable(GL11.GL_BLEND);
      GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
      GL11.glShadeModel(GL11.GL_SMOOTH);
      GL11.glDisable(GL11.GL_TEXTURE_2D);
      GL11.glEnable(GL11.GL_LINE_SMOOTH);
      GL11.glDisable(GL11.GL_DEPTH_TEST);
      GL11.glDisable(GL11.GL_LIGHTING);
      GL11.glDepthMask(false);
      GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
   }

   public static void post3D() {
      GL11.glDepthMask(true);
      GL11.glEnable(GL11.GL_DEPTH_TEST);
      GL11.glDisable(GL11.GL_LINE_SMOOTH);
      GL11.glEnable(GL11.GL_TEXTURE_2D);
      GL11.glDisable(GL11.GL_BLEND);
      GL11.glPopMatrix();
      GL11.glColor4f(1, 1, 1, 1);
   }

   public static void drawBoundingBox(AxisAlignedBB aa) {

      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer vertexBuffer = tessellator.getWorldRenderer();
      vertexBuffer.begin(3, DefaultVertexFormats.POSITION);
      vertexBuffer.pos(aa.minX, aa.minY, aa.minZ);
      vertexBuffer.pos(aa.minX, aa.maxY, aa.minZ);
      vertexBuffer.pos(aa.maxX, aa.minY, aa.minZ);
      vertexBuffer.pos(aa.maxX, aa.maxY, aa.minZ);
      vertexBuffer.pos(aa.maxX, aa.minY, aa.maxZ);
      vertexBuffer.pos(aa.maxX, aa.maxY, aa.maxZ);
      vertexBuffer.pos(aa.minX, aa.minY, aa.maxZ);
      vertexBuffer.pos(aa.minX, aa.maxY, aa.maxZ);
      tessellator.draw();
      vertexBuffer.begin(3, DefaultVertexFormats.POSITION);
      vertexBuffer.pos(aa.maxX, aa.maxY, aa.minZ);
      vertexBuffer.pos(aa.maxX, aa.minY, aa.minZ);
      vertexBuffer.pos(aa.minX, aa.maxY, aa.minZ);
      vertexBuffer.pos(aa.minX, aa.minY, aa.minZ);
      vertexBuffer.pos(aa.minX, aa.maxY, aa.maxZ);
      vertexBuffer.pos(aa.minX, aa.minY, aa.maxZ);
      vertexBuffer.pos(aa.maxX, aa.maxY, aa.maxZ);
      vertexBuffer.pos(aa.maxX, aa.minY, aa.maxZ);
      tessellator.draw();
      vertexBuffer.begin(3, DefaultVertexFormats.POSITION);
      vertexBuffer.pos(aa.minX, aa.maxY, aa.minZ);
      vertexBuffer.pos(aa.maxX, aa.maxY, aa.minZ);
      vertexBuffer.pos(aa.maxX, aa.maxY, aa.maxZ);
      vertexBuffer.pos(aa.minX, aa.maxY, aa.maxZ);
      vertexBuffer.pos(aa.minX, aa.maxY, aa.minZ);
      vertexBuffer.pos(aa.minX, aa.maxY, aa.maxZ);
      vertexBuffer.pos(aa.maxX, aa.maxY, aa.maxZ);
      vertexBuffer.pos(aa.maxX, aa.maxY, aa.minZ);
      tessellator.draw();
      vertexBuffer.begin(3, DefaultVertexFormats.POSITION);
      vertexBuffer.pos(aa.minX, aa.minY, aa.minZ);
      vertexBuffer.pos(aa.maxX, aa.minY, aa.minZ);
      vertexBuffer.pos(aa.maxX, aa.minY, aa.maxZ);
      vertexBuffer.pos(aa.minX, aa.minY, aa.maxZ);
      vertexBuffer.pos(aa.minX, aa.minY, aa.minZ);
      vertexBuffer.pos(aa.minX, aa.minY, aa.maxZ);
      vertexBuffer.pos(aa.maxX, aa.minY, aa.maxZ);
      vertexBuffer.pos(aa.maxX, aa.minY, aa.minZ);
      tessellator.draw();
      vertexBuffer.begin(3, DefaultVertexFormats.POSITION);
      vertexBuffer.pos(aa.minX, aa.minY, aa.minZ);
      vertexBuffer.pos(aa.minX, aa.maxY, aa.minZ);
      vertexBuffer.pos(aa.minX, aa.minY, aa.maxZ);
      vertexBuffer.pos(aa.minX, aa.maxY, aa.maxZ);
      vertexBuffer.pos(aa.maxX, aa.minY, aa.maxZ);
      vertexBuffer.pos(aa.maxX, aa.maxY, aa.maxZ);
      vertexBuffer.pos(aa.maxX, aa.minY, aa.minZ);
      vertexBuffer.pos(aa.maxX, aa.maxY, aa.minZ);
      tessellator.draw();
      vertexBuffer.begin(3, DefaultVertexFormats.POSITION);
      vertexBuffer.pos(aa.minX, aa.maxY, aa.maxZ);
      vertexBuffer.pos(aa.minX, aa.minY, aa.maxZ);
      vertexBuffer.pos(aa.minX, aa.maxY, aa.minZ);
      vertexBuffer.pos(aa.minX, aa.minY, aa.minZ);
      vertexBuffer.pos(aa.maxX, aa.maxY, aa.minZ);
      vertexBuffer.pos(aa.maxX, aa.minY, aa.minZ);
      vertexBuffer.pos(aa.maxX, aa.maxY, aa.maxZ);
      vertexBuffer.pos(aa.maxX, aa.minY, aa.maxZ);
      tessellator.draw();
   }

   public static double interpolate(double current, double old, double scale) {
      return old + (current - old) * scale;
   }

   public static void drawImage(ResourceLocation image, int x, int y, int width, int height) {
      drawImage(image, x, y, width, height, 1.0f);
   }

   public static void drawImage(ResourceLocation image, float x, float y, float width, float height) {
      drawImage(image, (int) x, (int) y, (int) width, (int) height, 1.0f);
   }

   public static void drawImage(ResourceLocation image, int x, int y, int width, int height, float alpha) {
      ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
      GL11.glDisable(2929);
      GL11.glEnable(3042);
      GL11.glDepthMask(false);
      OpenGlHelper.glBlendFunc(770, 771, 1, 0);
      GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha);
      Minecraft.getMinecraft().getTextureManager().bindTexture(image);
      Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, width, height, (float) width, (float) height);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glEnable(2929);
   }
   public static void doGlScissor(int x, int y, int width, int height) {
      Minecraft mc = Minecraft.getMinecraft();
      int scaleFactor = 1;
      int k = mc.gameSettings.guiScale;
      if (k == 0) {
         k = 1000;
      }
      while (scaleFactor < k && mc.displayWidth / (scaleFactor + 1) >= 320 && mc.displayHeight / (scaleFactor + 1) >= 240) {
         ++scaleFactor;
      }
      GL11.glScissor(x * scaleFactor, mc.displayHeight - (y + height) * scaleFactor, width * scaleFactor, height * scaleFactor);
   }

   public static double getAnimationState(double animation, final double finalState, final double speed) {
      final float add = (float) (HUDUtils.delta * speed);
      if (animation < finalState) {
         if (animation + add < finalState) {
            animation += add;
         } else {
            animation = finalState;
         }
      } else if (animation - add > finalState) {
         animation -= add;
      } else {
         animation = finalState;
      }
      return animation;
   }

   public static void drawArc(float x1, float y1, double r, int color, int startPoint, double arc, int linewidth) {
      r *= 2.0D;
      x1 *= 2;
      y1 *= 2;
      float f = (color >> 24 & 0xFF) / 255.0F;
      float f1 = (color >> 16 & 0xFF) / 255.0F;
      float f2 = (color >> 8 & 0xFF) / 255.0F;
      float f3 = (color & 0xFF) / 255.0F;
      GL11.glDisable(2929);
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glDepthMask(true);
      GL11.glEnable(2848);
      GL11.glHint(3154, 4354);
      GL11.glHint(3155, 4354);
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      GL11.glLineWidth(linewidth);
      GL11.glEnable(GL11.GL_LINE_SMOOTH);
      GL11.glColor4f(f1, f2, f3, f);
      GL11.glBegin(GL11.GL_LINE_STRIP);
      for (int i = startPoint; i <= arc; i += 1) {
         double x = Math.sin(i * 3.141592653589793D / 180.0D) * r;
         double y = Math.cos(i * 3.141592653589793D / 180.0D) * r;
         GL11.glVertex2d(x1 + x, y1 + y);
      }
      GL11.glEnd();
      GL11.glDisable(GL11.GL_LINE_SMOOTH);
      GL11.glScalef(2.0F, 2.0F, 2.0F);
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glEnable(2929);
      GL11.glDisable(2848);
      GL11.glHint(3154, 4352);
      GL11.glHint(3155, 4352);
   }


   public static void drawRoundedRect(float x, float y, float x2, float y2, float round, int color) {
      x = (float) ((double) x + ((double) (round / 2.0f) + 0.5));
      y = (float) ((double) y + ((double) (round / 2.0f) + 0.5));
      x2 = (float) ((double) x2 - ((double) (round / 2.0f) + 0.5));
      y2 = (float) ((double) y2 - ((double) (round / 2.0f) + 0.5));
      drawRect(x, y, x2, y2, color);
      drawCircle((int)(x2 - round / 2.0f), (int)(y + round / 2.0f), round, color);
      drawCircle((int)(x + round / 2.0f), (int)(y2 - round / 2.0f), round, color);
      drawCircle((int)(x + round / 2.0f), (int)(y + round / 2.0f), round, color);
      drawCircle((int)(x2 - round / 2.0f), (int)(y2 - round / 2.0f), round, color);
      drawRect(x - round / 2.0f - 0.5f, y + round / 2.0f, x2, y2 - round / 2.0f, color);
      drawRect(x, y + round / 2.0f, x2 + round / 2.0f + 0.5f, y2 - round / 2.0f, color);
      drawRect(x + round / 2.0f, y - round / 2.0f - 0.5f, x2 - round / 2.0f, y2 - round / 2.0f, color);
      drawRect(x + round / 2.0f, y, x2 - round / 2.0f, y2 + round / 2.0f + 0.5f, color);
   }

   public static void drawRoundedRect(float x, float y, float x1, float y1, int borderC, int insideC) {
      enableGL2D();
      GL11.glScalef((float) 0.5f, (float) 0.5f, (float) 0.5f);
      drawVLine(x *= 2.0f, (y *= 2.0f) + 1.0f, (y1 *= 2.0f) - 2.0f, borderC);
      drawVLine((x1 *= 2.0f) - 1.0f, y + 1.0f, y1 - 2.0f, borderC);
      drawHLine(x + 2.0f, x1 - 3.0f, y, borderC);
      drawHLine(x + 2.0f, x1 - 3.0f, y1 - 1.0f, borderC);
      drawHLine(x + 1.0f, x + 1.0f, y + 1.0f, borderC);
      drawHLine(x1 - 2.0f, x1 - 2.0f, y + 1.0f, borderC);
      drawHLine(x1 - 2.0f, x1 - 2.0f, y1 - 2.0f, borderC);
      drawHLine(x + 1.0f, x + 1.0f, y1 - 2.0f, borderC);
      HUDUtils.drawRect(x + 1.0f, y + 1.0f, x1 - 1.0f, y1 - 1.0f, insideC);
      GL11.glScalef((float) 2.0f, (float) 2.0f, (float) 2.0f);
      disableGL2D();
   }

   public static void drawHLine(float x, float y, float x1, int y1) {
      if (y < x) {
         float var5 = x;
         x = y;
         y = var5;
      }
      HUDUtils.drawRect(x, x1, y + 1.0F, x1 + 1.0F, y1);
   }

   public static void drawVLine(float x, float y, float x1, int y1) {
      if (x1 < y) {
         float var5 = y;
         y = x1;
         x1 = var5;
      }
      HUDUtils.drawRect(x, y + 1.0F, x + 1.0F, x1, y1);
   }

   public static void enableGL2D() {
      GL11.glDisable(GL11.GL_DEPTH_TEST);
      GL11.glEnable(GL11.GL_BLEND);
      GL11.glDisable(GL11.GL_TEXTURE_2D);
      GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
      GL11.glDepthMask(true);
      GL11.glEnable(GL11.GL_LINE_SMOOTH);
      GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
      GL11.glHint(GL11.GL_POLYGON_SMOOTH_HINT, GL11.GL_NICEST);
   }

   public static void disableGL2D() {
      GL11.glEnable(GL11.GL_TEXTURE_2D);
      GL11.glDisable(GL11.GL_BLEND);
      GL11.glEnable(GL11.GL_DEPTH_TEST);
      GL11.glDisable(GL11.GL_LINE_SMOOTH);
      GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_DONT_CARE);
      GL11.glHint(GL11.GL_POLYGON_SMOOTH_HINT, GL11.GL_DONT_CARE);
   }

   public static int reAlpha(int color, float alpha) {
      Color c = new Color(color);
      float r = ((float) 1 / 255) * c.getRed();
      float g = ((float) 1 / 255) * c.getGreen();
      float b = ((float) 1 / 255) * c.getBlue();
      return new Color(r, g, b, alpha).getRGB();
   }

   public static void drawBorderedCircle(int x, int y, float radius, int outsideC, int insideC) {
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glPushMatrix();
      float scale = 0.1F;
      GL11.glScalef(scale, scale, scale);
      x = (int) (x * (1.0F / scale));
      y = (int) (y * (1.0F / scale));
      radius *= 1.0F / scale;
      drawCircle(x, y, radius, insideC);
      drawUnfilledCircle(x, y, radius, 1.0F, outsideC);
      GL11.glScalef(1.0F / scale, 1.0F / scale, 1.0F / scale);
      GL11.glPopMatrix();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
   }

   public static void drawUnfilledCircle(int x, int y, float radius, float lineWidth, int color) {
      float alpha = (color >> 24 & 0xFF) / 255.0F;
      float red = (color >> 16 & 0xFF) / 255.0F;
      float green = (color >> 8 & 0xFF) / 255.0F;
      float blue = (color & 0xFF) / 255.0F;
      GL11.glColor4f(red, green, blue, alpha);
      GL11.glLineWidth(lineWidth);
      GL11.glEnable(2848);
      GL11.glBegin(2);
      for (int i = 0; i <= 360; i++) {
         GL11.glVertex2d(x + Math.sin(i * 3.141526D / 180.0D) * radius, y + Math.cos(i * 3.141526D / 180.0D) * radius);
      }
      GL11.glEnd();
      GL11.glDisable(2848);
   }

   public static void drawCircle(int x, int y, float radius, int color) {
      float alpha = (color >> 24 & 0xFF) / 255.0F;
      float red = (color >> 16 & 0xFF) / 255.0F;
      float green = (color >> 8 & 0xFF) / 255.0F;
      float blue = (color & 0xFF) / 255.0F;
      GL11.glColor4f(red, green, blue, alpha);
      GL11.glBegin(9);
      for (int i = 0; i <= 360; i++) {
         GL11.glVertex2d(x + Math.sin(i * 3.141526D / 180.0D) * radius, y + Math.cos(i * 3.141526D / 180.0D) * radius);
      }
      GL11.glEnd();
   }

   public static double distance(float x, float y, float x1, float y1) {
      return Math.sqrt((x - x1) * (x - x1) + (y - y1) * (y - y1));
   }
   public static void drawRect(double left, double top, double right, double bottom, int color) {
      int j;
      if (left < right) {
         j = (int) left;
         left = right;
         right = j;
      }

      if (top < bottom) {
         j = (int) top;
         top = bottom;
         bottom = j;
      }

      float f3 = (float)(color >> 24 & 255) / 255.0F;
      float f = (float)(color >> 16 & 255) / 255.0F;
      float f1 = (float)(color >> 8 & 255) / 255.0F;
      float f2 = (float)(color & 255) / 255.0F;
      net.minecraft.client.renderer.Tessellator tessellator = net.minecraft.client.renderer.Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();
      GlStateManager.enableBlend();
      GlStateManager.disableTexture2D();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.color(f, f1, f2, f3);
      worldrenderer.begin(7, DefaultVertexFormats.POSITION);
      worldrenderer.pos((double)left, (double)bottom, 0.0D).endVertex();
      worldrenderer.pos((double)right, (double)bottom, 0.0D).endVertex();
      worldrenderer.pos((double)right, (double)top, 0.0D).endVertex();
      worldrenderer.pos((double)left, (double)top, 0.0D).endVertex();
      tessellator.draw();
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
   }

   public static void su() {
      try {
         timerField = Minecraft.class.getDeclaredField("field_71428_T");
      } catch (Exception var4) {
         try {
            timerField = Minecraft.class.getDeclaredField("timer");
         } catch (Exception ignored) {}
      }

      if (timerField != null) {
         timerField.setAccessible(true);
      }
   }

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

   public static net.minecraft.util.Timer getTimer() {
      try {
         su();
         return (net.minecraft.util.Timer) timerField.get(mc);
      } catch (IndexOutOfBoundsException | IllegalAccessException var1) {
         return null;
      }
   }

   public static int rainbowDraw(long speed, long... delay) {
      long time = System.currentTimeMillis() + (delay.length > 0 ? delay[0] : 0L);
      return Color.getHSBColor((float)(time % (15000L / speed)) / (15000.0F / (float)speed), 1.0F, 1.0F).getRGB();
   }

   public static int astolfoColorsDraw(int yOffset, int yTotal, float speed) {
      float hue = (float) (System.currentTimeMillis() % (int)speed) + ((yTotal - yOffset) * 9);
      while (hue > speed) {
         hue -= speed;
      }
      hue /= speed;
      if (hue > 0.5) {
         hue = 0.5F - (hue - 0.5f);
      }
      hue += 0.5F;
      return Color.HSBtoRGB(hue, 0.5f, 1F);
   }

   public static int astolfoColorsDraw(int yOffset, int yTotal) {
      return astolfoColorsDraw(yOffset, yTotal, 2900F);
   }

   public static void re(BlockPos bp, int color, boolean shade) {
      if (bp != null) {
         double x = (double)bp.getX() - mc.getRenderManager().viewerPosX;
         double y = (double)bp.getY() - mc.getRenderManager().viewerPosY;
         double z = (double)bp.getZ() - mc.getRenderManager().viewerPosZ;
         GL11.glBlendFunc(770, 771);
         GL11.glEnable(3042);
         GL11.glLineWidth(2.0F);
         GL11.glDisable(3553);
         GL11.glDisable(2929);
         GL11.glDepthMask(false);
         float a = (float)(color >> 24 & 255) / 255.0F;
         float r = (float)(color >> 16 & 255) / 255.0F;
         float g = (float)(color >> 8 & 255) / 255.0F;
         float b = (float)(color & 255) / 255.0F;
         GL11.glColor4d(r, g, b, a);
         RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D));
         if (shade) {
            dbb(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D), r, g, b);
         }

         GL11.glEnable(3553);
         GL11.glEnable(2929);
         GL11.glDepthMask(true);
         GL11.glDisable(3042);
      }
   }

   public static void ee(Entity e, int type, double expand, double shift, int color, boolean damage) {
      if (e instanceof EntityLivingBase) {

         double x = e.lastTickPosX + (e.posX - e.lastTickPosX) * (double)getTimer().renderPartialTicks - mc.getRenderManager().viewerPosX;
         double y = e.lastTickPosY + (e.posY - e.lastTickPosY) * (double)getTimer().renderPartialTicks - mc.getRenderManager().viewerPosY;
         double z = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * (double)getTimer().renderPartialTicks - mc.getRenderManager().viewerPosZ;
         float d = (float)expand / 40.0F;

         if (e instanceof EntityPlayer && damage && ((EntityPlayer)e).hurtTime != 0) {
            color = Color.RED.getRGB();
         }

         GlStateManager.pushMatrix();

         if (type == 3) {
            GL11.glTranslated(x, y - 0.2D, z);
            GL11.glRotated(-mc.getRenderManager().playerViewY, 0.0D, 1.0D, 0.0D);
            GlStateManager.disableDepth();
            GL11.glScalef(0.03F + d, 0.03F + d, 0.03F + d);
            int outline = Color.black.getRGB();
            net.minecraft.client.gui.Gui.drawRect(-20, -1, -26, 75, outline);
            net.minecraft.client.gui.Gui.drawRect(20, -1, 26, 75, outline);
            net.minecraft.client.gui.Gui.drawRect(-20, -1, 21, 5, outline);
            net.minecraft.client.gui.Gui.drawRect(-20, 70, 21, 75, outline);
            if (color != 0) {
               net.minecraft.client.gui.Gui.drawRect(-21, 0, -25, 74, color);
               net.minecraft.client.gui.Gui.drawRect(21, 0, 25, 74, color);
               net.minecraft.client.gui.Gui.drawRect(-21, 0, 24, 4, color);
               net.minecraft.client.gui.Gui.drawRect(-21, 71, 25, 74, color);
            } else {
               int st = rainbowDraw(2L, 0L);
               int en = rainbowDraw(2L, 1000L);
               dGR(-21, 0, -25, 74, st, en);
               dGR(21, 0, 25, 74, st, en);
               net.minecraft.client.gui.Gui.drawRect(-21, 0, 21, 4, en);
               net.minecraft.client.gui.Gui.drawRect(-21, 71, 21, 74, st);
            }

            GlStateManager.enableDepth();
         } else {
            int i;
            if (type == 4) {
               EntityLivingBase en = (EntityLivingBase)e;
               double r = en.getHealth() / en.getMaxHealth();
               int b = (int)(74.0D * r);
               int hc = r < 0.3D ? Color.red.getRGB() : (r < 0.5D ? Color.orange.getRGB() : (r < 0.7D ? Color.yellow.getRGB() : Color.green.getRGB()));
               GL11.glTranslated(x, y - 0.2D, z);
               GL11.glRotated(-mc.getRenderManager().playerViewY, 0.0D, 1.0D, 0.0D);
               GlStateManager.disableDepth();
               GL11.glScalef(0.03F + d, 0.03F + d, 0.03F + d);
               i = (int)(21.0D + shift * 2.0D);
               net.minecraft.client.gui.Gui.drawRect(i, -1, i + 5, 75, Color.black.getRGB());
               net.minecraft.client.gui.Gui.drawRect(i + 1, b, i + 4, 74, Color.DARK_GRAY.getRGB());
               net.minecraft.client.gui.Gui.drawRect(i + 1, 0, i + 4, b, hc);
               GlStateManager.enableDepth();
            } else if (type == 6) {
               d3p(x, y, z, 0.699999988079071D, 45, 1.5F, color, color == 0);
            } else {
               if (color == 0) {
                  color = rainbowDraw(2L, 0L);
               }

               float a = (float)(color >> 24 & 255) / 255.0F;
               float r = (float)(color >> 16 & 255) / 255.0F;
               float g = (float)(color >> 8 & 255) / 255.0F;
               float b = (float)(color & 255) / 255.0F;
               if (type == 5) {
                  GL11.glTranslated(x, y - 0.2D, z);
                  GL11.glRotated(-mc.getRenderManager().playerViewY, 0.0D, 1.0D, 0.0D);
                  GlStateManager.disableDepth();
                  GL11.glScalef(0.03F + d, 0.03F, 0.03F + d);
                  int base = 1;
                  d2p(0.0D, 95.0D, 10, 3, Color.black.getRGB());

                  for(i = 0; i < 6; ++i) {
                     d2p(0.0D, 95 + (10 - i), 3, 4, Color.black.getRGB());
                  }

                  for(i = 0; i < 7; ++i) {
                     d2p(0.0D, 95 + (10 - i), 2, 4, color);
                  }

                  d2p(0.0D, 95.0D, 8, 3, color);
                  GlStateManager.enableDepth();
               } else {
                  AxisAlignedBB bbox = e.getEntityBoundingBox().expand(0.1D + expand, 0.1D + expand, 0.1D + expand);
                  AxisAlignedBB axis = new AxisAlignedBB(bbox.minX - e.posX + x, bbox.minY - e.posY + y, bbox.minZ - e.posZ + z, bbox.maxX - e.posX + x, bbox.maxY - e.posY + y, bbox.maxZ - e.posZ + z);
                  GL11.glBlendFunc(770, 771);
                  GL11.glEnable(3042);
                  GL11.glDisable(3553);
                  GL11.glDisable(2929);
                  GL11.glDepthMask(false);
                  GL11.glLineWidth(2.0F);
                  GL11.glColor4f(r, g, b, a);
                  if (type == 1) {
                     RenderGlobal.drawSelectionBoundingBox(axis);
                  } else if (type == 2) {
                     dbb(axis, r, g, b);
                  }

                  GL11.glEnable(3553);
                  GL11.glEnable(2929);
                  GL11.glDepthMask(true);
                  GL11.glDisable(3042);
               }
            }
         }

         GlStateManager.popMatrix();
      }
   }

   public static void dbb(AxisAlignedBB abb, float r, float g, float b) {
      float a = 0.25F;
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

   public static void dtl(Entity e, int color, float lw) {
      if (e != null) {
         double x = e.lastTickPosX + (e.posX - e.lastTickPosX) * (double)getTimer().renderPartialTicks - mc.getRenderManager().viewerPosX;
         double y = (double)e.getEyeHeight() + e.lastTickPosY + (e.posY - e.lastTickPosY) * (double)getTimer().renderPartialTicks - mc.getRenderManager().viewerPosY;
         double z = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * (double)getTimer().renderPartialTicks - mc.getRenderManager().viewerPosZ;
         float a = (float)(color >> 24 & 255) / 255.0F;
         float r = (float)(color >> 16 & 255) / 255.0F;
         float g = (float)(color >> 8 & 255) / 255.0F;
         float b = (float)(color & 255) / 255.0F;
         GL11.glPushMatrix();
         GL11.glEnable(3042);
         GL11.glEnable(2848);
         GL11.glDisable(2929);
         GL11.glDisable(3553);
         GL11.glBlendFunc(770, 771);
         GL11.glEnable(3042);
         GL11.glLineWidth(lw);
         GL11.glColor4f(r, g, b, a);
         GL11.glBegin(2);
         GL11.glVertex3d(0.0D, mc.thePlayer.getEyeHeight(), 0.0D);
         GL11.glVertex3d(x, y, z);
         GL11.glEnd();
         GL11.glDisable(3042);
         GL11.glEnable(3553);
         GL11.glEnable(2929);
         GL11.glDisable(2848);
         GL11.glDisable(3042);
         GL11.glPopMatrix();
      }
   }

   public static void dGR(int left, int top, int right, int bottom, int startColor, int endColor) {
      int j;
      if (left < right) {
         j = left;
         left = right;
         right = j;
      }

      if (top < bottom) {
         j = top;
         top = bottom;
         bottom = j;
      }

      float f = (float)(startColor >> 24 & 255) / 255.0F;
      float f1 = (float)(startColor >> 16 & 255) / 255.0F;
      float f2 = (float)(startColor >> 8 & 255) / 255.0F;
      float f3 = (float)(startColor & 255) / 255.0F;
      float f4 = (float)(endColor >> 24 & 255) / 255.0F;
      float f5 = (float)(endColor >> 16 & 255) / 255.0F;
      float f6 = (float)(endColor >> 8 & 255) / 255.0F;
      float f7 = (float)(endColor & 255) / 255.0F;
      GlStateManager.disableTexture2D();
      GlStateManager.enableBlend();
      GlStateManager.disableAlpha();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.shadeModel(7425);
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();
      worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
      worldrenderer.pos(right, top, 0.0D).color(f1, f2, f3, f).endVertex();
      worldrenderer.pos(left, top, 0.0D).color(f1, f2, f3, f).endVertex();
      worldrenderer.pos(left, bottom, 0.0D).color(f5, f6, f7, f4).endVertex();
      worldrenderer.pos(right, bottom, 0.0D).color(f5, f6, f7, f4).endVertex();
      tessellator.draw();
      GlStateManager.shadeModel(7424);
      GlStateManager.disableBlend();
      GlStateManager.enableAlpha();
      GlStateManager.enableTexture2D();
   }

   public static void db(int w, int h, int r) {
      int c = r == -1 ? -1089466352 : r;
      net.minecraft.client.gui.Gui.drawRect(0, 0, w, h, c);
   }

   public static void drawColouredText(String text, char lineSplit, int leftOffset, int topOffset, long colourParam1, long shift, boolean rect, FontRenderer fontRenderer) {
      int bX = leftOffset;
      int l = 0;
      long colourControl = 0L;

      for(int i = 0; i < text.length(); ++i) {
         char c = text.charAt(i);
         if (c == lineSplit) {
            ++l;
            leftOffset = bX;
            topOffset += fontRenderer.FONT_HEIGHT + 5;
            //reseting text colour?
            colourControl = shift * (long)l;
         } else {
            fontRenderer.drawString(String.valueOf(c), (float)leftOffset, (float)topOffset, astolfoColorsDraw((int)colourParam1, (int)colourControl), rect);
            leftOffset += fontRenderer.getCharWidth(c);
            if (c != ' ') {
               colourControl -= 90L;
            }
         }
      }

   }

   public static PositionMode getPostitionMode(int marginX, int marginY, double height, double width) {
      int halfHeight = (int)(height / 4);
      int halfWidth = (int)(width / 1);
      PositionMode positionMode = null;
      // up left

      if(marginY < halfHeight) {
         if(marginX < halfWidth) {
            positionMode = PositionMode.UPLEFT;
         }
         if(marginX > halfWidth) {
            positionMode = PositionMode.UPRIGHT;
         }
      }

      if(marginY > halfHeight) {
         if(marginX < halfWidth) {
            positionMode = PositionMode.DOWNLEFT;
         }
         if(marginX > halfWidth) {
            positionMode = PositionMode.DOWNRIGHT;
         }
      }


      //////System.out.println(height + " " + halfHeight + " || " + width + " " + halfWidth + " || " + marginX + " " + marginY + " || " + positionMode);

      return positionMode;
   }

   public static void d2p(double x, double y, int radius, int sides, int color) {
      float a = (float)(color >> 24 & 255) / 255.0F;
      float r = (float)(color >> 16 & 255) / 255.0F;
      float g = (float)(color >> 8 & 255) / 255.0F;
      float b = (float)(color & 255) / 255.0F;
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();
      GlStateManager.enableBlend();
      GlStateManager.disableTexture2D();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.color(r, g, b, a);
      worldrenderer.begin(6, DefaultVertexFormats.POSITION);

      for(int i = 0; i < sides; ++i) {
         double angle = 6.283185307179586D * (double)i / (double)sides + Math.toRadians(180.0D);
         worldrenderer.pos(x + Math.sin(angle) * (double)radius, y + Math.cos(angle) * (double)radius, 0.0D).endVertex();
      }

      tessellator.draw();
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
   }

   public static void d3p(double x, double y, double z, double radius, int sides, float lineWidth, int color, boolean chroma) {
      float a = (float)(color >> 24 & 255) / 255.0F;
      float r = (float)(color >> 16 & 255) / 255.0F;
      float g = (float)(color >> 8 & 255) / 255.0F;
      float b = (float)(color & 255) / 255.0F;
      mc.entityRenderer.disableLightmap();
      GL11.glDisable(3553);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(2929);
      GL11.glEnable(2848);
      GL11.glDepthMask(false);
      GL11.glLineWidth(lineWidth);
      if (!chroma) {
         GL11.glColor4f(r, g, b, a);
      }

      GL11.glBegin(1);
      long d = 0L;
      long ed = 15000L / (long)sides;
      long hed = ed / 2L;

      for(int i = 0; i < sides * 2; ++i) {
         if (chroma) {
            if (i % 2 != 0) {
               if (i == 47) {
                  d = hed;
               }

               d += ed;
            }

            int c = rainbowDraw(2L, d);
            float r2 = (float)(c >> 16 & 255) / 255.0F;
            float g2 = (float)(c >> 8 & 255) / 255.0F;
            float b2 = (float)(c & 255) / 255.0F;
            GL11.glColor3f(r2, g2, b2);
         }

         double angle = 6.283185307179586D * (double)i / (double)sides + Math.toRadians(180.0D);
         GL11.glVertex3d(x + Math.cos(angle) * radius, y, z + Math.sin(angle) * radius);
      }

      GL11.glEnd();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glDepthMask(true);
      GL11.glDisable(2848);
      GL11.glEnable(2929);
      GL11.glDisable(3042);
      GL11.glEnable(3553);
      mc.entityRenderer.enableLightmap();
   }



   public static void drawBoundingBox(double x, double y, double z, double width, double height, float red,
                                      float green, float blue, float alpha) {
      GlStateManager.pushMatrix();
      GlStateManager.enableBlend();
      GlStateManager.disableTexture2D();
      GlStateManager.disableDepth();
      GlStateManager.color(red, green, blue, alpha);
      drawBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
      GlStateManager.color(1, 1, 1, 1);
      GlStateManager.enableDepth();
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
      GlStateManager.popMatrix();
   }

   public enum PositionMode {
      UPLEFT,
      UPRIGHT,
      DOWNLEFT,
      DOWNRIGHT
   }
}
