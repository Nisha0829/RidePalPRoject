package com.example.ridepal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ModeSelect extends AppCompatActivity {
    Button drive, ride, signOut;
    TextView welcome;
    String hello;
    private String emailID;

    public String getEmailID(){
        return emailID;
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_select);
        Intent intent = getIntent();
        String userName = intent.getStringExtra("userName");
        emailID = intent.getStringExtra("emailID");

        drive = (Button)findViewById(R.id.drivebutton);
        ride = (Button)findViewById(R.id.ridebutton);
        signOut = (Button)findViewById(R.id.signoutbutton);
        welcome = (TextView)findViewById(R.id.hellotext);
        welcome.append("Hello, " +userName+"!");

        drive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent driveMode = new Intent (ModeSelect.this, DriverDestSearch.class);
                startActivity(driveMode);
            }
        });

        ride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rideMode = new Intent(ModeSelect.this, PassengerDestSearch.class);
                startActivity(rideMode);
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signOut = new Intent(ModeSelect.this, LogInPage.class);
                Toast youSignedOut = Toast.makeText(getApplicationContext(),"You've been signed out. Bye!", Toast.LENGTH_SHORT);
                youSignedOut.show();
                startActivity(signOut);
            }
        });
    }
}
