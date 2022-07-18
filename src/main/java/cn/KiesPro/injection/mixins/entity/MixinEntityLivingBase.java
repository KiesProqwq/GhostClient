package cn.KiesPro.injection.mixins.entity;


import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import cn.KiesPro.Client;
import net.minecraft.entity.EntityLivingBase;

@Mixin(EntityLivingBase.class)
public class MixinEntityLivingBase {
	
    @Shadow
    private int jumpTicks;
    
    @Inject(method = "onLivingUpdate", at = @At("HEAD"))
    private void headLiving(CallbackInfo callbackInfo) {
        if (Client.instance.moduleManager.getModule("NoJumpDelay").isToggled())
            jumpTicks = 0;
    }
}
