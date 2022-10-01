package cn.KiesPro.event.nouse;

import cn.KiesPro.event.eventapi.events.Event;
import cn.KiesPro.event.eventapi.types.EventType;
/*
 * @see MixinEntityPlayerSP.class
 * NOooooooooooo
 */
public class EventStep implements Event {
	
    private final EventType eventType;
    private float height;

    public EventStep(EventType eventType, float height) {
        this.eventType = eventType;
        this.height = height;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public EventType getEventType() {
        return eventType;
    }
}
