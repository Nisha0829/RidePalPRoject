package com.example.ridepal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class PassengerRequestAccepted extends AppCompatActivity {

    private Button driveTo;
    private double originlat, originlong, destlat, destlong, driverOriginlat,driverOriginlong,driverDestlat,driverDestLong;
    private Bundle sendInfo;
    private String passDest, passOrigin, passName, emailID,driverDestName,driverOriginName,driverName,driverEmailID, photo;
    private ImageView profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_request_accepted);
        Intent intent = getIntent();
//        Intent intent = getIntent();
//        name = intent.getStringExtra("passName");
//        origin = intent.getStringExtra("passorigin");
//        destination = intent.getStringExtra("passdest");
//        emailId = intent.getStringExtra("emailId");
//        originlat = intent.getDoubleExtra("passoriginlat",0.0);
//        originlong = intent.getDoubleExtra("passoriginlong",0.0);
//        destlat = intent.getDoubleExtra("passdestlat",0.0);
//        destlong = intent.getDoubleExtra("passdestlong",0.0);

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
//        originlat = getInfo.getString("originlat");
//        originlong = getInfo.getString("originlong");
//        destlat = getInfo.getString("destlat");
//        destlong = getInfo.getString("destlong");
//        emailID = getInfo.getString("emailID");
//        passoriginlat = getInfo.getString("passoriginlat");
//        passoriginlong = getInfo.getString("passoriginlong");
//        passdestlat = getInfo.getString("passdestlat");
//        passdestlong = getInfo.getString("passdestlong");
//        passName = getInfo.getString("passname");
//        passdestination = getInfo.getString("passdest");
//        passorigin = getInfo.getString("passorigin");
//        driverName = getInfo.getString("drivername");
//        driverDestName = getInfo.getString("driverdestname");
//        driverOriginName = getInfo.getString("driveroriginname");

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

        profilePic = (ImageView)findViewById(R.id.passengerimage);
        profilePic.setImageBitmap(getProfilePic(photo));

        driveTo = (Button) findViewById(R.id.drivetobutton);
        driveTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent drive = new Intent(PassengerRequestAccepted.this, PassengerDriverEnRoute.class);
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
