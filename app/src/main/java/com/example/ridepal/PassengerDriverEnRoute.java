package com.example.ridepal;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_driver_en_route);

        signalDriver = (Button)findViewById(R.id.signaldriver);
        signalDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Implement flashlight flash for signalling driver.
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
