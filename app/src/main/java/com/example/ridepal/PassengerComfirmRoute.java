package com.example.ridepal;

import android.content.Intent;
import android.location.Address;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.sql.Array;
import java.util.Arrays;
import java.util.List;

public class PassengerComfirmRoute extends AppCompatActivity {

    private TextView seekMiles;
    private SeekBar searchMiles;
    private String currentMiles;
    private Button changeDest, changeOrigin, search;
    private String destPlaceID;
    private PlacesClient placesClient;
    private Place dest;
    private String destName;
    private LatLng destLatLng;
    private String destAddress;
    private String originName;
    private Place origin;
    private LatLng originLatLng;
    private String originAddress;
    private String origPlaceID;

    private static final String TAG = "PassengerComfirmRoute";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_comfirm_route);

        seekMiles = (TextView) findViewById(R.id.miles);
        searchMiles = (SeekBar) findViewById(R.id.milesbar);
        currentMiles = searchMiles.getProgress() + " Miles";
        seekMiles.setText(currentMiles);

        changeDest = (Button)findViewById(R.id.destinationbutton);
        changeOrigin = (Button)findViewById(R.id.originbutton);
        search = (Button)findViewById(R.id.searchbutton);

        Places.initialize(getApplicationContext(),"AIzaSyB42SLMvdvtN6_P-GcgObIyf-u0S7Yu14Y");
        placesClient = Places.createClient(this);



        destPlaceID = getIntent().getExtras().getString("DestPlaceID");
        origPlaceID = getIntent().getExtras().getString("OriginID");

        if(origPlaceID==null){
            changeOrigin.setText("Current Location");
            String currentLocLatLng = getIntent().getExtras().getString("CurrentLocLatLng");
            String[] latlong = currentLocLatLng.split(",");
            double latitude = Double.parseDouble(latlong[0]);
            double longitude = Double.parseDouble(latlong[1]);
            originLatLng = new LatLng(latitude,longitude);

        }else{
            List<Place.Field> originFields = Arrays.asList(Place.Field.ID,Place.Field.NAME,Place.Field.ADDRESS, Place.Field.LAT_LNG);
            FetchPlaceRequest orginrequest = FetchPlaceRequest.builder(origPlaceID,originFields).build();

            placesClient.fetchPlace(orginrequest).addOnSuccessListener((originresponse)-> {
                origin = originresponse.getPlace();
                Log.i(TAG,"Origin Place found: "+origin.getName());
            }).addOnFailureListener((exception)->{
                if(exception instanceof ApiException){
                    ApiException apiException = (ApiException)exception;
                    int statusCode = apiException.getStatusCode();
                    Log.e(TAG,"Origin Place not Found: "+exception.getMessage());
                }
            });

            originName = origin.getName();
            originLatLng = origin.getLatLng();
            originAddress = origin.getAddress();

            changeOrigin.setText(originName);
        }

        List<Place.Field> destFields = Arrays.asList(Place.Field.ID,Place.Field.NAME,Place.Field.ADDRESS, Place.Field.LAT_LNG);

        FetchPlaceRequest destrequest = FetchPlaceRequest.builder(destPlaceID,destFields).build();

        placesClient.fetchPlace(destrequest).addOnSuccessListener((destresponse)->{
            dest = destresponse.getPlace();
            Log.i(TAG, "Destination Place Found: "+dest.getName());
        }).addOnFailureListener((exception)->{
            if(exception instanceof ApiException){
                ApiException apiException = (ApiException)exception;
                int statusCode = apiException.getStatusCode();
                Log.e(TAG, "Destination Place not Found: "+exception.getMessage());
            }
        });

        destName = dest.getName();
        destLatLng=dest.getLatLng();
        destAddress=dest.getAddress();

        changeDest.setText(destName);

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

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Create method to input Passenger Status, UserEmail, Destination Name, Destination LatLng, Origin Name, Origin LatLng, and Current Miles into Search Table.

                //TODO Create method to start search database for matching Drivers with above criteria.
            }
        });
    }

}

