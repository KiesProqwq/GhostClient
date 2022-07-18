package cn.KiesPro.module.render;

import cn.KiesPro.module.Category;
import cn.KiesPro.module.Module;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoBob extends Module {

	public NoBob() {
		super("NoBob", "Removes the bobbing animation", Category.RENDER);
	}
	
	@SubscribeEvent
	public void onRender(RenderHandEvent e) {
		mc.thePlayer.distanceWalkedModified = 0f;
	}
}
