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
    private String originlat, originlong, destlat, destlong, emailID, passoriginlat, passoriginlong, passdestlat, passdestlong, passName, passdestination, passorigin;
    private Bundle sendInfo;
    private String driverName, driverOriginName, driverDestName;
    private LatLng driverOriginLatLng, passOriginLatLng, driverDestLatLng, passDestLatLng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_drive_to_passenger);

        Bundle getInfo = getIntent().getExtras();
        originlat = getInfo.getString("originlat");
        originlong = getInfo.getString("originlong");
        destlat = getInfo.getString("destlat");
        destlong = getInfo.getString("destlong");
        emailID = getInfo.getString("emailID");
        passoriginlat = getInfo.getString("passoriginlat");
        passoriginlong = getInfo.getString("passoriginlong");
        passdestlat = getInfo.getString("passdestlat");
        passdestlong = getInfo.getString("passdestlong");
        passName = getInfo.getString("passname");
        passdestination = getInfo.getString("passdest");
        passorigin = getInfo.getString("passorigin");
        driverName = getInfo.getString("drivername");
        driverDestName = getInfo.getString("driverdestname");
        driverOriginName = getInfo.getString("driveroriginname");



        sendInfo = new Bundle();
        sendInfo.putString("originlat", originlat);
        sendInfo.putString("originlong", originlong);
        sendInfo.putString("destlat", destlat);
        sendInfo.putString("destlong", destlong);
        sendInfo.putString("emailID", emailID);
        sendInfo.putString("passoriginlat", passoriginlat);
        sendInfo.putString("passoriginlong", passoriginlong);
        sendInfo.putString("passdestlat", passdestlat);
        sendInfo.putString("passdestlong", passdestlong);
        sendInfo.putString("passname", passName);
        sendInfo.putString("passdest", passdestination);
        sendInfo.putString("passorigin", passorigin);
        sendInfo.putString("drivername", driverName);
        sendInfo.putString("driverdestname", driverDestName);
        sendInfo.putString("driveroriginname", driverOriginName);

        driverOriginLatLng = new LatLng(Double.parseDouble(originlat), Double.parseDouble(originlong));
        driverDestLatLng = new LatLng(Double.parseDouble(destlat), Double.parseDouble(destlong));
        passOriginLatLng = new LatLng(Double.parseDouble(passoriginlat), Double.parseDouble(passoriginlong));
        passDestLatLng = new LatLng(Double.parseDouble(passdestlat), Double.parseDouble(passdestlong));

        getDirection = (Button)findViewById(R.id.btnGetDirections);
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