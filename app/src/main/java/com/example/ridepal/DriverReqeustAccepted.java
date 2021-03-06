package com.example.ridepal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class DriverReqeustAccepted extends AppCompatActivity {

    private Button driveTo;
    private double originlat, originlong, destlat, destlong, driverOriginlat,driverOriginlong,driverDestlat,driverDestLong;
    private Bundle sendInfo;
    private String passDest, passOrigin, passName, emailID,driverDestName,driverOriginName,driverName,driverEmailID, photo;
    private ImageView profilePic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_reqeust_accepted);
        Intent intent = getIntent();

        originlat = intent.getDoubleExtra("passoriginlat", 0.0);
        originlong = intent.getDoubleExtra("passoriginlong", 0.0);
        destlat = intent.getDoubleExtra("passdestlat", 0.0);
        destlong = intent.getDoubleExtra("passdestlong", 0.0);
        passName = intent.getStringExtra("passName");
        passDest = intent.getStringExtra("passdest");
        passOrigin = intent.getStringExtra("passorigin");
        emailID = intent.getStringExtra("emailId");
        driverOriginlat= intent.getDoubleExtra("driverOrigingLat", 0.0);
        driverOriginlong= intent.getDoubleExtra("driverOrigingLong", 0.0);
        driverDestlat= intent.getDoubleExtra("driverDestLat", 0.0);
        driverDestLong= intent.getDoubleExtra("driverDestLong", 0.0);
        driverDestName= intent.getStringExtra("driverDest");
        driverOriginName= intent.getStringExtra("driverOrigin");
        driverName=intent.getStringExtra("driverName");
        driverEmailID = intent.getStringExtra("driverEmailId");
        photo = intent.getStringExtra("photo");


        sendInfo = new Bundle();
        sendInfo.putDouble("originlat", originlat);
        sendInfo.putDouble("originlong", originlong);
        sendInfo.putDouble("destlat", destlat);
        sendInfo.putDouble("destlong", destlong);
        sendInfo.putString("emailID", emailID);
        sendInfo.putString("passName", passName);
        sendInfo.putString("passDest", passDest);
        sendInfo.putString("passOrigin", passOrigin);
        sendInfo.putDouble("driverOrigingLat", driverOriginlat);
        sendInfo.putDouble("driverOrigingLong", driverOriginlong);
        sendInfo.putDouble("driverDestLat", driverDestlat);
        sendInfo.putDouble("driverDestLong", driverDestLong);
        sendInfo.putString("driverDest", driverDestName);
        sendInfo.putString("driverOrigin", driverOriginName);
        sendInfo.putString("driverName", driverName);
        sendInfo.putString("driverEmailID", driverEmailID);
        sendInfo.putString("photo", photo);

        driveTo = (Button)findViewById(R.id.drivetobutton);
        profilePic = (ImageView)findViewById(R.id.passengerimage);
        profilePic.setImageBitmap(getProfilePic(photo));

        driveTo = (Button) findViewById(R.id.drivetobutton);
        driveTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent drive = new Intent(DriverReqeustAccepted.this, DriverDriveToPassenger.class);
                drive.putExtras(sendInfo);
                startActivity(drive);
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
