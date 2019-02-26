package com.example.ridepal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class VehicleInfo extends AppCompatActivity {
    String make, model, color, email, lPlate, sYear;
    int year;
    EditText iMake, iModel, iColor, iLPlate, iYear;
    Button next, skip;
    DataBaseHelper newVehicleInfo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_info);

        iMake=(EditText)findViewById(R.id.vehiclemake);
        iModel=(EditText)findViewById(R.id.vehiclemodel);
        iColor=(EditText)findViewById(R.id.vehiclecolor);
        iYear=(EditText)findViewById(R.id.vehicleyear);
        iLPlate=(EditText)findViewById(R.id.platenumber);
        next=(Button)findViewById(R.id.nextbutton);
        skip=(Button)findViewById(R.id.skipbutton);

        //skip button for no vehicle information saved.
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent skip = new Intent(VehicleInfo.this, PaymentInfo.class);
                Toast noInfoSaved = Toast.makeText(getApplicationContext(),"No Vehicle Information saved.",Toast.LENGTH_SHORT);
                noInfoSaved.show();
                startActivity(skip);
            }
        });

        // click Next button
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                make = iMake.getText().toString();
                model = iModel.getText().toString();
                color = iColor.getText().toString();
                lPlate = iLPlate.getText().toString();
                sYear = iYear.getText().toString();
                year = Integer.parseInt(sYear);
                Intent getEmail = getIntent();
                email = getEmail.getStringExtra(CreateAccount1.EXTRA_EMAIL);

                //Incomplete Info message
                if(make==null||model==null||color==null||lPlate==null||sYear==null){
                    Toast missingInfo = Toast.makeText(getApplicationContext(), "Please fill in all fields or select SKIP", Toast.LENGTH_SHORT);
                    missingInfo.show();
                }else{
                    //save completed info and move to Payment activity.
                    newVehicleInfo.vehicleInfo(make, model,year,color,lPlate,email);
                    Intent next=new Intent(VehicleInfo.this, PaymentInfo.class);
                    startActivity(next);
                }
            }
        });



    }
}
