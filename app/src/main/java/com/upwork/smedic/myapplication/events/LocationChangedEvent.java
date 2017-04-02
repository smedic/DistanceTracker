package com.upwork.smedic.myapplication.events;

/**
 * Created by smedic on 24.3.17..
 */

public class LocationChangedEvent {

    private double lat; // Latitude
    private double lng;

    public LocationChangedEvent(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}
