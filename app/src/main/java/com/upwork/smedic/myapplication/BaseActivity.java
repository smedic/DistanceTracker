package com.upwork.smedic.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by smedic on 24.3.17..
 */

public class BaseActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private float distance;
    private float lat;
    private float lng;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        distance = prefs.getFloat("distanceTextView", 0);
        lat = prefs.getFloat("lat", 0);
        lng = prefs.getFloat("lng", 0);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    protected float getDistance() {
        return distance;
    }

    protected float getLat() {
        return lat;
    }

    protected float getLng() {
        return lng;
    }
}
