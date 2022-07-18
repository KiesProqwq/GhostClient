package cn.KiesPro.module.combat;

import java.util.ArrayList;
import cn.KiesPro.Client;
import cn.KiesPro.module.Category;
import cn.KiesPro.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class AntiBot extends Module {

    public static ArrayList<EntityPlayer> bots;
    int bot;
    
	public AntiBot() {
		super("AntiBot", "Prevent Bot", Category.COMBAT);
	}
	
	@SubscribeEvent
	public void onTick(PlayerTickEvent event) {
		if (Client.instance.destructed) {
			return;
		}
		
        for (Entity e : mc.theWorld.loadedEntityList) {
            if (e == mc.thePlayer) continue;
            if (e instanceof EntityPlayer) {
                if (!e.getDisplayName().getFormattedText().startsWith("\u00a7") || e.isInvisible()) {
                    mc.theWorld.removeEntity(e);
                }
            }
        }
	}
}
