package cn.KiesPro.ui;

import java.awt.Color;

import cn.KiesPro.ui.font.FontLoaders;
import cn.KiesPro.utils.RenderUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.ResourceLocation;

public class NewMainMenu extends GuiScreen {

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
//        this.drawGradientRect(0, 0, this.width, this.height, 0x00FFFFFF, 0x00FFFFFF);
    	ScaledResolution sr = new ScaledResolution(mc);
        GlStateManager.pushMatrix();
        RenderUtil.drawImage(new ResourceLocation("KiesPro/background.png"), 0.0, 0.0, sr.getScaledWidth_double(), sr.getScaledWidth());

        GlStateManager.popMatrix();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
