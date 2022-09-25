package cn.KiesPro.event.events;

import cn.KiesPro.event.eventapi.events.callables.EventCancellable;
import net.minecraft.network.Packet;

/*
 *@see MixinNetworkManager.java
 */
public class EventReceivePacket extends EventCancellable {
	
	public Packet packet;
	
	public EventReceivePacket(Packet packet) {
		this.packet = packet;
	}

	public Packet getPacket() {
		return packet;
	}

	public void setPacket(Packet packet) {
		this.packet = packet;
	}
}
