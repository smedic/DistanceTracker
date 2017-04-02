package com.upwork.smedic.myapplication.events;

/**
 * Created by smedic on 24.3.17..
 */

public class DistanceChangedEvent {

    private float distance;

    public DistanceChangedEvent(float distance) {
        this.distance = distance;
    }

    public float getDistance() {
        return distance;
    }
}
