package cn.KiesPro.event.events;

import cn.KiesPro.event.eventapi.events.Event;
/*
 * @see MixinEntityPlayerSP.class
 */
public class EventPostUpdate implements Event {
	
    private float yaw;
    private float pitch;

    public EventPostUpdate(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }
}

