package cn.KiesPro.injection.mixins.client.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import cn.KiesPro.Client;
import cn.KiesPro.event.eventapi.EventManager;
import cn.KiesPro.event.events.EventChat;
import cn.KiesPro.event.events.EventUpdate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.C01PacketChatMessage;

@Mixin(EntityPlayerSP.class)
public class MixinEntityPlayerSP {

	
    @Inject(method="onUpdate", at={@At(value="HEAD")})
    public void onUpdate(CallbackInfo callbackInfo) {
        EventManager.call(new EventUpdate());
    }
    
    
	@Overwrite
    public void sendChatMessage(String message)
    {
		EventChat event = new EventChat(message);
		EventManager.call(event);
		
		if (event.isCancelled()) {
			return;
		}
		
        Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage(message));
    }	
}
