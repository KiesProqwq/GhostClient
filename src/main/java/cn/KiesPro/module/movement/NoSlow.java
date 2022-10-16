package cn.KiesPro.module.movement;

import java.util.ArrayList;

import cn.KiesPro.event.eventapi.EventTarget;
import cn.KiesPro.event.events.EventPostUpdate;
import cn.KiesPro.event.events.EventPreUpdate;
import cn.KiesPro.module.Category;
import cn.KiesPro.module.Module;
import cn.KiesPro.module.blatant.Aura;
import cn.KiesPro.settings.Setting;
import cn.KiesPro.utils.MoveUtil;
import cn.KiesPro.utils.raven.RavenUtils;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;

public class NoSlow extends Module {
	
    ArrayList<String> modes = new ArrayList<String>();
    public static Setting mode;
    
	public NoSlow() {
		super("NoSlow", "No Block Slow", Category.MOVEMENT);
		modes.add("Vanilla");
		modes.add("Watchdog");
		modes.add("AAC5");
        
        registerSetting(mode = new Setting("Rotaions", this,"Blatant",modes));
	}
	
	@EventTarget
	public void onPreUpdate(EventPreUpdate e) {
		if (!mc.thePlayer.onGround) return;
        switch (mode.getValString()) {
    		case "Watchdog":
        		if (Aura.curTarget != null || !mc.thePlayer.isBlocking() || MoveUtil.isMoving())
        			return;
				if (mc.thePlayer.getItemInUseDuration() >= 1) {
	                mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
	                break;
	            }
        }
	}
	
	@EventTarget
	public void onPostUpdate(EventPostUpdate e) {
		if (!mc.thePlayer.onGround) return;
        switch (mode.getValString()) {
    		case "Watchdog":
        		if (Aura.curTarget != null || !mc.thePlayer.isBlocking() || (MoveUtil.isMoving() || MoveUtil.isOnGround(0.42)))
        			return;
    			mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer.inventory.getCurrentItem(), 0f, 0f, 0f));
                break;
    		case "AAC5":
    			if (mc.thePlayer.isUsingItem() && mc.thePlayer.isBlocking()) {
    				mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer.inventory.getCurrentItem(), 0f, 0f, 0f));
    			}
        }
	}
}
