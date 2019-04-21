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
    private String name, age, origin, destination;
    private PassengerTestObject testPassenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_selected_driver_info);

        modeSelect =(Button)findViewById(R.id.modeselect);

        modeSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent modeSelect = new Intent(PassengerSelectedDriverInfo.this, ModeSelect.class);
                startActivity(modeSelect);

            }
        });

        changePref = (Button)findViewById(R.id.preferences);
        changePref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent preferences = new Intent(PassengerSelectedDriverInfo.this, EditPrefernces.class);
                startActivity(preferences);
            }
        });

        backToResults = (Button)findViewById(R.id.backtoresults);
        backToResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent results = new Intent(PassengerSelectedDriverInfo.this, PassengerSearchResults.class);
                startActivity(results);
            }
        });

        changeRoute = (Button)findViewById(R.id.changeroute);
        changeRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent route = new Intent(PassengerSelectedDriverInfo.this, PassengerComfirmRoute.class);
                startActivity(route);
            }
        });

        iName = (TextView)findViewById(R.id.name);
        iAge = (TextView)findViewById(R.id.age);
        iOrigin = (TextView)findViewById(R.id.origin);
        iDestination = (TextView)findViewById(R.id.destination);

        //Hardcoded passenger test object for testing. to be removed when database queries are in place



        //end test code

        iName.setText(name);
        iAge.setText(age);
        iOrigin.setText(origin);
        iDestination.setText(destination);

        sendRequest = (Button)findViewById(R.id.sendrequest);
        sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent request = new Intent(PassengerSelectedDriverInfo.this, PassengerRequestAccepted.class);
                startActivity(request);
            }
        });
    }
}
