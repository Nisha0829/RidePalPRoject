package com.example.ridepal;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;

public class DriverDestinationSearch extends AppCompatActivity implements OnMapReadyCallback {

    public GoogleMap map;
    Location location;
    Button modeSelect, search;
    EditText inputLocation;
    TextView seekMiles;
    SeekBar searchMiles;
    String currentMiles;
    SupportMapFragment mapFragment;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_destination_search);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.destinationmap);
        mapFragment.getMapAsync(this);

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            map.setMyLocationEnabled(true);
        }else{

            // TODO Code needed here to ask for location permissions
            Toast enableLocationPermission = Toast.makeText(getApplicationContext(), "Please enable Location Permissions in Settings", Toast.LENGTH_SHORT);
            enableLocationPermission.show();
        }



        //select mode button.
        modeSelect = (Button)findViewById(R.id.modeselect);
        modeSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent modeSelect = new Intent(DriverDestinationSearch.this, ModeSelect.class);
                startActivity(modeSelect);
            }
        });

        //Miles Seekbar and display of current seekbar miles.
        seekMiles = (TextView)findViewById(R.id.miles);
        searchMiles=(SeekBar)findViewById(R.id.milesbar);
        currentMiles = searchMiles.getProgress()+" Miles";
        seekMiles.setText(currentMiles);
        searchMiles.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentMiles = searchMiles.getProgress()+" Miles";
                seekMiles.setText(currentMiles);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        inputLocation = (EditText)findViewById(R.id.desinationinput);
        search = (Button)findViewById(R.id.searchbutton);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(DriverDestinationSearch.this, DriverDestinationResults.class);
                startActivity(next);
            }
        });





    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        System.out.println("Value of map is " + map);

    }
}
