package cn.KiesPro.module.blatant;

import cn.KiesPro.Client;
import cn.KiesPro.module.Category;
import cn.KiesPro.module.Module;

public class BlatantMode extends Module {
	private boolean count = false;

	public BlatantMode() {
		super("BlatantMode", "如果你没有打开这个功能，你无法打开", Category.BLATANT);
	}
	
	@Override
	public void onEnable() {
		if (count = false) {
			Client.instance.sendMessage("请再次点击确认开启");
			count = true;
			Client.instance.moduleManager.getModule("BlatantMode").toggle();
		}
		Client.instance.blatant = true;
	}
	
	@Override
	public void onDisable() {
		Client.instance.blatant = false;
	}
}
