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

import static android.os.Build.VERSION_CODES.N;

public class DriverSearchResults extends AppCompatActivity {

    private static final String TAG = "DriverSearchResults";

    ListView passengerList;
    PassengerTestObject testOne, testTwo, testThree;
    Button modeSelect, editSearch;
    private String originlat, originlong, destlat, destlong, passoriginlat, passoriginlong, passdestlat, passdestlong, emailID;
    private Bundle sendInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_search_results);

        modeSelect = (Button)findViewById(R.id.modeselect);
        editSearch = (Button)findViewById(R.id.gobackbutton);

        Bundle getInfo = getIntent().getExtras();
        originlat = getInfo.getString("originlat");
        originlong = getInfo.getString("originlong");
        destlat = getInfo.getString("destlat");
        destlong = getInfo.getString("destlong");
        emailID = getInfo.getString("emailID");

        sendInfo = new Bundle();
        sendInfo.putString("originlat", originlat);
        sendInfo.putString("originlong", originlong);
        sendInfo.putString("destlat", destlat);
        sendInfo.putString("destlong", destlong);
        sendInfo.putString("emailID", emailID);

        modeSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent modeSelect = new Intent(DriverSearchResults.this, ModeSelect.class);
                startActivity(modeSelect);

            }
        });

        editSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editSearch = new Intent(DriverSearchResults.this, DriverComfirmRoute.class);
                startActivity(editSearch);
            }
        });

        passengerList = (ListView)findViewById(R.id.listview);



        testOne = new PassengerTestObject("testEmail1@email.com","Molly Randall", "PUT PIC STRING HERE", "Fox Theater", "Cobb Civic Center", new LatLng(33.9426, -84.5368), new LatLng(33.7725, 84.3858));
        testTwo = new PassengerTestObject("testEmail2@email.com", "Chris Minton", "PUT PIC STRING HERE", "Georgia Aquarium", "Cobb Galleria Centre",new LatLng(33.8833, -84.4666), new LatLng(33.7634, 84.3951));
        testThree = new PassengerTestObject("testEmail3@email.com", "Wyatt Cary", "PUT PIC STRING HERE","Zoo Atlanta" , "Cumberland Mall",new LatLng(33.8808, -84.4691), new LatLng(33.7341, 84.3723));

        ArrayList<PassengerTestObject> testList = new ArrayList<>();

        testList.add(testOne);
        testList.add(testTwo);
        testList.add(testThree);

        PassengerListAdapter adapter = new PassengerListAdapter(this, R.layout.list_item_layout, testList);

        passengerList.setAdapter(adapter);

        passengerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String passName = testList.get(position).getName();
                passoriginlat = String.valueOf(testList.get(position).getOriginLatLng().latitude);
                passoriginlong = String.valueOf(testList.get(position).getOriginLatLng().longitude);
                passdestlat = String.valueOf(testList.get(position).getDestLatLng().latitude);
                passdestlong = String.valueOf(testList.get(position).getDestLatLng().longitude);

                sendInfo.putString("passoriginlat", passdestlat);
                sendInfo.putString("passoriginlong", passoriginlong);
                sendInfo.putString("passdestlat", passdestlat);
                sendInfo.putString("passdestlong", passdestlong);
                sendInfo.putString("passName", passName);



                Intent passengerInfo = new Intent(DriverSearchResults.this, DriverSelectedPassInfo.class);
                passengerInfo.putExtras(sendInfo);
                startActivity(passengerInfo);
            }
        });








    }
}
