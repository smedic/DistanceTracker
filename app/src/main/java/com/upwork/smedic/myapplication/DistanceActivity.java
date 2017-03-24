package com.upwork.smedic.myapplication;

import android.app.Activity;
import android.os.Bundle;

import butterknife.ButterKnife;

/**
 * Created by smedic on 24.3.17..
 */

public class DistanceActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance);
        ButterKnife.bind(this);
    }
}
