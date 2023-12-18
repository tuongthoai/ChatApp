package com.hcmus.observer;

public interface Subscriber {
    int getObserverId();
    void update(Object obj);
}
