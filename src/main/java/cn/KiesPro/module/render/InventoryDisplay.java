package cn.KiesPro.module.render;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import akka.japi.Util;
import cn.KiesPro.Client;
import cn.KiesPro.event.eventapi.EventTarget;
import cn.KiesPro.event.events.EventRender2D;
import cn.KiesPro.module.Category;
import cn.KiesPro.module.Module;
import cn.KiesPro.settings.Setting;
import cn.KiesPro.utils.RenderUtil;
import cn.KiesPro.utils.raven.RavenUtils;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class InventoryDisplay extends Module {
	
    public Setting x;
    public Setting y;
    
	public InventoryDisplay() {
		super("InventoryDisplay", "Show your Inventory", Category.RENDER);
		registerSetting(x = new Setting("x", this, 4, 0, 100, true));
		registerSetting(y = new Setting("y", this, 4, 0, 100, true));
	}

    @EventTarget
    public void onRender(EventRender2D event) {
        if (this.isToggled() && RavenUtils.isPlayerInGame()) {
            GL11.glPushMatrix();
            ScaledResolution sr = new ScaledResolution(mc);
            int x = (int)((double)sr.getScaledWidth() * (this.x.getValDouble() / 100.0));
            int y = (int)((double)sr.getScaledHeight() * (this.y.getValDouble() / 100.0));

            RenderUtil.drawBorderedRoundedRect(x - 2, y + Client.instance.fontManager.robotom15.getHeight() - 4, 182.5f, 80 - (Client.instance.fontManager.robotom15.getHeight() - 4), 3.0f, 2.5f, new Color(19, 19, 19, 50).getRGB(), new Color(200, 1, 100, 1).getRGB());
            RenderUtil.drawRect(x - 2, (float)y + (float)Client.instance.fontManager.robotom15.getHeight() * 2.0f + 2.0f, (float)(x + 180) + 0.5f, (float)y + (float)Client.instance.fontManager.robotom15.getHeight() * 2.0f + 3.5f, new Color(200, 1, 100, 1).getRGB());
            Client.instance.fontManager.robotom15.drawString("Inventory", (float)x + 78.0f, y + Client.instance.fontManager.robotom15.getHeight(), Color.white.getRGB());
            GlStateManager.enableRescaleNormal();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            RenderHelper.enableGUIStandardItemLighting();
            for (int i = 9; i < 36; ++i) {
                ItemStack stack;
                if (i % 9 == 0) {
                    y += 20;
                }
                if ((stack = mc.thePlayer.inventory.getStackInSlot(i)) == null) continue;
                mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x + 20 * (i % 9), y);
                this.renderItemOverlayIntoGUI(stack, x + 20 * (i % 9), y);
            }
            RenderHelper.disableStandardItemLighting();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();
            GL11.glPopMatrix();
        }
    }
    public void renderItemOverlayIntoGUI(ItemStack itemStack, int x, int y) {
        if (itemStack != null) {
            if (itemStack.stackSize != 1) {
                String s = String.valueOf(itemStack.stackSize);
                if (itemStack.stackSize < 1) {
                    s = EnumChatFormatting.RED + String.valueOf(itemStack.stackSize);
                }
                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                GlStateManager.disableBlend();
                Client.instance.fontManager.robotom15.drawString(s, (int)((double)(x + 19 - 2) - Client.instance.fontManager.robotom15.getStringWidth(s)), y + 6 + 3, 0xFFFFFF);
                GlStateManager.enableLighting();
                GlStateManager.enableDepth();
            }
            if (itemStack.getItem().showDurabilityBar(itemStack)) {
                double health = itemStack.getItem().getDurabilityForDisplay(itemStack);
                int j = (int)Math.round(13.0 - health * 13.0);
                int i = (int)Math.round(255.0 - health * 255.0);
                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                GlStateManager.disableTexture2D();
                GlStateManager.disableAlpha();
                GlStateManager.disableBlend();
                Tessellator tessellator = Tessellator.getInstance();
                WorldRenderer worldrenderer = tessellator.getWorldRenderer();
                this.draw(worldrenderer, x + 2, y + 13, 13, 2, 0, 0, 0, 255);
                this.draw(worldrenderer, x + 2, y + 13, 12, 1, (255 - i) / 4, 64, 0, 255);
                this.draw(worldrenderer, x + 2, y + 13, j, 1, 255 - i, i, 0, 255);
                GlStateManager.enableAlpha();
                GlStateManager.enableTexture2D();
                GlStateManager.enableLighting();
                GlStateManager.enableDepth();
            }
        }
    }

    private void draw(WorldRenderer p_draw_1_, int p_draw_2_, int p_draw_3_, int p_draw_4_, int p_draw_5_, int p_draw_6_, int p_draw_7_, int p_draw_8_, int p_draw_9_) {
        p_draw_1_.begin(7, DefaultVertexFormats.POSITION_COLOR);
        p_draw_1_.pos(p_draw_2_ + 0, p_draw_3_ + 0, 0.0).color(p_draw_6_, p_draw_7_, p_draw_8_, p_draw_9_).endVertex();
        p_draw_1_.pos(p_draw_2_ + 0, p_draw_3_ + p_draw_5_, 0.0).color(p_draw_6_, p_draw_7_, p_draw_8_, p_draw_9_).endVertex();
        p_draw_1_.pos(p_draw_2_ + p_draw_4_, p_draw_3_ + p_draw_5_, 0.0).color(p_draw_6_, p_draw_7_, p_draw_8_, p_draw_9_).endVertex();
        p_draw_1_.pos(p_draw_2_ + p_draw_4_, p_draw_3_ + 0, 0.0).color(p_draw_6_, p_draw_7_, p_draw_8_, p_draw_9_).endVertex();
        Tessellator.getInstance().draw();
    }
}
