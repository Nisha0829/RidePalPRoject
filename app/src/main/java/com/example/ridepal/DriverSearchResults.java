package com.example.ridepal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class DriverSearchResults extends AppCompatActivity {

    private static final String TAG = "DriverSearchResults";

    ListView passengerList;
    PassengerTestObject testOne, testTwo, testThree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_search_results);

        passengerList = (ListView)findViewById(R.id.listview);

        testOne = new PassengerTestObject("testEmail1@email.com","Molly Randall", "PUT PIC STRING HERE", "Fox Theater", "Cobb Civic Center");
        testTwo = new PassengerTestObject("testEmail2@email.com", "Chris Minton", "PUT PIC STRING HERE", "Lockheed Martin", "Georgia Aquarium" );
        testThree = new PassengerTestObject("testEmail3@email.com", "Wyatt Cary", "PUT PIC STRING HERE", "Cumberland Mall", "Zoo Atlanta");

        ArrayList<PassengerTestObject> testList = new ArrayList<>();

        testList.add(testOne);
        testList.add(testTwo);
        testList.add(testThree);

        PassengerListAdapter adapter = new PassengerListAdapter(this, R.layout.list_item_layout, testList);

        passengerList.setAdapter(adapter);








    }
}
