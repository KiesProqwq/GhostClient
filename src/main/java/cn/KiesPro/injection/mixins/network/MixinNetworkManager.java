package cn.KiesPro.injection.mixins.network;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import cn.KiesPro.event.eventapi.EventManager;
import cn.KiesPro.event.events.EventReceivePacket;
import cn.KiesPro.event.nouse.EventSendPacket;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;

@Mixin(NetworkManager.class)
public class MixinNetworkManager {
	
    @Inject(method = { "channelRead0" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/network/Packet;processPacket(Lnet/minecraft/network/INetHandler;)V", shift = At.Shift.BEFORE) }, cancellable = true)
    private void packetReceived(final ChannelHandlerContext p_channelRead0_1_, final Packet packet, final CallbackInfo ci) {
        final EventReceivePacket event = new EventReceivePacket(packet);
        EventManager.call(event);
        if (event.isCancelled()) {
        	ci.cancel();
        }
    }
    
    @Inject(method = { "sendPacket(Lnet/minecraft/network/Packet;)V" }, at = { @At("HEAD") }, cancellable = true)
    private void sendPacket(final Packet packetIn, final CallbackInfo ci) {
        /*
        final EventSendPacket event = new EventSendPacket(packetIn);
        EventManager.call(event);
        if (event.isCancelled()) {
            ci.cancel();
        }
        */
    }
    
}
