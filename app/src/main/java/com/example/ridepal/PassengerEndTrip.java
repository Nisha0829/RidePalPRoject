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

    private double originlat, originlong, destlat, destlong, passoriginlat, passoriginlong, passdestlat, avgGas, distance, totalOwed;
    private Bundle sendInfo;
    double driverOriginlat,driverOriginlong,driverDestlat,driverDestLong;
    private TextView driverNameTV, driverDestTV, driverMilesTV, passNameTV, passDestTV, passMilesTV, avgGasPriceTV, totalGasTV;
    private Button pay;
    private LatLng driverOriginLatLng, passOriginLatLng, driverDestLatLng, passDestLatLng;
    private String driverName, emailID, driverOriginName, driverDestName, driverEmailID, passdestlong, passName, passdestination, passorigin, gasPrice;

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

        Bundle getInfo = getIntent().getExtras();
        originlat = getInfo.getDouble("originlat");
        originlong = getInfo.getDouble("originlong");
        destlat = getInfo.getDouble("destlat");
        destlong = getInfo.getDouble("destlong");
        emailID = getInfo.getString("emailID");
        passName = getInfo.getString("passname");
        passdestination = getInfo.getString("passdest");
        passorigin = getInfo.getString("passorigin");
        driverOriginlat = getInfo.getDouble("driverOrigingLat");
        driverOriginlong = getInfo.getDouble("driverOrigingLong");
        driverDestlat = getInfo.getDouble("driverDestLat");
        driverDestLong = getInfo.getDouble("driverDestLong");
        driverName = getInfo.getString("driverName");
        driverOriginName = getInfo.getString("driverOrigin");
        driverDestName = getInfo.getString("driverDest");
        driverEmailID = getInfo.getString("driverEmailId");
        gasPrice = getInfo.getString("gasprice");

        driverNameTV.setText(driverName);
        driverDestTV.setText(driverDestName);
        passNameTV.setText(passName);
        passDestTV.setText(passdestination);
        avgGasPriceTV.setText("$"+gasPrice);

        driverOriginLatLng = new LatLng(originlat, originlong);
        driverDestLatLng = new LatLng(destlat,destlong);
        passOriginLatLng = new LatLng(driverOriginlat, driverOriginlong);
        passDestLatLng = new LatLng(driverDestlat, driverDestLong);


        double driverDistanceMeters = SphericalUtil.computeDistanceBetween(driverOriginLatLng, driverDestLatLng);
        double driverDistanceMiles = driverDistanceMeters*0.00062171;
        driverMilesTV.setText(String.format("%.2f", driverDistanceMiles));

        double passDistanceMeters = SphericalUtil.computeDistanceBetween(passOriginLatLng, passDestLatLng);
        double passDistanceMiles = passDistanceMeters*0.00062171;
        passMilesTV.setText(String.format("%.2f", passDistanceMiles));
        avgGas=Double.valueOf(gasPrice);

        totalOwed =(passDistanceMiles)/avgGas;

        totalGasTV.setText("$"+String.format("%.2f", totalOwed));




        sendInfo = new Bundle();
        sendInfo.putDouble("originlat", originlat);
        sendInfo.putDouble("originlong", originlong);
        sendInfo.putDouble("destlat", destlat);
        sendInfo.putDouble("destlong", destlong);
        sendInfo.putString("emailID", emailID);
        sendInfo.putDouble("passoriginlat", passoriginlat);
        sendInfo.putDouble("passoriginlong", passoriginlong);
        sendInfo.putDouble("passdestlat", passdestlat);
        sendInfo.putString("passdestlong", passdestlong);
        sendInfo.putString("passname", passName);
        sendInfo.putString("passdest", passdestination);
        sendInfo.putString("passorigin", passorigin);
        sendInfo.putString("driverName", driverName);
        sendInfo.putString("emailId", driverEmailID);
        pay= (Button)findViewById(R.id.sendmoneybutton);
        pay.setText(getResources().getText(R.string.paygasbutton)+" $"+String.format("%.2f", totalOwed));

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent end=new Intent(PassengerEndTrip.this, ActionUserFeedback.class);
                end.putExtras(sendInfo);
                startActivity(end);
            }
        });


    }
}
