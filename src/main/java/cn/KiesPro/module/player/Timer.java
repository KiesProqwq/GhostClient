package cn.KiesPro.module.player;

import cn.KiesPro.Client;
import cn.KiesPro.event.eventapi.EventTarget;
import cn.KiesPro.event.events.EventUpdate;
import cn.KiesPro.module.Category;
import cn.KiesPro.module.Module;
import cn.KiesPro.settings.Setting;

public class Timer extends Module {

	public Timer() {
		super("Timer", "Update your timer in game", Category.PLAYER);
		registerSetting(new Setting("Speed", this, 1.07D, 0.01D, 2.00D, false));
	}

	@EventTarget
	public void onUpdate(EventUpdate e) {
		if (Client.instance.moduleManager.getModule("Timer").isToggled()) {
			float speed = (float)Client.instance.settingsManager.getSettingByName(this, "Speed").getValDouble();
			mc.timer.timerSpeed = speed;
		} else {
			mc.timer.timerSpeed = 1.00f;
		}

	}
	
	@Override
	public void onDisable() {
	}
}
