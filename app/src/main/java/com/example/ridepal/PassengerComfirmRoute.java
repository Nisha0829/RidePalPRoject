package com.example.ridepal;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.sql.Array;
import java.util.Arrays;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class PassengerComfirmRoute extends AppCompatActivity {

    private TextView seekMiles;
    private SeekBar searchMiles;
    String currentMiles;
    private Button changeDest, changeOrigin, search, modeSelect;
    String destPlaceID;
    PlacesClient placesClient;
    Place dest;
    String destName;
    LatLng destLatLng;
    String destAddress;
    String originName;
    Place origin;
    LatLng originLatLng;
    String originAddress;
    String origPlaceID;
    LatLng currentLocationLatLng;
    String emailID;
    String driverName;


    private static final String TAG = "PassengerComfirmRoute";

    private Boolean mLocationPermissionsGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    private static final String FINE_LOCATION = ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_comfirm_route);

        seekMiles = (TextView) findViewById(R.id.miles);
        searchMiles = (SeekBar) findViewById(R.id.milesbar);
        currentMiles = searchMiles.getProgress() + " Miles";
        seekMiles.setText(currentMiles);

        ModeSelect activity = new ModeSelect();
        emailID = activity.getEmailID();

        getLocationPermission();

        changeDest = (Button)findViewById(R.id.destinationbutton);
        changeOrigin = (Button)findViewById(R.id.originbutton);
        search = (Button)findViewById(R.id.searchbutton);
        modeSelect = (Button)findViewById(R.id.modeselect);
        modeSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent modeSelect = new Intent(PassengerComfirmRoute.this, ModeSelect.class);
                startActivity(modeSelect);
            }
        });

        Places.initialize(getApplicationContext(),"AIzaSyB42SLMvdvtN6_P-GcgObIyf-u0S7Yu14Y");
        placesClient = Places.createClient(this);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                double originlat = originLatLng.latitude;
                double orginlong = originLatLng.longitude;
                double destlat = destLatLng.latitude;
                double deslong = destLatLng.longitude;

                Bundle sendDriverInfo = new Bundle();
                sendDriverInfo.putString("emailID", emailID);
                sendDriverInfo.putString("orinlat", String.valueOf(originlat));
                sendDriverInfo.putString("originlong", String.valueOf(orginlong));
                sendDriverInfo.putString("destlat", String.valueOf(destlat));
                sendDriverInfo.putString("deslong", String.valueOf(deslong));
                sendDriverInfo.putString("drivername", driverName);
                sendDriverInfo.putString("driverdestname", destName);
                sendDriverInfo.putString("driveroriginname", originName);

                Intent searchForPassengers = new Intent(PassengerComfirmRoute.this, PassengerSearchResults.class);
                searchForPassengers.putExtras(sendDriverInfo);
                startActivity(searchForPassengers);

            }
        });



        destPlaceID = getIntent().getExtras().getString("DestPlaceID");
        origPlaceID = getIntent().getExtras().getString("OriginID");

        if(origPlaceID==null){
            changeOrigin.setText("Current Location");
            getDeviceLocation();
            originLatLng = currentLocationLatLng;

        }else{
            List<Place.Field> originFields = Arrays.asList(Place.Field.ID,Place.Field.NAME,Place.Field.ADDRESS, Place.Field.LAT_LNG);
            FetchPlaceRequest orginrequest = FetchPlaceRequest.builder(origPlaceID,originFields).build();

            placesClient.fetchPlace(orginrequest).addOnSuccessListener((originresponse)-> {
                origin = originresponse.getPlace();
                Log.i(TAG,"Origin Place found: "+origin.getName());
                originName = origin.getName();
                originLatLng = origin.getLatLng();
                originAddress = origin.getAddress();

                changeOrigin.setText(originName);
            }).addOnFailureListener((exception)->{
                if(exception instanceof ApiException){
                    ApiException apiException = (ApiException)exception;
                    int statusCode = apiException.getStatusCode();
                    Log.e(TAG,"Origin Place not Found: "+exception.getMessage());
                }
            });

        }

        List<Place.Field> destFields = Arrays.asList(Place.Field.ID,Place.Field.NAME,Place.Field.ADDRESS, Place.Field.LAT_LNG);

        FetchPlaceRequest destrequest = FetchPlaceRequest.builder(destPlaceID,destFields).build();

        placesClient.fetchPlace(destrequest).addOnSuccessListener((destresponse)->{
            dest = destresponse.getPlace();
            Log.i(TAG, "Destination Place Found: "+dest.getName());
            destName = dest.getName();
            destLatLng=dest.getLatLng();
            destAddress=dest.getAddress();

            changeDest.setText(destName);
        }).addOnFailureListener((exception)->{
            if(exception instanceof ApiException){
                ApiException apiException = (ApiException)exception;
                int statusCode = apiException.getStatusCode();
                Log.e(TAG, "Destination Place not Found: "+exception.getMessage());
            }
        });



        changeDest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selectDest = new Intent(PassengerComfirmRoute.this, PassengerDestSearch.class);
                startActivity(selectDest);

            }
        });

        changeOrigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent setO = new Intent (PassengerComfirmRoute.this, PassengerOriginSearch.class);
                Bundle desID = new Bundle();
                desID.putString("DestPlaceID", destPlaceID);
                desID.putSerializable("drivername", driverName);
                setO.putExtras(desID);
                startActivity(setO);

            }
        });


        searchMiles.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentMiles = searchMiles.getProgress() + " Miles";
                seekMiles.setText(currentMiles);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }
    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try{
            if(mLocationPermissionsGranted){

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();
                            currentLocationLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());



                        }else{
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(PassengerComfirmRoute.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;

                }
            }
        }
    }

    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;

            }else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

}

