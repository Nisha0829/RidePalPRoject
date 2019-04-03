package com.example.ridepal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class DriverReqeustAccepted extends AppCompatActivity {

    private Button driveTo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_reqeust_accepted);

        driveTo = (Button)findViewById(R.id.drivebutton);

    }
}
