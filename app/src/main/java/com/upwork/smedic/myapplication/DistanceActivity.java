package com.upwork.smedic.myapplication;

import android.os.Bundle;
import android.widget.TextView;

import com.upwork.smedic.myapplication.events.DistanceChangedEvent;

import org.greenrobot.eventbus.Subscribe;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by smedic on 24.3.17..
 */

public class DistanceActivity extends BaseActivity {

    @BindView(R.id.distanceTextView)
    TextView distanceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance);
        ButterKnife.bind(this);

        setDistanceTextView(getDistance());
    }

    @Subscribe()
    public void onEvent(DistanceChangedEvent event) {
        setDistanceTextView(event.getDistance());
    }

    private void setDistanceTextView(float distance) {
        String dist = "0.0";
        if (BuildConfig.FLAVOR.equals("km")) {
            dist = String.format(Locale.getDefault(), "%.3f ", distance / 1000) + getString(R.string.km);
        } else if (BuildConfig.FLAVOR.equals("miles")) {
            dist = String.format(Locale.getDefault(), "%.3f ", (distance / 1000) / 1.609344) + getString(R.string.miles);
        }
        distanceTextView.setText(dist);
    }
}
