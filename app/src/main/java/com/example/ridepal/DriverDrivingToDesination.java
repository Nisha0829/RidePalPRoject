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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class DriverDrivingToDesination extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {

    GoogleMap map;
    MarkerOptions start, end;
    Polyline currentPolyline;
    Button tripDetails, emergency, endTrip;
    private double originlat, originlong, destlat, destlong, passoriginlat, passoriginlong, passdestlat, passdestlong,driverOriginlat,driverOriginlong,driverDestlat,driverDestLong;

    private Bundle sendInfo;
    private String driverName, driverOriginName, driverDestName,passName, passdestination, passorigin,emailID,driverEmailID, photo;
    private LatLng driverOriginLatLng, passOriginLatLng, driverDestLatLng, passDestLatLng;
    private ImageView profilePic;
    Button getDirection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_driving_to_desination);
        Bundle getInfo = getIntent().getExtras();
        originlat = getInfo.getDouble("originlat");
        originlong = getInfo.getDouble("originlong");
        destlat = getInfo.getDouble("destlat");
        destlong = getInfo.getDouble("destlong");
        emailID = getInfo.getString("emailID");
        driverOriginlat= getInfo.getDouble("driverOrigingLat",0.0);
        driverOriginlong= getInfo.getDouble("driverOrigingLong", 0.0);
        driverDestlat= getInfo.getDouble("driverDestLat", 0.0);
        driverDestLong= getInfo.getDouble("driverDestLong", 0.0);
        driverDestName= getInfo.getString("driverDest");
        driverOriginName= getInfo.getString("driverOrigin");
        driverName=getInfo.getString("driverName");
        driverEmailID = getInfo.getString("driverEmailId");
        passName = getInfo.getString("passName");
        passdestination = getInfo.getString("passdest");
        passorigin = getInfo.getString("passorigin");
        photo = getInfo.getString("photo");

        sendInfo = new Bundle();
        sendInfo.putDouble("originlat", originlat);
        sendInfo.putDouble("originlong", originlong);
        sendInfo.putDouble("destlat", destlat);
        sendInfo.putDouble("destlong", destlong);
        sendInfo.putString("emailID", emailID);
        sendInfo.putString("passname", passName);
        sendInfo.putString("passdest", passdestination);
        sendInfo.putString("passorigin", passorigin);
        sendInfo.putDouble("driverOrigingLat", driverOriginlat);
        sendInfo.putDouble("driverOrigingLong", driverOriginlong);
        sendInfo.putDouble("driverDestLat", driverDestlat);
        sendInfo.putDouble("driverDestLong", driverDestLong);
        sendInfo.putString("driverDest", driverDestName);
        sendInfo.putString("driverOrigin", driverOriginName);
        sendInfo.putString("driverName", driverName);
        sendInfo.putString("driverEmailId", driverEmailID);
        sendInfo.putString("photo", photo);

        driverOriginLatLng = new LatLng(originlat, originlong);
        driverDestLatLng = new LatLng(destlat,destlong);
        passOriginLatLng = new LatLng(originlat, originlong);
        passDestLatLng = new LatLng(destlat, destlong);


        MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.mapFrag);
        mapFragment.getMapAsync(this);

        tripDetails=(Button)findViewById(R.id.tripdetailsbutton);
        emergency=(Button)findViewById(R.id.emergencybutton);
        endTrip=(Button)findViewById(R.id.endtripbutton);
        getDirection = (Button)findViewById(R.id.btnGetDirections);

        tripDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = "driver";

                sendInfo.putString("status", status);
                Intent tripdets = new Intent(DriverDrivingToDesination.this, TripDetails.class);
                tripdets.putExtras(sendInfo);
                startActivity(tripdets);
            }
        });

        getDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+passdestlat+","+passdestlong);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = "driver";

                sendInfo.putString("status", status);
                Intent goToEmergency = new Intent(DriverDrivingToDesination.this, Emergency.class);
                goToEmergency.putExtras(sendInfo);
                startActivity(goToEmergency);
            }
        });

        endTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent end=new Intent(DriverDrivingToDesination.this, DriverCheckGasPrice.class);
                end.putExtras(sendInfo);
                startActivity(end);
            }
        });


        //Test LatLng values. Must input actual Origin LatLng values when complete.

        start = new MarkerOptions().position(passOriginLatLng).title("Start");
        end = new MarkerOptions().position(passDestLatLng).title("End");
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
        ArrayList<LatLng> points = new ArrayList<>();
        points.add(passOriginLatLng);
        points.add(passDestLatLng);
        LatLngBounds.Builder bc = new LatLngBounds.Builder();

        for (LatLng point:points){
            bc.include(point);
        }
        CameraUpdate update = CameraUpdateFactory.newLatLngBounds(bc.build(),50);

        //CameraUpdate update = CameraUpdateFactory.newLatLngZoom(new LatLng(33.8808, -84.4691),15);
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
