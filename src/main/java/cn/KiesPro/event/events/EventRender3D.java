package cn.KiesPro.event.events;

import cn.KiesPro.event.eventapi.events.Event;
/*
 * @see MixinEntityRenderer.java
 */
public class EventRender3D implements Event {
	
    public float partialTicks;
    
    public EventRender3D(final float partialTicks) {
        this.partialTicks = partialTicks;
    }

	public float getPartialTicks() {
		return partialTicks;
	}

	public void setPartialTicks(float partialTicks) {
		this.partialTicks = partialTicks;
	}
}
