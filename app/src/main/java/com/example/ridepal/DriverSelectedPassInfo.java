package com.example.ridepal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Driver;

public class DriverSelectedPassInfo extends AppCompatActivity {

    private Button modeSelect, changePref, backToResults, changeRoute, sendRequest;
    private TextView iName, iAge, iOrigin, iDestination;
    private String name, age, origin, destination, emailId,driverDestName,driverOriginName,driverName,driverEmailID,photo;
    double originlat, originlong, destlat, destlong,driverOriginlat,driverOriginlong,driverDestlat,driverDestLong;
    Bundle sendInfo;
    private PassengerTestObject testPassenger;
    private ImageView profilePic;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_selected_pass_info);
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
        photo = intent.getStringExtra("photo");
        //        sendInfo.putDouble("driverOrigingLat", driverOriginlat);
//        sendInfo.putDouble("driverOrigingLong", driverOriginlong);
//        sendInfo.putDouble("driverDestLat", driverDestlat);
//        sendInfo.putDouble("driverOrigingLong", driverDestLong);
//        sendInfo.putString("driverDest", driverDestName);
//        sendInfo.putString("driverOrigin", driverOriginName);
//        sendInfo.putString("driverName", driverName);
//        sendInfo.putString("driverEmailId", driverEmailID);





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
        profilePic = (ImageView)findViewById(R.id.passengerimage);

        //Hardcoded passenger test object for testing. to be removed when database queries are in place


        iName.setText(name);
        // iAge.setText(age);
        iOrigin.setText(origin);
        iDestination.setText(destination);
        profilePic.setImageBitmap(getProfilePic(photo));

        sendRequest = (Button)findViewById(R.id.sendrequest);
        sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent request = new Intent(DriverSelectedPassInfo.this, DriverReqeustAccepted.class);
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
                sendInfo.putString("photo", photo);

                request.putExtras(sendInfo);
                startActivity(request);
            }
        });

    }

    private Bitmap getProfilePic(String pic){
        Bitmap bitmap = null;
        File picture = new File(pic);
        try {
            bitmap = BitmapFactory.decodeStream(new FileInputStream(picture));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
