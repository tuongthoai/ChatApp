package com.hcmus.observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Publisher {
    private final List<Subscriber> subscribers = new ArrayList<>();
    private final Map<Integer, Subscriber> subscribersMap = new HashMap<>();
    public void addObserver(Subscriber subscriber) {
        subscribers.add(subscriber);
        subscribersMap.put(subscriber.getObserverId(), subscriber);
    }

    public void removeObserver(Subscriber subscriber) {
        subscribers.removeIf(obs -> obs.getObserverId() == subscriber.getObserverId());
        subscribersMap.remove(subscriber.getObserverId());
    }

    public void notify(Object obj) {
        for (Subscriber obs : subscribers) {
            obs.update(obj);
        }
    }

    public void notify(Integer id, Object obj) {
        (subscribersMap.get(id)).update(obj);
    }
}
