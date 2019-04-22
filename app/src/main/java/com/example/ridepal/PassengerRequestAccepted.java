package com.example.ridepal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PassengerRequestAccepted extends AppCompatActivity {

    private Button driveTo;
    private String originlat, originlong, destlat, destlong, emailID, passoriginlat, passoriginlong, passdestlat, passdestlong, passName, passdestination, passorigin;
    private Bundle sendInfo;
    private String driverName, driverOriginName, driverDestName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_request_accepted);
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

        driveTo = (Button)findViewById(R.id.drivebutton);
        driveTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent drive = new Intent(PassengerRequestAccepted.this, PassengerDriverEnRoute.class);
                drive.putExtras(sendInfo);
                startActivity(drive);
            }
        });
    }
}
