package com.upwork.smedic.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.startStopButton)
    Button startStopTracking;
    @BindView(R.id.showDistanceButton)
    Button showDistance;
    @BindView(R.id.showLocationButton)
    Button showLocation;

    @OnClick({R.id.startStopButton, R.id.showDistanceButton, R.id.showLocationButton})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.startStopButton:
                break;
            case R.id.showDistanceButton:
                break;
            case R.id.showLocationButton:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
}
