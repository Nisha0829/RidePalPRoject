package com.example.ridepal;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class DriverDestinationResults extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap map;
    Location location;
    Button modeSelect;
    SupportMapFragment mapFragment;
    private String searchInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_destination_results);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.destinationresultsmap);
        mapFragment.getMapAsync(this);

        List<Address> resultsList=null;
        MarkerOptions resultMarkerOptions = new MarkerOptions();

        Intent getSearchInput = getIntent();
        searchInput = getSearchInput.getStringExtra(DriverDestinationSearch.EXTRA_DESTINATIONSEARCH);

        Geocoder geocoder = new Geocoder(this);

        try{
            resultsList = geocoder.getFromLocationName(searchInput,5);
        }catch (IOException e){
            e.printStackTrace();
        }

        for(int i=0;i<resultsList.size();i++){
            Address result = resultsList.get(i);
            LatLng latLng = new LatLng(result.getLatitude(),result.getLongitude());
            resultMarkerOptions.position(latLng);


        }



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        System.out.println("Value of map is " + map);
    }
}
