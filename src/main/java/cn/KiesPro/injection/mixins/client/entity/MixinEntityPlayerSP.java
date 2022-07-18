package cn.KiesPro.injection.mixins.client.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import cn.KiesPro.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.C01PacketChatMessage;

@Mixin(EntityPlayerSP.class)
public class MixinEntityPlayerSP {

	@Overwrite
    public void sendChatMessage(String message)
    {
		if (!Client.instance.destructed) {
			
        if (Client.instance.commandManager.execute(message)) {
            return;
        }
        if (message.startsWith(".")) {
            Client.instance.commandManager.execute(message.replace(".", " "));
            return;
        }
	}
        Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage(message));
    }	

}
