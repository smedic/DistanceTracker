package com.upwork.smedic.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.upwork.smedic.myapplication.events.ServiceStartedEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by smedic on 24.3.17..
 */
public class DeviceBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean wasServiceRunning = preferences.getBoolean("serviceRunning", false);
        if (wasServiceRunning) {
            Thread t = new Thread() {
                public void run() {
                    context.startService(new Intent(context, GpsService.class));
                    EventBus.getDefault().post(new ServiceStartedEvent());
                }
            };
            t.start();
        }
    }
}
