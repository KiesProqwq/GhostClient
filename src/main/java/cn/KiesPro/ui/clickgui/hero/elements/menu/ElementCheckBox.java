package cn.KiesPro.ui.clickgui.hero.elements.menu;

import java.awt.Color;

import cn.KiesPro.settings.Setting;
import cn.KiesPro.ui.clickgui.hero.elements.Element;
import cn.KiesPro.ui.clickgui.hero.elements.ModuleButton;
import cn.KiesPro.utils.RenderUtil;
import cn.KiesPro.utils.color.ColorUtils;
import cn.KiesPro.utils.hero.FontUtil;
import net.minecraft.client.gui.Gui;

/**
 *  Made by HeroCode
 *  it's free to use
 *  but you have to credit me
 *
 *  @author HeroCode
 */
public class ElementCheckBox extends Element {
	/*
	 * Konstrukor
	 */
	public ElementCheckBox(ModuleButton iparent, Setting iset) {
		parent = iparent;
		set = iset;
		super.setup();
	}

	/*
	 * Rendern des Elements 
	 */
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		Color temp = ColorUtils.getClickGUIColor();
		int color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 200).getRGB();
		
		/*
		 * Die Box und Umrandung rendern
		 */
		RenderUtil.drawRect(x, y, x + width, y + height, 0xff1a1a1a);

		/*
		 * Titel und Checkbox rendern.
		 */
		FontUtil.drawString(setstrg, x + width - FontUtil.getStringWidth(setstrg), y + FontUtil.getFontHeight() / 2 - 0.5, 0xffffffff);
		RenderUtil.drawRect(x + 1, y + 2, x + 12, y + 13, set.getValBoolean() ? color : 0xff000000);
		if (isCheckHovered(mouseX, mouseY))
			RenderUtil.drawRect(x + 1, y + 2, x + 12, y + 13, 0x55111111);
	}

	/*
	 * 'true' oder 'false' bedeutet hat der Nutzer damit interagiert und
	 * sollen alle anderen Versuche der Interaktion abgebrochen werden?
	 */
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
		if (mouseButton == 0 && isCheckHovered(mouseX, mouseY)) {
			set.setValBoolean(!set.getValBoolean());
			return true;
		}
		
		return super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	/*
	 * Einfacher HoverCheck, ben�tigt damit die Value ge�ndert werden kann
	 */
	public boolean isCheckHovered(int mouseX, int mouseY) {
		return mouseX >= x + 1 && mouseX <= x + 12 && mouseY >= y + 2 && mouseY <= y + 13;
	}
}
