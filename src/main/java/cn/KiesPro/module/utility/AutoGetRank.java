package cn.KiesPro.module.utility;

import java.util.Random;

import cn.KiesPro.module.Category;
import cn.KiesPro.module.Module;
import cn.KiesPro.utils.MathUtils;
import cn.KiesPro.utils.TimerUtils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AutoGetRank extends Module {
	
	private TimerUtils time = new TimerUtils();
	
	String rank = " I Want to get Rank, thank you";
	String[] message = new String[]{
			"VIPPPPPPPPPPPPPPPPPPPPPPPPPPPPP",
			"I Want to get Rank thank you",
			
					
			};

	public AutoGetRank() {
		super("GetRank", "Auto spammer to get Rank", Category.Utility);
	}
	
	@SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent e) {
		if (time.hasReached(MathUtils.randomNumber(50000, 1000) + 10000)) {
			mc.thePlayer.sendChatMessage(Minecraft.getSystemTime() / 100 + rank);
			time.reset();
		}
	}
}
