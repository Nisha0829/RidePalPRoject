package com.example.ridepal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

public class PassengerEndTrip extends AppCompatActivity {

    private String originlat, originlong, destlat, destlong, emailID, passoriginlat, passoriginlong, passdestlat, passdestlong, passName, passdestination, passorigin;
    private Bundle sendInfo;
    private TextView driverNameTV, driverDestTV, driverMilesTV, passNameTV, passDestTV, passMilesTV, avgGasPriceTV, totalGasTV;
    private Button pay;
    private LatLng driverOriginLatLng, passOriginLatLng, driverDestLatLng, passDestLatLng;
    private String driverName, driverOriginName, driverDestName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_end_trip);
        driverNameTV = (TextView)findViewById(R.id.drivername);
        driverDestTV = (TextView)findViewById(R.id.driverdestination);
        driverMilesTV = (TextView)findViewById(R.id.drivermiles);
        passNameTV = (TextView)findViewById(R.id.passengername);
        passDestTV = (TextView)findViewById(R.id.passengerdestination);
        passMilesTV = (TextView)findViewById(R.id.passengermiles);
        avgGasPriceTV = (TextView)findViewById(R.id.destinationgasprice);
        totalGasTV = (TextView)findViewById(R.id.totalgasprice);
        pay= (Button)findViewById(R.id.sendrequestbutton);

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

        driverNameTV.setText(driverName);
        driverDestTV.setText(driverDestName);
        passNameTV.setText(passName);
        passDestTV.setText(passdestination);

        driverOriginLatLng = new LatLng(Double.parseDouble(originlat), Double.parseDouble(originlong));
        driverDestLatLng = new LatLng(Double.parseDouble(destlat), Double.parseDouble(destlong));
        passOriginLatLng = new LatLng(Double.parseDouble(passoriginlat), Double.parseDouble(passoriginlong));
        passDestLatLng = new LatLng(Double.parseDouble(passdestlat), Double.parseDouble(passdestlong));

        double driverDistanceMeters = SphericalUtil.computeDistanceBetween(driverOriginLatLng, driverDestLatLng);
        double driverDistanceMiles = driverDistanceMeters*0.00062171;
        driverMilesTV.setText(String.format("%.2f", driverDistanceMiles));

        double passDistanceMeters = SphericalUtil.computeDistanceBetween(passOriginLatLng, passDestLatLng);
        double passDistanceMiles = passDistanceMeters*0.00062171;
        passMilesTV.setText(String.format("%.2f", passDistanceMiles));




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

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToFeedback = new Intent(PassengerEndTrip.this, ActionUserFeedback.class);
                goToFeedback.putExtras(sendInfo);
                startActivity(goToFeedback);
            }
        });


    }
}
