package cn.KiesPro.module.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.entity.Render;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.Sys;

import cn.KiesPro.Client;
import cn.KiesPro.module.Category;
import cn.KiesPro.module.Module;
import cn.KiesPro.settings.Setting;
import cn.KiesPro.utils.SortUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class HUD extends Module {
	
    private boolean watermark, background, textShadow, active;
    private int margin;
    private int waterMarkMargin, topOffSet, rightOffSet, miniboxWidth;
    private int modColor = 0xFFFFFF;
    private int wmColor = 0xFF4500;
    private String sortMode;
    public ArrayList<Module> modList;

    public HUD() {
        super("HUD", "Draws the module list on your screen", Category.RENDER);

        ArrayList<String> sort = new ArrayList<String>();
        sort.add("Length long > short");
        sort.add("Length short > long");
        sort.add("Alphabet");
        sort.add("idfc");

        Setting waterMark = new Setting("Watermark", this, true);
        Setting background = new Setting("Background", this, false);
        Setting textShadow = new Setting("Text Shadow", this, true);
        Setting arrayListSort = new Setting("Arraylist sort", this, "Length long > short", sort);

        Setting margin = new Setting("Module padding", this, 2, 0, 10, true);
        Setting waterMarkMargin = new Setting("Watermark padding", this, 3, 0, 10, true);
        Setting miniboxWidth = new Setting("Mini box width", this, 1, 0, 10, true);
        Setting topOffSet = new Setting("HUD top offset", this, 4, 0, 500, true);
        Setting rightOffSet = new Setting("HUD right offset", this, 4, 0, 1000, true);

        Client.instance.settingsManager.rSetting(arrayListSort);
        Client.instance.settingsManager.rSetting(waterMark);
        Client.instance.settingsManager.rSetting(background);
        Client.instance.settingsManager.rSetting(textShadow);

        Client.instance.settingsManager.rSetting(margin);
        Client.instance.settingsManager.rSetting(waterMarkMargin);
        Client.instance.settingsManager.rSetting(miniboxWidth);
        Client.instance.settingsManager.rSetting(topOffSet);
        Client.instance.settingsManager.rSetting(rightOffSet);
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent egoe) {
        if (Client.instance.destructed){
            return;
        }

        if (egoe.type != RenderGameOverlayEvent.ElementType.CROSSHAIRS || !active) {
            return;
        }

        sortMode = Client.instance.settingsManager.getSettingByName(this, "Arraylist sort").getValString();
        watermark = Client.instance.settingsManager.getSettingByName(this, "Watermark").getValBoolean();
        background = Client.instance.settingsManager.getSettingByName(this, "Background").getValBoolean();
        textShadow = Client.instance.settingsManager.getSettingByName(this, "Text Shadow").getValBoolean();
        // Renders active modules
        ScaledResolution sr = new ScaledResolution(mc);


        margin = (int) Client.instance.settingsManager.getSettingByName(this, "Module padding").getValDouble();
        waterMarkMargin = (int) Client.instance.settingsManager.getSettingByName(this, "Watermark padding").getValDouble();
        topOffSet = (int) Client.instance.settingsManager.getSettingByName(this, "HUD top offset").getValDouble();;
        rightOffSet = (int) Client.instance.settingsManager.getSettingByName(this, "HUD right offset").getValDouble();;
        miniboxWidth = (int) Client.instance.settingsManager.getSettingByName(this, "Mini box width").getValDouble();;

        if(watermark) {
            String waterMarkText = Client.instance.CLIENT_NAME + " " + Client.instance.CLIENT_VERSION.toUpperCase();
            FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
            if(background) {
                Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(waterMarkText) - waterMarkMargin*2 - rightOffSet, topOffSet,sr.getScaledWidth() - rightOffSet, topOffSet + waterMarkMargin * 2 + fr.FONT_HEIGHT, 0x90000000);
                Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(waterMarkText) - waterMarkMargin * 2 - rightOffSet - miniboxWidth, topOffSet,sr.getScaledWidth() - fr.getStringWidth(waterMarkText) - waterMarkMargin * 2 - rightOffSet, topOffSet + waterMarkMargin * 2 + fr.FONT_HEIGHT, 0xffff4500);
            }
            if (textShadow) {
                fr.drawStringWithShadow(waterMarkText, sr.getScaledWidth() - fr.getStringWidth(waterMarkText) - rightOffSet - waterMarkMargin, topOffSet  + waterMarkMargin, wmColor);
            } else {
                fr.drawString(waterMarkText, sr.getScaledWidth() - fr.getStringWidth(waterMarkText) - rightOffSet - waterMarkMargin, topOffSet  + waterMarkMargin, wmColor);
            }

            topOffSet += fr.FONT_HEIGHT + waterMarkMargin*2;
        }


        modList = Client.instance.moduleManager.getModuleList();

        if (sortMode.equalsIgnoreCase("Length long > short")) {
            modList = SortUtil.longShort();
        } else if (sortMode.equalsIgnoreCase("Length short > long")) {
            modList = SortUtil.shortLong();
        } else if (sortMode.equalsIgnoreCase("Alphabet")) {
            modList = SortUtil.abcList();
        }

        for (Module mod : modList) {
            if (mod.visible && mod.isToggled()) {
                FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
                if(background) {
                    Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(mod.getName()) - margin * 2 - rightOffSet, topOffSet,sr.getScaledWidth() - rightOffSet, topOffSet + margin * 2 + fr.FONT_HEIGHT, 0x90000000);
                    Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(mod.getName()) - margin * 2 - rightOffSet - miniboxWidth, topOffSet,sr.getScaledWidth() - fr.getStringWidth(mod.getName()) - margin * 2 - rightOffSet, topOffSet + margin * 2 + fr.FONT_HEIGHT, 0xffff4500);
                }
                if (textShadow) {
                    fr.drawStringWithShadow(mod.getName(), sr.getScaledWidth() - fr.getStringWidth(mod.getName()) - rightOffSet - margin, topOffSet  + margin, modColor);
                } else {
                    fr.drawString(mod.getName(), sr.getScaledWidth() - fr.getStringWidth(mod.getName()) - rightOffSet - margin, topOffSet  + margin, modColor);
                }

                topOffSet += fr.FONT_HEIGHT + margin*2;
            }
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.active = true;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.active = false;
    }
}
