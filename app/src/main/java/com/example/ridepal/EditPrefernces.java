package com.example.ridepal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class EditPrefernces extends AppCompatActivity {

    EditText iMin, iMax;
    RadioGroup genderPref;
    int min, max;
    String genderPrefSelect, email;
    Button next;
    String sMax, sMin;
    DataBaseHelper prefUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_prefernces);

        iMin = (EditText)findViewById(R.id.min);
        iMax = (EditText)findViewById(R.id.max);
        genderPref = (RadioGroup)findViewById(R.id.genderprefset);
        next=(Button)findViewById(R.id.nextbutton);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sMin=iMin.getText().toString();
                sMax=iMax.getText().toString();
                min=Integer.parseInt(sMin);
                max=Integer.parseInt(sMax);
                Intent getEmail = getIntent();
                email = getEmail.getStringExtra(CreateAccount1.EXTRA_EMAIL);
                switch (genderPref.getCheckedRadioButtonId()){
                    case R.id.malebutton:
                        genderPrefSelect = "Male";
                        break;
                    case R.id.femalebutton:
                        genderPrefSelect = "Female";
                        break;
                    case R.id.eitherbutton:
                        genderPrefSelect = "Either";
                        break;
                }

                if(genderPrefSelect==null || sMax == null || sMin == null){
                    Toast missingInfo = Toast.makeText(getApplicationContext(),"Please Fill in All Fields.", Toast.LENGTH_SHORT);
                    missingInfo.show();
                }else{
                    prefUpdate.custPreference(max,min,genderPrefSelect,email);
                    Intent next = new Intent(EditPrefernces.this, VehicleInfo.class);
                    startActivity(next);
                }

            }
        });

    }
}
