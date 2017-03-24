package com.upwork.smedic.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final String TAG = "SMEDIC MA";
    @BindView(R.id.startStopButton)
    Button startStopTrackingButton;
    @BindView(R.id.showDistanceButton)
    Button showDistanceButton;
    @BindView(R.id.showLocationButton)
    Button showLocationButton;

    private boolean isStarted;
    private boolean isLocationPermissionGranted;

    @OnClick({R.id.startStopButton, R.id.showDistanceButton, R.id.showLocationButton})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.startStopButton:
                if (isStarted) {
                    Log.d(TAG, "onClick: stop service");
                    //stop
                    isStarted = false;
                    startStopTrackingButton.setText(getString(R.string.stop_tracking));
                    stopService(new Intent(MainActivity.this, GpsService.class));
                } else {
                    Log.d(TAG, "onClick: start service");
                    showDialog();
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

        checkPermissions();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                isLocationPermissionGranted = true;
            }
        }
    }

    private boolean checkPermissions() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
        return false;
    }

    private void showDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this, R.style.myDialog);
        alertDialog.setTitle(getString(R.string.location_in_background));
        alertDialog.setMessage(getString(R.string.allow_location_in_background));

        alertDialog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Thread t = new Thread(){
                    public void run(){
                        getApplicationContext().startService(new Intent(MainActivity.this, GpsService.class));
                    }
                };
                t.start();
                isStarted = true;
                startStopTrackingButton.setText(getString(R.string.stop_tracking));
            }
        });

        // On pressing the cancel button
        alertDialog.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
}
