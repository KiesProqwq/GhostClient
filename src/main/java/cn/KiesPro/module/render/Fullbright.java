package cn.KiesPro.module.render;

import cn.KiesPro.Client;
import cn.KiesPro.module.Category;
import cn.KiesPro.module.Module;

public class Fullbright extends Module {
	
	private float gamma = mc.gameSettings.gammaSetting;
	
	public Fullbright() {
		super("Fullbright", "Make your Bright", Category.RENDER);
	}
	
	public void onEnable() {
		if (Client.instance.destructed) {
			return;
		}
		mc.gameSettings.gammaSetting = 1000f;
	}
	
	public void onDisable() {
		mc.gameSettings.gammaSetting = this.gamma;;
	}

}
