/*
 * LiquidBounce+ Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/WYSI-Foundation/LiquidBouncePlus/
 */
package cn.KiesPro.injection.mixins.bugfixes;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiVideoSettings;
import org.spongepowered.asm.mixin.Mixin;

import cn.KiesPro.utils.patcher.GameSettingsExt;

@Mixin(GuiVideoSettings.class)
public abstract class GuiVideoSettingsMixin_MipmapSlider extends GuiScreen {
    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        ((GameSettingsExt) mc.gameSettings).patcher$onSettingsGuiClosed();
    }
}
