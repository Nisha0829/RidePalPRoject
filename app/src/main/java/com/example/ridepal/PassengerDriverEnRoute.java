package com.example.ridepal;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class PassengerDriverEnRoute extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {

    GoogleMap map;
    Button signalDriver, call, startTrip;
    MarkerOptions driver, passenger;
    Polyline currentPolyline;
    String mCameraId;
    CameraManager mCameraManager;
    boolean isFlashAvailable;
    boolean flashLightChecked = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_driver_en_route);
        isFlashAvailable = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        signalDriver = (Button)findViewById(R.id.signaldriver);
        signalDriver.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                //TODO Implement flashlight flash for signalling driver.
                if (!isFlashAvailable) {
                    AlertDialog alert = new AlertDialog.Builder(PassengerDriverEnRoute.this).create();
                    alert.setTitle("Oops!");
                    alert.setMessage("Flash not available in this device...");
                    alert.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    alert.show();
                }

                mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                try {
                    mCameraId = mCameraManager.getCameraIdList()[0];
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
                try {
                    mCameraManager.setTorchMode(mCameraId, flashLightChecked);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
                flashLightChecked= false;
            }
            });
        MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.mapFrag);
        mapFragment.getMapAsync(this);

        //Test LatLng values. Must input actual Origin LatLng values when complete.

        driver = new MarkerOptions().position(new LatLng(33.8808, -84.4691)).title("Driver");
        passenger = new MarkerOptions().position(new LatLng(33.9426, -84.5368)).title("Passenger");
        startTrip=(Button)findViewById(R.id.starttrip);
        startTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickup= new Intent(PassengerDriverEnRoute.this, PassengerDrivingToDesination.class);
                startActivity(pickup);
            }
        });

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.addMarker(driver);
        map.addMarker(passenger);

        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(new LatLng(33.8808, -84.4691),15);
        map.moveCamera(update);

        String url = getUrl(driver.getPosition(), passenger.getPosition(), "driving");
        new FetchURL(PassengerDriverEnRoute.this).execute(url, "driving");
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }


    @Override
    public void onTaskDone(Object... values) {
        if(currentPolyline!=null) {
            currentPolyline.remove();
        }
        else{
            currentPolyline = map.addPolyline((PolylineOptions)values[0]);
        }

    }
}
