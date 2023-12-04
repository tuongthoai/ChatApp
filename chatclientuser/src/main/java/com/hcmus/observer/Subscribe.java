package com.hcmus.observer;

public interface Subscribe {
    int getObserverId();
    void update(Object obj);
}
