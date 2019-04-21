package com.example.ridepal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DriverReqeustAccepted extends AppCompatActivity {

    private Button driveTo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_reqeust_accepted);

        driveTo = (Button)findViewById(R.id.drivetobutton);
        driveTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent drive = new Intent(DriverReqeustAccepted.this, DriverDriveToPassenger.class);
                startActivity(drive);
            }
        });

    }
}
