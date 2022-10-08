package cn.KiesPro.module.movement;

import cn.KiesPro.event.eventapi.EventTarget;
import cn.KiesPro.event.events.EventMove;
import cn.KiesPro.module.Category;
import cn.KiesPro.module.Module;

public class NoSlow extends Module {

	public NoSlow() {
		super("NoSlow", "No Block Slow", Category.MOVEMENT);
	}
	
	@EventTarget
	public void onMove(EventMove e) {
		
	}

}
