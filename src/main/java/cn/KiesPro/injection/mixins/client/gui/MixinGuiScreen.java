package cn.KiesPro.injection.mixins.client.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

@Mixin(GuiScreen.class)
public class MixinGuiScreen {
	
	/*
	 * GuiScreenResourcePacksMixin_ClearHandles.java
	 * bugfixes
	 * Character input fix
	 */
    @Redirect(method = "handleKeyboardInput", at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Keyboard;getEventKeyState()Z", remap = false))
    private boolean patcher$checkCharacter() {
        return Keyboard.getEventKey() == 0 && Keyboard.getEventCharacter() >= ' ' || Keyboard.getEventKeyState();
    }
}
