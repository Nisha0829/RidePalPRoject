package com.example.ridepal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

public class PassengerSelectedDriverInfo extends AppCompatActivity {

    private Button modeSelect, changePref, backToResults, changeRoute, sendRequest;
    private TextView iName, iAge, iOrigin, iDestination;
    private String name, age, origin, destination, emailId,driverDestName,driverOriginName,driverName,driverEmailID;
    double originlat, originlong, destlat, destlong,driverOriginlat,driverOriginlong,driverDestlat,driverDestLong;
    Bundle sendInfo;
    private PassengerTestObject testPassenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_selected_driver_info);
        Intent intent = getIntent();
        name = intent.getStringExtra("passName");
        origin = intent.getStringExtra("passorigin");
        destination = intent.getStringExtra("passdest");
        emailId = intent.getStringExtra("emailId");
        originlat = intent.getDoubleExtra("passoriginlat",0.0);
        originlong = intent.getDoubleExtra("passoriginlong",0.0);
        destlat = intent.getDoubleExtra("passdestlat",0.0);
        destlong = intent.getDoubleExtra("passdestlong",0.0);
        driverOriginlat= intent.getDoubleExtra("driverOrigingLat", 0.0);
        driverOriginlong= intent.getDoubleExtra("driverOrigingLong", 0.0);
        driverDestlat= intent.getDoubleExtra("driverDestLat", 0.0);
        driverDestLong= intent.getDoubleExtra("driverDestLong", 0.0);
        driverDestName= intent.getStringExtra("driverDest");
        driverOriginName= intent.getStringExtra("driverOrigin");
        driverName=intent.getStringExtra("driverName");
        driverEmailID = intent.getStringExtra("driverEmailId");
 //        sendInfo.putDouble("driverOrigingLat", driverOriginlat);
//        sendInfo.putDouble("driverOrigingLong", driverOriginlong);
//        sendInfo.putDouble("driverDestLat", driverDestlat);
//        sendInfo.putDouble("driverOrigingLong", driverDestLong);
//        sendInfo.putString("driverDest", driverDestName);
//        sendInfo.putString("driverOrigin", driverOriginName);
//        sendInfo.putString("driverName", driverName);
//        sendInfo.putString("driverEmailId", driverEmailID);




        modeSelect = (Button) findViewById(R.id.modeselect);

        modeSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent modeSelect = new Intent(PassengerSelectedDriverInfo.this, ModeSelect.class);
                startActivity(modeSelect);

            }
        });

        changePref = (Button) findViewById(R.id.preferences);
        changePref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent preferences = new Intent(PassengerSelectedDriverInfo.this, EditPrefernces.class);
                startActivity(preferences);
            }
        });

        backToResults = (Button) findViewById(R.id.backtoresults);
        backToResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent results = new Intent(PassengerSelectedDriverInfo.this, PassengerSearchResults.class);
                startActivity(results);
            }
        });

        changeRoute = (Button) findViewById(R.id.changeroute);
        changeRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent route = new Intent(PassengerSelectedDriverInfo.this, PassengerComfirmRoute.class);
                startActivity(route);
            }
        });

        iName = (TextView) findViewById(R.id.name);
        iAge = (TextView) findViewById(R.id.age);
        iOrigin = (TextView) findViewById(R.id.origin);
        iDestination = (TextView) findViewById(R.id.destination);


        iName.setText(name);
        // iAge.setText(age);
        iOrigin.setText(origin);
        iDestination.setText(destination);

        sendRequest = (Button) findViewById(R.id.sendrequest);
        sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent request = new Intent(PassengerSelectedDriverInfo.this, PassengerRequestAccepted.class);
                sendInfo = new Bundle();
                sendInfo.putDouble("passoriginlat", originlat);
                sendInfo.putDouble("passoriginlong", originlong);
                sendInfo.putDouble("passdestlat", destlat);
                sendInfo.putDouble("passdestlong", destlong);
                sendInfo.putString("passName", name);
                sendInfo.putString("passdest", destination);
                sendInfo.putString("passorigin", origin);
                sendInfo.putString("emailId", emailId);
                sendInfo.putDouble("driverOrigingLat", driverOriginlat);
                sendInfo.putDouble("driverOrigingLong", driverOriginlong);
                sendInfo.putDouble("driverDestLat", driverDestlat);
                sendInfo.putDouble("driverDestLong", driverDestLong);
                sendInfo.putString("driverDest", driverDestName);
                sendInfo.putString("driverOrigin", driverOriginName);
                sendInfo.putString("driverName", driverName);
                sendInfo.putString("driverEmailId", driverEmailID);

                request.putExtras(sendInfo);
                startActivity(request);
            }
        });
    }
}
