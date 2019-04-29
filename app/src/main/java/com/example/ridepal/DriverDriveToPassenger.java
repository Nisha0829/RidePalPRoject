package com.example.ridepal;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.sql.Driver;
import java.util.ArrayList;
import java.util.Map;

public class DriverDriveToPassenger extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {

    GoogleMap map;
    Button getDirection, call, pickUp;
    MarkerOptions driver, passenger;
    Polyline currentPolyline;
    private double originlat, originlong, destlat, destlong,driverOriginlat,driverOriginlong,driverDestlat,driverDestLong;
    String passoriginlat, passoriginlong, passdestlat, passdestlong, passName, passdestination, passOrigin, passDest, photo;
    private Bundle sendInfo;
    private String driverName, driverOriginName, driverDestName, emailID,driverEmailID;
    private LatLng driverOriginLatLng, passOriginLatLng, driverDestLatLng, passDestLatLng;
    private ImageView profilePic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_drive_to_passenger);
        Bundle getInfo = getIntent().getExtras();
        Intent intent = getIntent();
        originlat = getInfo.getDouble("originlat", 0.0);
        originlong = getInfo.getDouble("originlong", 0.0);
        destlat = getInfo.getDouble("destlat",0.0);
        destlong = getInfo.getDouble("destlong", 0.0);
        emailID = getInfo.getString("emailID");
        passName = getInfo.getString("passName");
        passDest = getInfo.getString("passDest");
        passOrigin = getInfo.getString("passOrigin");
        driverOriginlat= getInfo.getDouble("driverDestLat",0.0);
        driverOriginlong= getInfo.getDouble("driverOrigingLong", 0.0);
        driverDestlat= getInfo.getDouble("driverDestLat", 0.0);
        driverDestLong= getInfo.getDouble("driverDestLong", 0.0);
        driverDestName= getInfo.getString("driverDest");
        driverOriginName= getInfo.getString("driverOrigin");
        driverName=getInfo.getString("driverName");
        driverEmailID = getInfo.getString("driverEmailID");
        photo = getInfo.getString("photo");


        sendInfo = new Bundle();
        sendInfo.putDouble("originlat", originlat);
        sendInfo.putDouble("originlong", originlong);
        sendInfo.putDouble("destlat", destlat);
        sendInfo.putDouble("destlong", destlong);
        sendInfo.putString("emailID", emailID);
        sendInfo.putString("passName", passName);
        sendInfo.putString("passdest", passDest);
        sendInfo.putString("passorigin", passOrigin);
        sendInfo.putDouble("driverOrigingLat", driverOriginlat);
        sendInfo.putDouble("driverOrigingLong", driverOriginlong);
        sendInfo.putDouble("driverDestLat", driverDestlat);
        sendInfo.putDouble("driverDestLong", driverDestLong);
        sendInfo.putString("driverDest", driverDestName);
        sendInfo.putString("driverOrigin", driverOriginName);
        sendInfo.putString("driverName", driverName);
        sendInfo.putString("driverEmailId", driverEmailID);
        sendInfo.putString("photo", photo);

        driverOriginLatLng = new LatLng(driverOriginlat,driverOriginlong);
        driverDestLatLng = new LatLng(driverDestlat, driverDestLong);
        passOriginLatLng = new LatLng(originlat, originlong);
        passDestLatLng = new LatLng(destlat, destlong);

        getDirection = (Button)findViewById(R.id.btnGetDirections);
        getDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+passoriginlat+","+passoriginlong);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });


        MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.mapFrag);
        mapFragment.getMapAsync(this);

        //Test LatLng values. Must input actual Origin LatLng values when complete.

        driver = new MarkerOptions().position(driverOriginLatLng).title("Driver");
        passenger = new MarkerOptions().position(passOriginLatLng).title("Passenger");
        pickUp=(Button)findViewById(R.id.pickupbutton);
        pickUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickup= new Intent(DriverDriveToPassenger.this, DriverDrivingToDesination.class);
                pickup.putExtras(sendInfo);
                startActivity(pickup);
            }
        });



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.addMarker(driver);
        map.addMarker(passenger);
        ArrayList<LatLng> points = new ArrayList<>();
        points.add(driverOriginLatLng);
        points.add(passOriginLatLng);
        LatLngBounds.Builder bc = new LatLngBounds.Builder();

        for (LatLng point:points){
            bc.include(point);
        }
        CameraUpdate update = CameraUpdateFactory.newLatLngBounds(bc.build(),50);
        map.moveCamera(update);

        String url = getUrl(driver.getPosition(), passenger.getPosition(), "driving");
        new FetchURL(DriverDriveToPassenger.this).execute(url, "driving");
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
//https://goo.gl/pTbDBG