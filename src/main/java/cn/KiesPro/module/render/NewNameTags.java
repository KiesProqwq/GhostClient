package cn.KiesPro.module.render;

import org.lwjgl.opengl.GL11;

import cn.KiesPro.Client;
import cn.KiesPro.module.Category;
import cn.KiesPro.module.Module;
import cn.KiesPro.settings.Setting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NewNameTags extends Module {
	

	public NewNameTags() {
		super("NewNameTags", "透视名字666", Category.RENDER);
		Client.instance.settingsManager.rSetting(new Setting("scale", this, 1.0, 0.1, 5.0, false));
	}
	
	@SubscribeEvent
    public void onPreRender(RenderLivingEvent.Specials.Pre event) {
		
		//EntityPlayer
		if (event.entity instanceof EntityPlayer && event.entity != mc.thePlayer && event.entity.deathTime == 0) {
            final EntityPlayer entity = (EntityPlayer)event.entity;
            
			event.setCanceled(true);
        
			String playerName = "";
			playerName += entity.getDisplayName().getFormattedText();
			//health
			final double health = entity.getHealth() / entity.getMaxHealth();
            final String healthString = ((health < 0.3) ? EnumChatFormatting.RED.toString() : ((health < 0.5) ? EnumChatFormatting.GOLD.toString() : ((health < 0.7) ? EnumChatFormatting.YELLOW.toString() : EnumChatFormatting.GREEN.toString()))) + rnd(entity.getHealth(), 1);
            playerName = playerName + " " + healthString;
            
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)event.x + 0.0f, (float)event.y + entity.height + 0.5f, (float)event.z);
            GL11.glNormal3f(0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(mc.getRenderManager().playerViewX, 1.0f, 0.0f, 0.0f);
            final float f1 = 0.02666667f;
            GlStateManager.scale(-f1, -f1, f1);
            if (entity.isSneaking()) {
                GlStateManager.translate(0.0f, 9.374999f, 0.0f);
            }
            GlStateManager.disableLighting();
            GlStateManager.depthMask(false);
            GlStateManager.disableDepth();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            final Tessellator tessellator = Tessellator.getInstance();
            final WorldRenderer worldrenderer = tessellator.getWorldRenderer();
            final int i = (int)Client.instance.settingsManager.getSettingByName(this, "scale").getValDouble();
            final int j = (int)(mc.fontRendererObj.getStringWidth(playerName) / 2 * Client.instance.settingsManager.getSettingByName(this, "scale").getValDouble());
            GlStateManager.disableTexture2D();
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
            worldrenderer.pos((double)(-j - 1), (double)(-1 + i), 0.0).color(entity.isSneaking() ? 100.0f : 0.0f, 0.0f, 0.0f, 1.0f).endVertex();
            worldrenderer.pos((double)(-j - 1), (double)(8 + i), 0.0).color(entity.isSneaking() ? 100.0f : 0.0f, 0.0f, 0.0f, 1.0f).endVertex();
            worldrenderer.pos((double)(j + 1), (double)(8 + i), 0.0).color(entity.isSneaking() ? 100.0f : 0.0f, 0.0f, 0.0f, 1.0f).endVertex();
            worldrenderer.pos((double)(j + 1), (double)(-1 + i), 0.0).color(entity.isSneaking() ? 100.0f : 0.0f, 0.0f, 0.0f, 1.0f).endVertex();
            tessellator.draw();
            GlStateManager.enableTexture2D();
            mc.fontRendererObj.drawString(playerName, (int)(-mc.fontRendererObj.getStringWidth(playerName) / 2 * Client.instance.settingsManager.getSettingByName(this, "scale").getValDouble()), i, -1);
            GlStateManager.enableDepth();
            GlStateManager.depthMask(true);
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.popMatrix();
		}
    }
	
	//only skid i dont know mean
    public static double rnd(final double n, final int d) {
        if (d == 0) {
            return (double)Math.round(n);
        }
        final double p = Math.pow(10.0, d);
        return Math.round(n * p) / p;
    }
}
