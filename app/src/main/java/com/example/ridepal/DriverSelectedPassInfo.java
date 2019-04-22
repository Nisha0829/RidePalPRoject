package com.example.ridepal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

public class DriverSelectedPassInfo extends AppCompatActivity {

    private Button modeSelect, changePref, backToResults, changeRoute, sendRequest;
    private TextView iName, iAge, iOrigin, iDestination;
    private String originlat, originlong, destlat, destlong, emailID, passoriginlat, passoriginlong, passdestlat, passdestlong, passName, passdestination, passorigin, driverName, driverOriginName, driverDestName;
    private PassengerTestObject testPassenger;
    private Bundle sendInfo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_selected_pass_info);
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

        modeSelect =(Button)findViewById(R.id.modeselect);

        modeSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent modeSelect = new Intent(DriverSelectedPassInfo.this, ModeSelect.class);
                startActivity(modeSelect);

            }
        });

        changePref = (Button)findViewById(R.id.preferences);
        changePref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent preferences = new Intent(DriverSelectedPassInfo.this, EditPrefernces.class);
                startActivity(preferences);
            }
        });

        backToResults = (Button)findViewById(R.id.backtoresults);
        backToResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent results = new Intent(DriverSelectedPassInfo.this, DriverSearchResults.class);
                startActivity(results);
            }
        });

        changeRoute = (Button)findViewById(R.id.changeroute);
        changeRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent route = new Intent(DriverSelectedPassInfo.this, DriverComfirmRoute.class);
                startActivity(route);
            }
        });

        iName = (TextView)findViewById(R.id.name);
        iAge = (TextView)findViewById(R.id.age);
        iOrigin = (TextView)findViewById(R.id.origin);
        iDestination = (TextView)findViewById(R.id.destination);

        //Hardcoded passenger test object for testing. to be removed when database queries are in place



        iName.setText(passName);

        iOrigin.setText(passorigin);
        iDestination.setText(passdestination);

        sendRequest = (Button)findViewById(R.id.sendrequest);
        sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent request = new Intent(DriverSelectedPassInfo.this, DriverReqeustAccepted.class);
                request.putExtras(sendInfo);
                startActivity(request);
            }
        });

    }
}
