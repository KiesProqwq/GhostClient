package cn.KiesPro.event.events;

import cn.KiesPro.event.eventapi.events.Event;

/*
 * @see MixinGuiIngame.java
 */
public class EventRender2D implements Event {
	
    public float partialTicks;
    
    public EventRender2D(final float partialTicks) {
        this.partialTicks = partialTicks;
    }
}