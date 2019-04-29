package com.example.ridepal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class PassengerSearchResults extends AppCompatActivity {

    private static final String TAG = "PassengerSearchResults";

    ListView driverList;
    PassengerTestObject testOne, testTwo, testThree;
    Button modeSelect, editSearch;
    private String emailID, driverDestName, driverOriginName, driverName, destination,origin, driverEmailID, photo;
            double passoriginlat, passoriginlong, passdestlat, passdestlong,originlat, originlong, destlat, destlong,driverOriginlat,driverOriginlong,driverDestlat,driverDestLong;
    private Bundle sendInfo;
    ArrayList<PassengerTestObject> testList;
    ArrayList<DestinationValues> passengerSearchResultList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_search_results);
        Intent intent = getIntent();
        if (!intent.equals("")) {
            passengerSearchResultList = intent.getParcelableArrayListExtra("passengerSearchResult");
            List<String> abc = (List<String>) intent.getStringArrayListExtra("abc");
        }
        modeSelect = (Button) findViewById(R.id.modeselect);
        editSearch = (Button) findViewById(R.id.gobackbutton);

        if (!getIntent().equals("")) {
            Bundle getInfo = getIntent().getExtras();  //driverInfo
            if (!getInfo.isEmpty()) {
                driverOriginlat = getInfo.getDouble("originlat");
                driverOriginlong = getInfo.getDouble("originlong");
                driverDestlat = getInfo.getDouble("destlat");
                driverDestLong = getInfo.getDouble("deslongitude");
                driverEmailID = getInfo.getString("emailID");
                driverName = getInfo.getString("drivername");
                driverDestName = getInfo.getString("driverdestname");
                driverOriginName = getInfo.getString("driveroriginname");
                sendInfo = new Bundle();
                sendInfo.putDouble("originlat", driverOriginlat);
                sendInfo.putDouble("originlong", driverOriginlong);
                sendInfo.putDouble("destlat", driverDestlat);
                sendInfo.putDouble("destlong", driverDestLong);
                sendInfo.putString("emailID", driverEmailID);
                sendInfo.putString("drivername", driverName);
                sendInfo.putString("driverdestname", driverDestName);
                sendInfo.putString("driveroriginname", driverOriginName);
            }
        }

        modeSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent modeSelect = new Intent(PassengerSearchResults.this, ModeSelect.class);
                startActivity(modeSelect);

            }
        });

        editSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editSearch = new Intent(PassengerSearchResults.this, ModeSelect.class);
                Bundle sendInforToMode = new Bundle();
                sendInforToMode.putString("userName", driverName);
                sendInforToMode.putString("emailID", emailID);
                editSearch.putExtras(sendInforToMode);
                startActivity(editSearch);
            }
        });

        driverList = (ListView) findViewById(R.id.listview);

        if (null != passengerSearchResultList) {
            testList = new ArrayList<>();
            for (DestinationValues destinationValues : passengerSearchResultList) {
                testList.add(new PassengerTestObject(destinationValues.emailId, destinationValues.name, destinationValues.photoString, destinationValues.destination, destinationValues.origin, new LatLng(destinationValues.destLat, destinationValues.destLog), new LatLng(destinationValues.originLat, destinationValues.originLog)));
            }
        }

//        testOne = new PassengerTestObject("testEmail1@email.com", "Molly Randall", "PUT PIC STRING HERE", "Fox Theater", "Cobb Civic Center", new LatLng(33.9426, -84.5368), new LatLng(33.7725, 84.3858));
//        testTwo = new PassengerTestObject("testEmail2@email.com", "Chris Minton", "PUT PIC STRING HERE", "Georgia Aquarium", "Cobb Galleria Centre", new LatLng(33.8833, -84.4666), new LatLng(33.7634, 84.3951));
//        testThree = new PassengerTestObject("testEmail3@email.com", "Wyatt Cary", "PUT PIC STRING HERE", "Zoo Atlanta", "Cumberland Mall", new LatLng(33.8808, -84.4691), new LatLng(33.7341, 84.3723));
//        ArrayList<PassengerTestObject> testList = new ArrayList<>();
//
//        testList.add(testOne);
//        testList.add(testTwo);
//        testList.add(testThree);

        PassengerListAdapter adapter = new PassengerListAdapter(this, R.layout.list_item_layout, testList);
            driverList.setAdapter(adapter);

            driverList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    String passName = testList.get(position).getName();
                    String emailId = String.valueOf((testList.get(position).getEmailID()));
                    passoriginlat = (testList.get(position).getOriginLatLng().latitude);
                    passoriginlong = (testList.get(position).getOriginLatLng().longitude);
                    passdestlat = (testList.get(position).getDestLatLng().latitude);
                    passdestlong = (testList.get(position).getDestLatLng().longitude);
                    destination = testList.get(position).getDestName();
                    origin = testList.get(position).getOriginName();
                    photo = testList.get(position).getPicture();


                    sendInfo.putDouble("passoriginlat", passoriginlat);
                    sendInfo.putDouble("passoriginlong", passoriginlong);
                    sendInfo.putDouble("passdestlat", passdestlat);
                    sendInfo.putDouble("passdestlong", passdestlong);
                    sendInfo.putString("passName", passName);
                    sendInfo.putString("passdest", destination);
                    sendInfo.putString("passorigin", origin);
                    sendInfo.putString("emailId", emailId);
//                originlat = getInfo.getString("originlat");
//                originlong = getInfo.getString("originlong");
//                destlat = getInfo.getString("destlat");
//                destlong = getInfo.getString("destlong");
//                emailID = getInfo.getString("emailID");
//                driverName = getInfo.getString("drivername");
//                driverDestName = getInfo.getString("driverdestname");
//                driverOriginName = getInfo.getString("driveroriginname");
                    sendInfo.putDouble("driverOrigingLat", driverOriginlat);
                    sendInfo.putDouble("driverOrigingLong", driverOriginlong);
                    sendInfo.putDouble("driverDestLat", driverDestlat);
                    sendInfo.putDouble("driverDestLong", driverDestLong);
                    sendInfo.putString("driverDest", driverDestName);
                    sendInfo.putString("driverOrigin", driverOriginName);
                    sendInfo.putString("driverName", driverName);
                    sendInfo.putString("driverEmailId", driverEmailID);
                    sendInfo.putString("photo", photo);


                    Intent passengerInfo = new Intent(PassengerSearchResults.this, PassengerSelectedDriverInfo.class);
                    passengerInfo.putExtras(sendInfo);
                    startActivity(passengerInfo);
                }
            });
        }
}
