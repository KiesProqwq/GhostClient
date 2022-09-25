package cn.KiesPro.event.events;

import cn.KiesPro.event.eventapi.events.callables.EventCancellable;
import cn.KiesPro.event.eventapi.types.EventType;
import net.minecraft.network.Packet;

/*
 *@see MixinNetworkManager.java
 */
public class EventSendPacket extends EventCancellable {
    private boolean cancel;
    private Packet packet;

    public EventSendPacket(Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return this.packet;
    }

    @Override
    public boolean isCancelled() {
        return this.cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }
}

