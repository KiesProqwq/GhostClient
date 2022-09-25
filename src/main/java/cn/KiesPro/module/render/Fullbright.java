package cn.KiesPro.module.render;

import cn.KiesPro.event.eventapi.EventTarget;
import cn.KiesPro.event.events.EventUpdate;
import cn.KiesPro.module.Category;
import cn.KiesPro.module.Module;

public class Fullbright extends Module {
	
	private float gamma = mc.gameSettings.gammaSetting;
	
	public Fullbright() {
		super("Fullbright", "Make your Bright", Category.RENDER);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate e) {
		mc.gameSettings.gammaSetting = 10f;
	}
	
    @Override
    public void onDisable() {
        super.onDisable();
        mc.gameSettings.gammaSetting = 1.0f;
    }

}
