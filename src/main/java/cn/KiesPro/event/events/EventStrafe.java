package cn.KiesPro.event.events;

import cn.KiesPro.event.eventapi.events.callables.EventCancellable;
/*
 * @see MixinEntityPlayerSP.class
 */
public class EventStrafe extends EventCancellable {
    public float forward;
    public float strafe;
    public float friction;
    public float yaw;

    public EventStrafe(float forward, float strafe, float friction, float yaw) {
        this.forward = forward;
        this.strafe = strafe;
        this.friction = friction;
        this.yaw = yaw;
    }
}
