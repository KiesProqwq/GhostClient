package cn.KiesPro.injection.mixins.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import cn.KiesPro.Client;
import net.minecraft.client.Minecraft;

@Mixin(Minecraft.class)
public class MixinMinecraft {

	
    @Inject(method = "runTick", at = @At("HEAD"))
    private void runTick(CallbackInfo callbackInfo) {
        if (Client.instance.moduleManager.getModule("NoHurtCam").isToggled()) {
            callbackInfo.cancel();
        }
    }
}
