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

public class DriverDrivingToDesination extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {

    GoogleMap map;
    MarkerOptions start, end;
    Polyline currentPolyline;
    Button tripDetails, emergency, endTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_driving_to_desination);

        MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.mapFrag);
        mapFragment.getMapAsync(this);

        tripDetails=(Button)findViewById(R.id.tripdetailsbutton);
        emergency=(Button)findViewById(R.id.emergencybutton);
        endTrip=(Button)findViewById(R.id.endtripbutton);

        tripDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tripdets = new Intent(DriverDrivingToDesination.this, TripDetails.class);
                startActivity(tripdets);
            }
        });

        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = "driver";
                Bundle stat =new Bundle();
                stat.putString(status, "status");
                Intent goToEmergency = new Intent(DriverDrivingToDesination.this, Emergency.class);
                goToEmergency.putExtras(stat);
                startActivity(goToEmergency);
            }
        });

        endTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent end=new Intent(DriverDrivingToDesination.this, DriverEndTrip.class);
                startActivity(end);
            }
        });


        //Test LatLng values. Must input actual Origin LatLng values when complete.

        start = new MarkerOptions().position(new LatLng(33.8808, -84.4691)).title("Start");
        end = new MarkerOptions().position(new LatLng(33.9426, -84.5368)).title("End");
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.addMarker(start);
        map.addMarker(end);

        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(new LatLng(33.8808, -84.4691),15);
        map.moveCamera(update);

        String url = getUrl(start.getPosition(), end.getPosition(), "driving");
        new FetchURL(DriverDrivingToDesination.this).execute(url, "driving");

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
}
