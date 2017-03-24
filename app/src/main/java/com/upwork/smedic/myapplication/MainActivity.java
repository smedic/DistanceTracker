package com.upwork.smedic.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.startStopButton)
    Button startStopTrackingButton;
    @BindView(R.id.showDistanceButton)
    Button showDistanceButton;
    @BindView(R.id.showLocationButton)
    Button showLocationButton;

    private boolean isStarted;

    @OnClick({R.id.startStopButton, R.id.showDistanceButton, R.id.showLocationButton})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.startStopButton:
                if(isStarted) {
                    //stop
                    isStarted = false;
                    startStopTrackingButton.setText(getString(R.string.stop_tracking));
                } else {
                    isStarted = true;
                    startStopTrackingButton.setText(getString(R.string.stop_tracking));
                }
                break;
            case R.id.showDistanceButton:
                startActivity(new Intent(MainActivity.this, DistanceActivity.class));
                break;
            case R.id.showLocationButton:
                startActivity(new Intent(MainActivity.this, LocationActivity.class));
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
