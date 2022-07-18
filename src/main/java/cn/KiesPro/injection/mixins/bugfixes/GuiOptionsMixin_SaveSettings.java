/*
 * LiquidBounce+ Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/WYSI-Foundation/LiquidBouncePlus/
 */
package cn.KiesPro.injection.mixins.bugfixes;

import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GuiOptions.class)
public class GuiOptionsMixin_SaveSettings extends GuiScreen {
    @Override
    public void onGuiClosed() {
        mc.gameSettings.saveOptions();
    }
}
