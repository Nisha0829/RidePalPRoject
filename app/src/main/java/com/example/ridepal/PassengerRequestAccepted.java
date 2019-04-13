package com.example.ridepal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PassengerRequestAccepted extends AppCompatActivity {

    Button driveTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_request_accepted);

        driveTo = (Button)findViewById(R.id.drivebutton);
        driveTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent drive = new Intent(PassengerRequestAccepted.this, PassengerDriverEnRoute.class);
                startActivity(drive);
            }
        });
    }
}
