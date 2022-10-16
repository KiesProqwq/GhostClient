package cn.KiesPro.event.events;

import cn.KiesPro.event.eventapi.events.callables.EventCancellable;
import net.minecraft.entity.Entity;
/*
 * @see MixinRenderPlayer.class
 */
public class EventNameTags extends EventCancellable {
	
    public Entity p;

    public EventNameTags(Entity p2) {
        this.p = p2;
    }
}
