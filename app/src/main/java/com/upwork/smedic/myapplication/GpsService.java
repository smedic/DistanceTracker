package com.upwork.smedic.myapplication;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.upwork.smedic.myapplication.events.DistanceChangedEvent;
import com.upwork.smedic.myapplication.events.LocationChangedEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by smedic on 24.3.17..
 */

public class GpsService extends Service implements LocationListener {

    private static final String TAG = "SMEDIC SERVICE";

    private boolean isGPSEnabled = false;
    private Location location; // Location
    private Location oldLocation;
    private float distance = 0;

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 10; // 10 seconds

    // Declaring a Location Manager
    protected LocationManager locationManager;

    public GpsService() {
        super();
        getLocation();
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            // Getting GPS status
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (isGPSEnabled) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    return null;
                }
                if (locationManager != null) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, 0, this);
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location != null) {
                        savePrefs(distance, (float) location.getLatitude(), (float) location.getLongitude());
                        EventBus.getDefault().post(new LocationChangedEvent(location.getLatitude(), location.getLongitude()));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    @Override
    public void onDestroy() {

        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.removeUpdates(GpsService.this);
        }
        super.onDestroy();

    }

    @Override
    public void onLocationChanged(Location location) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        Log.d(TAG, "onLocationChanged: " + location.getLatitude() + " - " + location.getLongitude());
        EventBus.getDefault().post(new LocationChangedEvent(location.getLatitude(), location.getLongitude()));
        if (oldLocation != null) {
            float[] results = new float[100];
            Location.distanceBetween(oldLocation.getLatitude(), oldLocation.getLongitude(),
                    location.getLatitude(), location.getLongitude(), results);
            distance += results[0];
            EventBus.getDefault().post(new DistanceChangedEvent(distance));

            editor.putFloat("distanceTextView", distance);
            editor.apply();
        }
        savePrefs(distance, (float) location.getLatitude(), (float) location.getLongitude()); //update params
        oldLocation = location;
    }


    @Override
    public void onProviderDisabled(String provider) {
        if (provider.equals(LocationManager.GPS_PROVIDER)) {
            isGPSEnabled = false;
        }
    }


    @Override
    public void onProviderEnabled(String provider) {
        if (provider.equals(LocationManager.GPS_PROVIDER)) {
            isGPSEnabled = true;
        }
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        savePrefs(0,0,0); //init state
        getLocation();
        return START_STICKY;
    }

    private void savePrefs(float distance, float lat, float lng) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        editor.putFloat("distanceTextView", distance);
        editor.putFloat("lat", lat);
        editor.putFloat("lng", lng);
        editor.apply();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}