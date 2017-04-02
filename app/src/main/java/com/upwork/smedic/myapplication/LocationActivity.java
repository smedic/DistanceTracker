package com.upwork.smedic.myapplication;

import android.os.Bundle;
import android.widget.TextView;

import com.upwork.smedic.myapplication.events.LocationChangedEvent;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by smedic on 24.3.17..
 */

public class LocationActivity extends BaseActivity {

    @BindView(R.id.location)
    TextView location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        ButterKnife.bind(this);

        String locationString = getLat() + ", " + getLng();
        location.setText(locationString);
    }

    @Subscribe()
    public void onEvent(LocationChangedEvent event) {
        String locationString = event.getLat() + ", " + event.getLng();
        location.setText(locationString);
    }
}
