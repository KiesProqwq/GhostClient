package cn.KiesPro.module.misc;

import cn.KiesPro.Client;
import cn.KiesPro.module.Category;
import cn.KiesPro.module.Module;

public class SelfDestruct extends Module {

	public SelfDestruct() {
		super("SelfDestruct", "", Category.MISC);
	}

	@Override
	public void onEnable() {
		Client.instance.onDestruct();
	}
	
}
