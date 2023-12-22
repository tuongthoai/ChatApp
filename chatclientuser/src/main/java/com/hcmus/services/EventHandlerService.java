package com.hcmus.services;

import com.hcmus.observer.Publisher;

public class EventHandlerService extends Publisher {
    private static final EventHandlerService instance;

    static {
        instance = new EventHandlerService();
    }

    private EventHandlerService() {
    }

    public static EventHandlerService getInstance() {
        return instance;
    }
}
