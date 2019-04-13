package com.example.ridepal;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static android.Manifest.permission.CALL_PHONE;

public class Emergency extends AppCompatActivity {

    Button call911, roadside, goBack;
    String status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        call911=(Button)findViewById(R.id.nineoneone);
        roadside=(Button)findViewById(R.id.servicesbutton);
        goBack=(Button)findViewById(R.id.gobackbutton);

        Intent getStat=getIntent();
        Bundle bundle = getStat.getExtras();
        status = bundle.getString("status");

        call911.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                call911();
            }
        });

        roadside.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                callAssistance();
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent passback = new Intent(Emergency.this, PassengerDrivingToDesination.class);
                Intent driverback = new Intent(Emergency.this, DriverDrivingToDesination.class);
                if(status.equals("driver")){
                    startActivity(driverback);
                }
                if(status.equals("passenger")){
                    startActivity(passback);
                }
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void call911(){
        Intent i = new Intent(Intent.ACTION_CALL);
        i.setData(Uri.parse("tel:123"));

        if (ContextCompat.checkSelfPermission(getApplicationContext(), CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startActivity(i);
        } else {
            requestPermissions(new String[]{CALL_PHONE}, 1);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void callAssistance(){
        Intent i = new Intent(Intent.ACTION_CALL);
        i.setData(Uri.parse("tel:123"));

        if (ContextCompat.checkSelfPermission(getApplicationContext(), CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startActivity(i);
        } else {
            requestPermissions(new String[]{CALL_PHONE}, 1);
        }
    }

}
