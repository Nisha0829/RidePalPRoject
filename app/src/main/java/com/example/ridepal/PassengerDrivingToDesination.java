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
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class PassengerDrivingToDesination extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {

    GoogleMap map;
    MarkerOptions start, end;
    Polyline currentPolyline;
    Button tripDetails, emergency, endTrip;
    private String originlat, originlong, destlat, destlong, emailID, passoriginlat, passoriginlong, passdestlat, passdestlong, passName, passdestination, passorigin;
    private Bundle sendInfo;
    private String driverName, driverOriginName, driverDestName;
    private LatLng driverOriginLatLng, passOriginLatLng, driverDestLatLng, passDestLatLng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_driving_to_desination);
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

        MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.mapFrag);
        mapFragment.getMapAsync(this);

        tripDetails=(Button)findViewById(R.id.tripdetailsbutton);
        emergency=(Button)findViewById(R.id.emergencybutton);
        endTrip=(Button)findViewById(R.id.endtripbutton);

        tripDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = "passenger";
                sendInfo.putString("status", status);
                Intent tripdets = new Intent(PassengerDrivingToDesination.this, TripDetails.class);
                tripdets.putExtras(sendInfo);
                startActivity(tripdets);
            }
        });

        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = "passenger";

                sendInfo.putString(status, "status");
                Intent goToEmergency = new Intent(PassengerDrivingToDesination.this, Emergency.class);
                goToEmergency.putExtras(sendInfo);
                startActivity(goToEmergency);
            }
        });

        endTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent end=new Intent(PassengerDrivingToDesination.this, PassengerEndTrip.class);
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
        new FetchURL(PassengerDrivingToDesination.this).execute(url, "driving");

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
