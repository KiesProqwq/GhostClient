package cn.KiesPro.event.eventapi.events.callables;

import cn.KiesPro.event.eventapi.events.Cancellable;
import cn.KiesPro.event.eventapi.events.Event;

/**
 * Abstract example implementation of the Cancellable interface.
 *
 * @author DarkMagician6
 * @since August 27, 2013
 */
public abstract class EventCancellable implements Event, Cancellable {

    private boolean cancelled;

    protected EventCancellable() {
    }

    /**
     * @see cn.KiesPro.event.eventapi.events.Cancellable.isCancelled
     */
    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * @see cn.KiesPro.event.eventapi.events.Cancellable.setCancelled
     */
    @Override
    public void setCancelled(boolean state) {
        cancelled = state;
    }
}
