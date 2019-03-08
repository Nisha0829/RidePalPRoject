package com.example.ridepal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PaymentInfo extends AppCompatActivity {

    Button finish;
    EditText iCardName, iCardnum, iCCV, iZip, iExpDate;
    String cardName, expDate, sCardnum, sCCV, sZip, email;
    int cardNum, ccv, zip;
    DataBaseHelper newPaymentInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_info);
        newPaymentInfo = new DataBaseHelper(this);

        iCardName = (EditText) findViewById(R.id.ccname);
        iCardnum = (EditText) findViewById(R.id.ccnum);
        iCCV = (EditText) findViewById(R.id.ccccv);
        iZip = (EditText) findViewById(R.id.cczip);
        iExpDate = (EditText) findViewById(R.id.ccexpdate);
        finish = (Button) findViewById(R.id.finishbutton);

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardName = iCardName.getText().toString();
                expDate = iExpDate.getText().toString();
                sCardnum = iCardnum.getText().toString();
                sCCV = iCCV.getText().toString();
                sZip = iZip.getText().toString();
                Intent intent = getIntent();
                Bundle bundle = intent.getExtras();
               // email = bundle.getString("vehEmail");
                cardNum = Integer.parseInt(sCardnum);
                ccv = Integer.parseInt(sCCV);
                zip = Integer.parseInt(sZip);

                if (cardName == null || expDate == null || sCardnum == null || sCCV == null || sZip == null) {
                    Toast missingInfo = Toast.makeText(getApplicationContext(), "Please Fill in all fields.", Toast.LENGTH_SHORT);
                    missingInfo.show();
                } else {
                    Intent logInIntent = new Intent(PaymentInfo.this, LogInPage.class);
                    Toast login = Toast.makeText(getApplicationContext(),"You've directed to  Login page!", Toast.LENGTH_SHORT);
                    login.show();
                    startActivity(logInIntent);
                   // newPaymentInfo.paymentInfo(cardName, cardNum, expDate, ccv, zip, email);
//                    Intent next = new Intent(PaymentInfo.this, LogInPage.class);
//                    startActivity(next);
                }
            }
        });

    }
}
