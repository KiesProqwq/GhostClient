/*
 * LiquidBounce+ Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/WYSI-Foundation/LiquidBouncePlus/
 */
package cn.KiesPro.injection.mixins.bugfixes.crashes;

import net.minecraft.client.gui.ServerListEntryNormal;
import net.minecraft.client.multiplayer.ServerData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerListEntryNormal.class)
public abstract class ServerListEntryNormalMixin_ResolveCrash {

    @Shadow protected abstract void prepareServerIcon();
    @Shadow @Final private ServerData server;

    @Redirect(method = "drawEntry", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/ServerListEntryNormal;prepareServerIcon()V"))
    private void patcher$resolveCrash(ServerListEntryNormal serverListEntryNormal) {
        try {
            prepareServerIcon();
        } catch (Exception e) {
            server.setBase64EncodedIconData(null);
        }
    }
}
