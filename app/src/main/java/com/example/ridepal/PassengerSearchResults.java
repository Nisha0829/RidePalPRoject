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

public class PassengerSearchResults extends AppCompatActivity {

    private static final String TAG = "PassengerSearchResults";

    ListView driverList;
    PassengerTestObject testOne, testTwo, testThree;
    Button modeSelect, editSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_search_results);

        modeSelect = (Button)findViewById(R.id.modeselect);
        editSearch = (Button)findViewById(R.id.gobackbutton);

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
                Intent editSearch = new Intent(PassengerSearchResults.this, PassengerComfirmRoute.class);
                startActivity(editSearch);
            }
        });

        driverList = (ListView)findViewById(R.id.listview);

        /*TODO take serach results from database query and create PassengerObjects with data pulled from DB
         * Each driver result needs email, name, picture string, desination name, origin name, destionation LatLng, origin LatLng*/

        //Test passenger objects for use until database queries on implemented.

        testOne = new PassengerTestObject("testEmail1@email.com","Molly Randall", "PUT PIC STRING HERE", "Fox Theater", "Cobb Civic Center", new LatLng(33.9426, -84.5368));
        testTwo = new PassengerTestObject("testEmail2@email.com", "Chris Minton", "PUT PIC STRING HERE", "Georgia Aquarium", "Cobb Galleria Centre", new LatLng(33.8833, -84.4666));
        testThree = new PassengerTestObject("testEmail3@email.com", "Wyatt Cary", "PUT PIC STRING HERE","Zoo Atlanta" , "Cumberland Mall", new LatLng(33.8808, -84.4691));

        ArrayList<PassengerTestObject> testList = new ArrayList<>();

        testList.add(testOne);
        testList.add(testTwo);
        testList.add(testThree);

        PassengerListAdapter adapter = new PassengerListAdapter(this, R.layout.list_item_layout, testList);

        driverList.setAdapter(adapter);

        driverList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent passengerInfo = new Intent(PassengerSearchResults.this, PassengerSelectedDriverInfo.class);
                startActivity(passengerInfo);
            }
        });
    }
}
