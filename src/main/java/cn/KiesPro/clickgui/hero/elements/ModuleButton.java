package cn.KiesPro.clickgui.hero.elements;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import cn.KiesPro.Client;
import cn.KiesPro.clickgui.hero.Panel;
import cn.KiesPro.clickgui.hero.elements.menu.ElementCheckBox;
import cn.KiesPro.clickgui.hero.elements.menu.ElementComboBox;
import cn.KiesPro.clickgui.hero.elements.menu.ElementSlider;
import cn.KiesPro.module.Module;
import cn.KiesPro.settings.Setting;
import cn.KiesPro.utils.RenderUtil;
import cn.KiesPro.utils.color.ColorUtils;
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
public class ModuleButton {
	public Module mod;
	public ArrayList<Element> menuelements;
	public Panel parent;
	public double x;
	public double y;
	public double width;
	public double height;
	public boolean extended = false;
	public boolean listening = false;

	/*
	 * Konstrukor
	 */
	public ModuleButton(Module imod, Panel pl) {
		mod = imod;
		height = Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + 2;
		parent = pl;
		menuelements = new ArrayList<>();
		/*
		 * Settings wurden zuvor in eine ArrayList eingetragen
		 * dieses SettingSystem hat 3 Konstruktoren je nach
		 *  verwendetem Konstruktor �ndert sich die Value
		 *  bei .isCheck() usw. so kann man ganz einfach ohne
		 *  irgendeinen Aufwand bestimmen welches Element
		 *  f�r ein Setting ben�tigt wird :>
		 */
		if (Client.instance.settingsManager.getSettingsByMod(imod) != null)
			for (Setting s : Client.instance.settingsManager.getSettingsByMod(imod)) {
				if (s.isCheck()) {
					menuelements.add(new ElementCheckBox(this, s));
				} else if (s.isSlider()) {
					menuelements.add(new ElementSlider(this, s));
				} else if (s.isCombo()) {
					menuelements.add(new ElementComboBox(this, s));
				}
			}

	}

	/*
	 * Rendern des Elements 
	 */
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		Color temp = ColorUtils.getClickGUIColor();
		int color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 150).getRGB();
		
		/*
		 * Ist das Module an, wenn ja dann soll
		 *  #ein neues Rechteck in Gr��e des Buttons den Knopf als Toggled kennzeichnen
		 *  #sich der Text anders f�rben
		 */
		int textcolor = 0xffafafaf;
		if (mod.isToggled()) {
			RenderUtil.drawRect(x - 2, y, x + width + 2, y + height + 1, color);
			textcolor = 0xffefefef;
		}
		
		/*
		 * Ist die Maus �ber dem Element, wenn ja dann soll der Button sich anders f�rben
		 */
		if (isHovered(mouseX, mouseY)) {
			RenderUtil.drawRect(x - 2, y, x + width + 2, y + height + 1, 0x55111111);
		}
		
		/*
		 * Den Namen des Modules in die Mitte (x und y) rendern
		 */
		FontUtil.drawTotalCenteredStringWithShadow(mod.getName(), x + width / 2, y + 1 + height / 2, textcolor);
	}

	/*
	 * 'true' oder 'false' bedeutet hat der Nutzer damit interagiert und
	 * sollen alle anderen Versuche der Interaktion abgebrochen werden?
	 */
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
		if (!isHovered(mouseX, mouseY))
			return false;

		/*
		 * Rechtsklick, wenn ja dann Module togglen, 
		 */
		if (mouseButton == 0) {
			mod.toggle();
			
			Minecraft.getMinecraft().thePlayer.playSound("random.click", 0.5f, 0.5f);
		} else if (mouseButton == 1) {
			/*
			 * Wenn ein Settingsmenu existiert dann sollen alle Settingsmenus 
			 * geschlossen werden und dieses ge�ffnet/geschlossen werden
			 */
			if (menuelements != null && menuelements.size() > 0) {
				boolean b = !this.extended;
				Client.instance.heroGui.closeAllSettings();
				this.extended = b;
				
				if(extended)Minecraft.getMinecraft().thePlayer.playSound("tile.piston.out", 1f, 1f);else Minecraft.getMinecraft().thePlayer.playSound("tile.piston.in", 1f, 1f);
			}
		} else if (mouseButton == 2) {
			/*
			 * MidClick => Set keybind (wait for next key)
			 */
			listening = true;
		}
		return true;
	}

	public boolean keyTyped(char typedChar, int keyCode) throws IOException {
		/*
		 * Wenn listening, dann soll der n�chster Key (abgesehen 'ESCAPE') als Keybind f�r mod
		 * danach soll nicht mehr gewartet werden!
		 */
		if (listening) {
			if (keyCode != Keyboard.KEY_ESCAPE) {
				Client.instance.sendMessage("Bound '" + mod.getName() + "'" + " to '" + Keyboard.getKeyName(keyCode) + "'");
				mod.setKey(keyCode);
			} else {
				Client.instance.sendMessage("Unbound '" + mod.getName() + "'");
				mod.setKey(Keyboard.KEY_NONE);
			}
			listening = false;
			return true;
		}
		return false;
	}

	public boolean isHovered(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
	}

}
