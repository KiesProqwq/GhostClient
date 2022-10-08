package cn.KiesPro.module.movement;

import cn.KiesPro.event.eventapi.EventTarget;
import cn.KiesPro.event.events.EventMove;
import cn.KiesPro.module.Category;
import cn.KiesPro.module.Module;

public class Speed extends Module {

	public Speed() {
		super("Speed", "BHOP", Category.MOVEMENT);
	}
	
	@EventTarget
	public void onMove(EventMove e) {
		
	}

}
