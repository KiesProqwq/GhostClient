package cn.KiesPro.utils.hero;

import java.awt.Color;

import cn.KiesPro.Client;
import cn.KiesPro.module.ModuleManager;


/**
 *  Made by HeroCode
 *  it's free to use
 *  but you have to credit me
 *
 *  @author HeroCode
 */
public class ColorUtil {
	
	public static Color getClickGUIColor(){
		ModuleManager mm = new ModuleManager();
		return new Color((int)Client.instance.settingsManager.getSettingByName(mm.getModule("ClickGUI"),"GuiRed").getValDouble(), (int)Client.instance.settingsManager.getSettingByName(mm.getModule("ClickGUI"),"GuiGreen").getValDouble(), (int)Client.instance.settingsManager.getSettingByName(mm.getModule("ClickGUI"),"GuiBlue").getValDouble());
	}
}
