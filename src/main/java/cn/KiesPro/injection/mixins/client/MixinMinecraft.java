package cn.KiesPro.injection.mixins.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import cn.KiesPro.Client;
import cn.KiesPro.event.eventapi.EventManager;
import cn.KiesPro.event.events.EventTick;
import net.minecraft.client.Minecraft;

@Mixin(Minecraft.class)
public class MixinMinecraft {

	
    @Inject(method = "runTick", at = @At("HEAD"))
    private void runTick(CallbackInfo callbackInfo) {
//        EventManager.call(new EventTick());
        
        if (Client.instance.moduleManager.getModule("NoHurtCam").isToggled()) {
            callbackInfo.cancel();
        }
    }
}
