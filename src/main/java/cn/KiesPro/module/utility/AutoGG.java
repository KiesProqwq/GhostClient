package cn.KiesPro.module.utility;

import cn.KiesPro.module.Category;
import cn.KiesPro.module.Module;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/*
 *由于哥们是个懒熊所以火速搞了个AutoGG来刷Karma(好像拼错了)
 * 
 */
public class AutoGG extends Module {
	
    private String[] triggers;
    public static String unformattedMessage;
	
	public AutoGG() {
		super("AutoGG", "When the game finish, send a message 'GG' ", Category.Utility);
	}

	private void onChat(ClientChatReceivedEvent event) throws InterruptedException {
		unformattedMessage = event.message.getUnformattedText();
        unformattedMessage = EnumChatFormatting.getTextWithoutFormattingCodes((String)unformattedMessage);
        
        for (int i = 0; i < this.triggers.length; ++i) {
            if (!unformattedMessage.contains((CharSequence)this.triggers[i])) continue;
            mc.thePlayer.sendChatMessage("/achat gg");
            break;
        }
	}

}
