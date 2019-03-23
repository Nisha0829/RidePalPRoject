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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_search_results);

        modeSelect = (Button)findViewById(R.id.modeselect);
        editSearch = (Button)findViewById(R.id.gobackbutton);

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

        /*TODO take serach results from database query and create PassengerObjects with data pulled from DB*/

        //Test passenger objects for use until database queries on implemented.

        testOne = new PassengerTestObject("testEmail1@email.com","Molly Randall", "PUT PIC STRING HERE", "Fox Theater", "Cobb Civic Center", new LatLng(33.9426, -84.5368));
        testTwo = new PassengerTestObject("testEmail2@email.com", "Chris Minton", "PUT PIC STRING HERE", "Georgia Aquarium", "Cobb Galleria Centre", new LatLng(33.8833, -84.4666));
        testThree = new PassengerTestObject("testEmail3@email.com", "Wyatt Cary", "PUT PIC STRING HERE","Zoo Atlanta" , "Cumberland Mall", new LatLng(33.8808, -84.4691));

        ArrayList<PassengerTestObject> testList = new ArrayList<>();

        testList.add(testOne);
        testList.add(testTwo);
        testList.add(testThree);

        PassengerListAdapter adapter = new PassengerListAdapter(this, R.layout.list_item_layout, testList);

        passengerList.setAdapter(adapter);

        passengerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent passengerInfo = new Intent(DriverSearchResults.this, DriverSelectedPassInfo.class);
                startActivity(passengerInfo);
            }
        });








    }
}
