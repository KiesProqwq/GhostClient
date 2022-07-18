/*
 * LiquidBounce+ Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/WYSI-Foundation/LiquidBouncePlus/
 */
package cn.KiesPro.injection.mixins.bugfixes.crashes;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import cn.KiesPro.utils.patcher.ResolutionHelper;

@Mixin(GuiScreen.class)
public class GuiScreenMixin_ResolveCrash {

    @Shadow public Minecraft mc;

    @SuppressWarnings({"ConstantConditions", "RedundantCast"})
    @Inject(method = "handleInput", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiScreen;handleKeyboardInput()V"), cancellable = true)
    private void patcher$checkScreen(CallbackInfo ci) {
        if ((GuiScreen) (Object) this != this.mc.currentScreen) {
            ResolutionHelper.setScaleOverride(-1);
            ci.cancel();
        }
    }
}
