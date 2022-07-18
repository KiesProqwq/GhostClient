package cn.KiesPro.clickgui.hero.elements.menu;

import java.awt.Color;

import cn.KiesPro.Client;
import cn.KiesPro.clickgui.hero.elements.Element;
import cn.KiesPro.clickgui.hero.elements.ModuleButton;
import cn.KiesPro.settings.Setting;
import cn.KiesPro.utils.RenderUtil;
import cn.KiesPro.utils.hero.ColorUtil;
import cn.KiesPro.utils.hero.FontUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

/**
 *  Made by HeroCode
 *  it's free to use
 *  but you have to credit me
 *
 *  @author HeroCode
 */
public class ElementComboBox extends Element {
	/*
	 * Konstrukor
	 */
	public ElementComboBox(ModuleButton iparent, Setting iset) {
		parent = iparent;
		set = iset;
		super.setup();
	}

	/*
	 * Rendern des Elements
	 */
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		Color temp = ColorUtil.getClickGUIColor();
		int color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 150).getRGB();
		
		/*
		 * Die Box und Umrandung rendern
		 */
		RenderUtil.drawRect(x, y, x + width, y + height, 0xff1a1a1a);

		FontUtil.drawTotalCenteredString(setstrg, x + width / 2, y + 15/2, 0xffffffff);
		int clr1 = color;
		int clr2 = temp.getRGB();

		RenderUtil.drawRect(x, y + 14, x + width, y + 15, 0x77000000);
		if (comboextended) {
			RenderUtil.drawRect(x, y + 15, x + width, y + height, 0xaa121212);
			double ay = y + 15;
			for (String sld : set.getOptions()) {
				String elementtitle = sld.substring(0, 1).toUpperCase() + sld.substring(1, sld.length());
				FontUtil.drawCenteredString(elementtitle, x + width / 2, ay + 2, 0xffffffff);
				
				/*
				 * Ist das Element ausgew�hlt, wenn ja dann markiere
				 * das Element in der ComboBox
				 */
				if (sld.equalsIgnoreCase(set.getValString())) {
					RenderUtil.drawRect(x, ay, x + 1.5, ay + FontUtil.getFontHeight() + 2, clr1);
				}
				/*
				 * Wie bei mouseClicked 'is hovered', wenn ja dann markiere
				 * das Element in der ComboBox
				 */
				if (mouseX >= x && mouseX <= x + width && mouseY >= ay && mouseY < ay + FontUtil.getFontHeight() + 2) {
					RenderUtil.drawRect(x + width - 1.2, ay, x + width, ay + FontUtil.getFontHeight() + 2, clr2);
				}
				ay += FontUtil.getFontHeight() + 2;
			}
		}
	}

	/*
	 * 'true' oder 'false' bedeutet hat der Nutzer damit interagiert und
	 * sollen alle anderen Versuche der Interaktion abgebrochen werden?
	 */
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
		if (mouseButton == 0) {
			if (isButtonHovered(mouseX, mouseY)) {
				comboextended = !comboextended;
				return true;
			}
			
			/*
			 * Also wenn die Box ausgefahren ist, dann wird f�r jede m�gliche Options
			 * �berpr�ft, ob die Maus auf diese zeigt, wenn ja dann global jeder weitere 
			 * call an mouseClicked gestoppt und die Values werden aktualisiert
			 */
			if (!comboextended)return false;
			double ay = y + 15;
			for (String slcd : set.getOptions()) {
				if (mouseX >= x && mouseX <= x + width && mouseY >= ay && mouseY <= ay + FontUtil.getFontHeight() + 2) {
					
					if(clickgui != null && clickgui.setmgr != null)
						
					//clickgui.setmgr.getSettingByName(set.getName()).setValString(slcd.toLowerCase());
					//idk
						
					return true;
				}
				ay += FontUtil.getFontHeight() + 2;
			}
		}

		return super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	/*
	 * Einfacher HoverCheck, ben�tigt damit die Combobox ge�ffnet und geschlossen werden kann
	 */
	public boolean isButtonHovered(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + 15;
	}
}
