package cn.KiesPro.event.nouse;

import cn.KiesPro.event.eventapi.events.callables.EventCancellable;
/*
 * @see MixinEntityPlayerSP.class
 * Noooooooooooo
 */
public class EventSafeWalk extends EventCancellable {
    public boolean safe;

    public EventSafeWalk(boolean safe) {
        this.safe = safe;
    }

    public boolean getSafe() {
        return safe;
    }

    public void setSafe(boolean safe) {
        this.safe = safe;
    }
}
