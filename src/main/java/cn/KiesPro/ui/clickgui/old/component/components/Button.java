package cn.KiesPro.ui.clickgui.old.component.components;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import cn.KiesPro.Client;
import cn.KiesPro.module.Module;
import cn.KiesPro.settings.Setting;
import cn.KiesPro.ui.clickgui.old.ClickGui;
import cn.KiesPro.ui.clickgui.old.component.Component;
import cn.KiesPro.ui.clickgui.old.component.Frame;
import cn.KiesPro.ui.clickgui.old.component.components.sub.Checkbox;
import cn.KiesPro.ui.clickgui.old.component.components.sub.Keybind;
import cn.KiesPro.ui.clickgui.old.component.components.sub.ModeButton;
import cn.KiesPro.ui.clickgui.old.component.components.sub.Slider;
import cn.KiesPro.ui.clickgui.old.component.components.sub.VisibleButton;
import cn.KiesPro.utils.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class Button extends Component {

	public Module mod;
	public Frame parent;
	public int offset;
	private boolean isHovered;
	private ArrayList<Component> subcomponents;
	public boolean open;
	private int height;
	private	int tooltipX;
	private int tooltipY;
	
	public Button(Module mod, Frame parent, int offset) {
		this.mod = mod;
		this.parent = parent;
		this.offset = offset;
		this.subcomponents = new ArrayList<Component>();
		this.open = false;
		height = 12;
		int opY = offset + 12;
		if(Client.instance.settingsManager.getSettingsByMod(mod) != null) {
			for(Setting s : Client.instance.settingsManager.getSettingsByMod(mod)){
				if(s.isCombo()){
					this.subcomponents.add(new ModeButton(s, this, mod, opY));
					opY += 12;
				}
				if(s.isSlider()){
					this.subcomponents.add(new Slider(s, this, opY));
					opY += 12;
				}
				if(s.isCheck()){
					this.subcomponents.add(new Checkbox(s, this, opY));
					opY += 12;
				}
			}
		}
		this.subcomponents.add(new Keybind(this, opY));
		this.subcomponents.add(new VisibleButton(this, mod, opY));
	}
	
	@Override
	public void setOff(int newOff) {
		offset = newOff;
		int opY = offset + 12;
		for(Component comp : this.subcomponents) {
			comp.setOff(opY);
			opY += 12;
		}
	}
	
	public void renderTooltip(String name) {
			RenderUtil.drawBorderedCorneredRect(parent.getWidth() / 2 + tooltipX - 54, this.parent.barHeight + tooltipY - 3, parent.getWidth() / 2 + tooltipX + Minecraft.getMinecraft().fontRendererObj.getStringWidth(name) - 45, this.parent.barHeight + tooltipY + 12, 2, 0x95000000, 0x80000000);
			Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(name, parent.getWidth() / 2 + tooltipX - 50, (this.parent.barHeight + tooltipY), -1);
			RenderUtil.startClip(parent.getWidth() / 2 + tooltipX - 54, this.parent.barHeight + tooltipY - 3, parent.getWidth() / 2 + tooltipX + Minecraft.getMinecraft().fontRendererObj.getStringWidth(name) - 45, this.parent.barHeight + tooltipY + 12);
			RenderUtil.endClip();
	}
	
	@Override
	public void renderComponent() {
		Gui.drawRect(parent.getX(), this.parent.getY() + this.offset, parent.getX() + parent.getWidth(), this.parent.getY() + 12 + this.offset, this.isHovered ? (this.mod.isToggled() ? new Color(0xFF222222).darker().getRGB() : 0xFF222222) : (this.mod.isToggled() ? new Color(14,14,14).getRGB() : 0xFF111111));
		GL11.glPushMatrix();
		GL11.glScalef(0.5f,0.5f, 0.5f);
		Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.mod.getName(), (parent.getX() + 2) * 2, (parent.getY() + offset + 2) * 2 + 4, this.mod.isToggled() ? 0x999999 : -1);
		if(this.subcomponents.size() > 2)
		Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.open ? "-" : "+", (parent.getX() + parent.getWidth() - 10) * 2, (parent.getY() + offset + 2) * 2 + 4, -1);
		GL11.glPopMatrix();
		if(this.open) {
			if(!this.subcomponents.isEmpty()) {
				for(Component comp : this.subcomponents) {
					comp.renderComponent();
				}
				Gui.drawRect(parent.getX() + 2, parent.getY() + this.offset + 12, parent.getX() + 3, parent.getY() + this.offset + ((this.subcomponents.size() + 1) * 12), ClickGui.color);
			}
		}
		if (this.isHovered) {
			renderTooltip(mod.getDescription());
		}
	}
	
	@Override
	public int getHeight() {
		if(this.open) {
			return (12 * (this.subcomponents.size() + 1));
		}
		return 12;
	}
	
	public void updateTooltipPosition(int mouseX, int mouseY) {
		tooltipX = mouseX + 18;
		tooltipY = mouseY - 18;
	}
	
	@Override
	public void updateComponent(int mouseX, int mouseY) {
		this.isHovered = isMouseOnButton(mouseX, mouseY);
		updateTooltipPosition(mouseX, mouseY);
		if(!this.subcomponents.isEmpty()) {
			for(Component comp : this.subcomponents) {
				comp.updateComponent(mouseX, mouseY);
			}
		}
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		if(isMouseOnButton(mouseX, mouseY) && button == 0) {
			this.mod.toggle();
		}
		if(isMouseOnButton(mouseX, mouseY) && button == 1) {
			this.open = !this.open;
			this.parent.refresh();
		}
		for(Component comp : this.subcomponents) {
			comp.mouseClicked(mouseX, mouseY, button);
		}
	}
	
	@Override
	public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
		for(Component comp : this.subcomponents) {
			comp.mouseReleased(mouseX, mouseY, mouseButton);
		}
	}
	
	@Override
	public void keyTyped(char typedChar, int key) {
		for(Component comp : this.subcomponents) {
			comp.keyTyped(typedChar, key);
		}
	}
	
	public boolean isMouseOnButton(int x, int y) {
		if(x > parent.getX() && x < parent.getX() + parent.getWidth() && y > this.parent.getY() + this.offset && y < this.parent.getY() + 12 + this.offset) {
			return true;
		}
		return false;
	}
}
