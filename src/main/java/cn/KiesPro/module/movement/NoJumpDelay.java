package cn.KiesPro.module.movement;

import cn.KiesPro.module.Category;
import cn.KiesPro.module.Module;

/*
 * MixinEntityLivingBase.java
 */

public class NoJumpDelay extends Module {

	public NoJumpDelay() {
		super("NoJumpDelay", "Make your jump ticks = 0", Category.MOVEMENT);
	}
	
	public void onTick() {
		
	}
}
