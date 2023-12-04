package com.hcmus.observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Publisher {
    private final List<Subscribe> subscribers = new ArrayList<>();
    private final Map<Integer, Subscribe> subscribersMap = new HashMap<>();
    public void addObserver(Subscribe subscriber) {
        subscribers.add(subscriber);
        subscribersMap.put(subscriber.getObserverId(), subscriber);
    }

    public void removeObserver(Subscribe subscriber) {
        subscribers.removeIf(obs -> obs.getObserverId() == subscriber.getObserverId());
        subscribersMap.remove(subscriber.getObserverId());
    }

    public void notify(Object obj) {
        for (Subscribe obs : subscribers) {
            obs.update(obj);
        }
    }

    public void notify(Integer id, Object obj) {
        (subscribersMap.get(id)).update(obj);
    }
}
