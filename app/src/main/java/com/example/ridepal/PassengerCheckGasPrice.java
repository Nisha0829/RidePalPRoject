package com.example.ridepal;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

public class PassengerCheckGasPrice extends AppCompatActivity {
    private EditText gasPriceInput;
    private Button cont, checkGasPrice;
    private float gasPrice;
    private Bundle sendInfo;
    private String driverName, driverOriginName, driverDestName,passName, passdestination, passorigin,emailID,driverEmailID, photo, gasPriceString;
    private LatLng driverOriginLatLng, passOriginLatLng, driverDestLatLng, passDestLatLng;
    private ImageView profilePic;
    private Double originlat, originlong, destlat, destlong, passoriginlat, passoriginlong, passdestlat, passdestlong,driverOriginlat,driverOriginlong,driverDestlat,driverDestLong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_check_gas_price);

        gasPriceInput=(EditText)findViewById(R.id.gaspriceinput);
        cont = (Button)findViewById(R.id.continuebutton);
        checkGasPrice = (Button)findViewById(R.id.gasbutton);

        Bundle getInfo = getIntent().getExtras();
        originlat = getInfo.getDouble("originlat");
        originlong = getInfo.getDouble("originlong");
        destlat = getInfo.getDouble("destlat");
        destlong = getInfo.getDouble("destlong");
        emailID = getInfo.getString("emailID");
        driverOriginlat= getInfo.getDouble("driverOrigingLat",0.0);
        driverOriginlong= getInfo.getDouble("driverOrigingLong", 0.0);
        driverDestlat= getInfo.getDouble("driverDestLat", 0.0);
        driverDestLong= getInfo.getDouble("driverDestLong", 0.0);
        driverDestName= getInfo.getString("driverDest");
        driverOriginName= getInfo.getString("driverOrigin");
        driverName=getInfo.getString("driverName");
        driverEmailID = getInfo.getString("driverEmailId");
        passName = getInfo.getString("passName");
        passdestination = getInfo.getString("passdest");
        passorigin = getInfo.getString("passorigin");
        photo = getInfo.getString("photo");

        sendInfo = new Bundle();
        sendInfo.putDouble("originlat", originlat);
        sendInfo.putDouble("originlong", originlong);
        sendInfo.putDouble("destlat", destlat);
        sendInfo.putDouble("destlong", destlong);
        sendInfo.putString("emailID", emailID);
        sendInfo.putString("passname", passName);
        sendInfo.putString("passdest", passdestination);
        sendInfo.putString("passorigin", passorigin);
        sendInfo.putDouble("driverOrigingLat", driverOriginlat);
        sendInfo.putDouble("driverOrigingLong", driverOriginlong);
        sendInfo.putDouble("driverDestLat", driverDestlat);
        sendInfo.putDouble("driverDestLong", driverDestLong);
        sendInfo.putString("driverDest", driverDestName);
        sendInfo.putString("driverOrigin", driverOriginName);
        sendInfo.putString("driverName", driverName);
        sendInfo.putString("driverEmailId", driverEmailID);
        sendInfo.putString("photo", photo);

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gasPriceInput.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please input gas price to continue", Toast.LENGTH_SHORT).show();
                }else{
                    gasPriceString = gasPriceInput.getText().toString();
                    sendInfo.putString("gasprice", gasPriceString);
                    Intent end=new Intent(PassengerCheckGasPrice.this, PassengerEndTrip.class);
                    end.putExtras(sendInfo);
                    startActivity(end);
                }
            }
        });

        checkGasPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                String term = "nearestgasprice";   // term which you want to search for
                intent.putExtra(SearchManager.QUERY, term);
                startActivity(intent);
            }
        });


    }
}
