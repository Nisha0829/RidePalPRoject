package com.example.ridepal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

public class TripDetails extends AppCompatActivity {
    String status;
    private double originlat, originlong, destlat, destlong,driverOriginlat,driverOriginlong,driverDestlat,driverDestLong;
    private Bundle sendInfo;
    private TextView driverNameTV, driverDestTV, driverMilesTV, passNameTV, passDestTV, passMilesTV;
   String passName, passdestination, passorigin,driverName, driverOriginName, driverDestName,emailID,driverEmailID;
    private LatLng driverOriginLatLng, passOriginLatLng, driverDestLatLng, passDestLatLng;
    private Button goBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);

        driverNameTV = (TextView)findViewById(R.id.drivername);
        driverDestTV = (TextView)findViewById(R.id.driverdestination);
        driverMilesTV = (TextView)findViewById(R.id.drivermiles);
        passNameTV = (TextView)findViewById(R.id.passengername);
        passDestTV = (TextView)findViewById(R.id.passengerdestination);
        passMilesTV = (TextView)findViewById(R.id.passengermiles);
        goBack = (Button)findViewById(R.id.gobackbutton);

        Bundle getInfo = getIntent().getExtras();
        originlat = getInfo.getDouble("originlat");
        originlong = getInfo.getDouble("originlong");
        destlat = getInfo.getDouble("destlat");
        destlong = getInfo.getDouble("destlong");
        emailID = getInfo.getString("emailID");
//        passoriginlat = getInfo.getString("passoriginlat");
//        passoriginlong = getInfo.getString("passoriginlong");
//        passdestlat = getInfo.getString("passdestlat");
//        passdestlong = getInfo.getString("passdestlong");
        passName = getInfo.getString("passname");
        passdestination = getInfo.getString("passdest");
        passorigin = getInfo.getString("passorigin");
        status = getInfo.getString("status");
        driverOriginlat= getInfo.getDouble("driverOriginlat",0.0);
        driverOriginlong= getInfo.getDouble("driverOriginlong", 0.0);
        driverDestlat= getInfo.getDouble("driverDestlat", 0.0);
        driverDestLong= getInfo.getDouble("driverDestLong", 0.0);
        driverDestName= getInfo.getString("driverDestName");
        driverOriginName= getInfo.getString("driverOriginName");
        driverName=getInfo.getString("driverName");
        driverEmailID = getInfo.getString("driverEmailID");
//        driverName = getInfo.getString("drivername");
//        driverDestName = getInfo.getString("driverdestname");
//        driverOriginName = getInfo.getString("driveroriginname");



        sendInfo = new Bundle();
        sendInfo.putDouble("originlat", originlat);
        sendInfo.putDouble("originlong", originlong);
        sendInfo.putDouble("destlat", destlat);
        sendInfo.putDouble("destlong", destlong);
        sendInfo.putString("emailID", emailID);
//        sendInfo.putString("passoriginlat", passoriginlat);
//        sendInfo.putString("passoriginlong", passoriginlong);
//        sendInfo.putString("passdestlat", passdestlat);
//        sendInfo.putString("passdestlong", passdestlong);
        sendInfo.putString("passname", passName);
        sendInfo.putString("passdest", passdestination);
        sendInfo.putString("passorigin", passorigin);
        sendInfo.putDouble("driverOrigingLat", driverOriginlat);
        sendInfo.putDouble("driverOrigingLong", driverOriginlong);
        sendInfo.putDouble("driverDestLat", driverDestlat);
        sendInfo.putDouble("driverOrigingLong", driverDestLong);
        sendInfo.putString("driverDest", driverDestName);
        sendInfo.putString("driverOrigin", driverOriginName);
        sendInfo.putString("driverName", driverName);
        sendInfo.putString("driverEmailId", driverEmailID);
//        sendInfo.putString("drivername", driverName);
//        sendInfo.putString("driverdestname", driverDestName);
//        sendInfo.putString("driveroriginname", driverOriginName);

        driverNameTV.setText(driverName);
        driverDestTV.setText(driverDestName);
        passNameTV.setText(passName);
        passDestTV.setText(passdestination);

        driverOriginLatLng = new LatLng(originlat, originlong);
        driverDestLatLng = new LatLng(destlat,destlong);
        passOriginLatLng = new LatLng(originlat, originlong);
        passDestLatLng = new LatLng(destlat, destlong);

        double driverDistanceMeters = SphericalUtil.computeDistanceBetween(driverOriginLatLng, driverDestLatLng);
        double driverDistanceMiles = driverDistanceMeters*0.00062171;
        driverMilesTV.setText(String.format("%.2f", driverDistanceMiles));

        double passDistanceMeters = SphericalUtil.computeDistanceBetween(passOriginLatLng, passDestLatLng);
        double passDistanceMiles = passDistanceMeters*0.00062171;
        passMilesTV.setText(String.format("%.2f", passDistanceMiles));

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundlePass = new Bundle();
                bundlePass.putString("userName", passName);
                bundlePass.putString("emailID", emailID);
                Bundle bundleDriver = new Bundle();
                bundleDriver.putString("userName", driverName);
                bundleDriver.putString("emailID", driverEmailID);
                Intent passback = new Intent(TripDetails.this, ModeSelect.class);
                Intent driverback = new Intent(TripDetails.this, ModeSelect.class);
                if(status.equals("driver")){
                    passback.putExtras(bundlePass);
                    startActivity(driverback);
                }
                if(status.equals("passenger")){
                    passback.putExtras(bundlePass);
                    startActivity(passback);
                }
            }
        });


    }
}
