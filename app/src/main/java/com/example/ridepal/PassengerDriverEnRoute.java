package com.example.ridepal;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import static android.Manifest.permission.CALL_PHONE;

public class PassengerDriverEnRoute extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {

    GoogleMap map;
    Button signalDriver, call, startTrip;
    MarkerOptions driver, passenger;
    Polyline currentPolyline;
    String mCameraId;
    CameraManager mCameraManager;
    boolean isFlashAvailable;
    boolean flashLightChecked = true;
    private double originlat, originlong, destlat, destlong,driverOriginlat,driverOriginlong,driverDestlat,driverDestLong;
    String passoriginlat, passoriginlong, passdestlat, passdestlong, passName, passdestination, passOrigin, passDest, photo;
    private Bundle sendInfo;
    private String driverName, driverOriginName, driverDestName, emailID,driverEmailID;
    private LatLng driverOriginLatLng, passOriginLatLng, driverDestLatLng, passDestLatLng;
    private ImageView profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_driver_en_route);
        Bundle getInfo = getIntent().getExtras();
        Intent intent = getIntent();
        originlat = getInfo.getDouble("originlat", 0.0);
        originlong = getInfo.getDouble("originlong", 0.0);
        destlat = getInfo.getDouble("destlat",0.0);
        destlong = getInfo.getDouble("destlong", 0.0);
        emailID = getInfo.getString("emailID");
        passName = getInfo.getString("passName");
        passDest = getInfo.getString("passDest");
        passOrigin = getInfo.getString("passOrigin");
        driverOriginlat= getInfo.getDouble("driverDestLat",0.0);
        driverOriginlong= getInfo.getDouble("driverOrigingLong", 0.0);
        driverDestlat= getInfo.getDouble("driverDestLat", 0.0);
        driverDestLong= getInfo.getDouble("driverDestLong", 0.0);
        driverDestName= getInfo.getString("driverDest");
        driverOriginName= getInfo.getString("driverOrigin");
        driverName=getInfo.getString("driverName");
        driverEmailID = getInfo.getString("driverEmailID");
        photo = getInfo.getString("photo");

        sendInfo = new Bundle();
        sendInfo.putDouble("originlat", originlat);
        sendInfo.putDouble("originlong", originlong);
        sendInfo.putDouble("destlat", destlat);
        sendInfo.putDouble("destlong", destlong);
        sendInfo.putString("emailID", emailID);
        sendInfo.putString("passName", passName);
        sendInfo.putString("passdest", passDest);
        sendInfo.putString("passorigin", passOrigin);
        sendInfo.putDouble("driverOrigingLat", driverOriginlat);
        sendInfo.putDouble("driverOrigingLong", driverOriginlong);
        sendInfo.putDouble("driverDestLat", driverDestlat);
        sendInfo.putDouble("driverDestLong", driverDestLong);
        sendInfo.putString("driverDest", driverDestName);
        sendInfo.putString("driverOrigin", driverOriginName);
        sendInfo.putString("driverName", driverName);
        sendInfo.putString("driverEmailId", driverEmailID);
        sendInfo.putString("photo", photo);

        profilePic = (ImageView)findViewById(R.id.driverimage);
        profilePic.setImageBitmap(getProfilePic(photo));




        isFlashAvailable = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        signalDriver = (Button) findViewById(R.id.signaldriver);
        signalDriver.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                //TODO Implement flashlight flash for signalling driver.
                if (!isFlashAvailable) {
                    AlertDialog alert = new AlertDialog.Builder(PassengerDriverEnRoute.this).create();
                    alert.setTitle("Oops!");
                    alert.setMessage("Flash not available in this device...");
                    alert.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    alert.show();
                }

                mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                try {
                    mCameraId = mCameraManager.getCameraIdList()[0];
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
                try {
                    mCameraManager.setTorchMode(mCameraId, flashLightChecked);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
                flashLightChecked = false;
            }
        });

        driverOriginLatLng = new LatLng(driverOriginlat,driverOriginlong);
        driverDestLatLng = new LatLng(driverDestlat, driverDestLong);
        passOriginLatLng = new LatLng(originlat, originlong);
        passDestLatLng = new LatLng(destlat, destlong);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFrag);
        mapFragment.getMapAsync(this);

        //Test LatLng values. Must input actual Origin LatLng values when complete.

       driver = new MarkerOptions().position(driverOriginLatLng).title("Driver");
        passenger = new MarkerOptions().position(passOriginLatLng).title("Passenger");
        call = (Button) findViewById(R.id.callbutton);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDriver();
            }
        });

        startTrip = (Button) findViewById(R.id.starttrip);
        startTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickup = new Intent(PassengerDriverEnRoute.this, PassengerDrivingToDesination.class);
                pickup.putExtras(sendInfo);
                startActivity(pickup);
            }
        });

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.addMarker(driver);
        map.addMarker(passenger);

        ArrayList<LatLng> points = new ArrayList<>();
        points.add(driverOriginLatLng);
        points.add(passOriginLatLng);
        LatLngBounds.Builder bc = new LatLngBounds.Builder();

        for (LatLng point : points) {
            bc.include(point);
        }
        CameraUpdate update = CameraUpdateFactory.newLatLngBounds(bc.build(), 50);
        map.moveCamera(update);

        String url = getUrl(driver.getPosition(), passenger.getPosition(), "driving");
        new FetchURL(PassengerDriverEnRoute.this).execute(url, "driving");
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }


    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null) {
            currentPolyline.remove();
        } else {
            currentPolyline = map.addPolyline((PolylineOptions) values[0]);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void callDriver()
    {
        Intent i = new Intent(Intent.ACTION_CALL);
        i.setData(Uri.parse("tel:123"));

        if (ContextCompat.checkSelfPermission(getApplicationContext(), CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startActivity(i);
        } else {
            requestPermissions(new String[]{CALL_PHONE}, 1);
        }
    }

    private Bitmap getProfilePic(String pic){
        Bitmap bitmap = null;
        File picture = new File(pic);
        try {
            bitmap = BitmapFactory.decodeStream(new FileInputStream(picture));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


}
