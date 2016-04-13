package com.skurski.gwt.demo.client.library;

import com.google.gwt.event.shared.ResettableEventBus;
import com.google.gwt.event.shared.SimpleEventBus;

/**
 * Created by pskurski on 3/31/2016.
 */
public final class EventBusFactory {

    private static final ResettableEventBus EVENT_BUS = new ResettableEventBus(new SimpleEventBus());

    private EventBusFactory() {
    }

    public static ResettableEventBus getGlobalEventBus() {
        return EVENT_BUS;
    }
}
