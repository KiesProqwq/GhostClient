package cn.KiesPro.module.render;

import org.lwjgl.input.Keyboard;

import cn.KiesPro.Client;
import cn.KiesPro.module.Category;
import cn.KiesPro.module.Module;
import cn.KiesPro.settings.Setting;

public class ClickGUI extends Module {

	public ClickGUI() {
		super("ClickGUI", "Allows you to enable and disable modules", Category.RENDER);
        Client.instance.settingsManager.rSetting(new Setting("ClickGui:GuiRed", (Module)this, 255.0, 0.0, 255.0, true));
        Client.instance.settingsManager.rSetting(new Setting("ClickGui:GuiGreen", (Module)this, 0.0, 0.0, 255.0, true));
        Client.instance.settingsManager.rSetting(new Setting("ClickGui:GuiBlue", (Module)this, 0.0, 0.0, 255.0, true));
        
		this.setKey(Keyboard.KEY_RSHIFT);
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
		mc.displayGuiScreen(Client.instance.oldClickGui);
		this.setToggled(false);
	}
}
