package cn.KiesPro.event.events;

import cn.KiesPro.event.eventapi.events.callables.EventCancellable;

public class EventChat extends EventCancellable {
	
    public String message;

    public EventChat(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
