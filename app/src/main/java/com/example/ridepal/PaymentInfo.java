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

        iCardName = (EditText)findViewById(R.id.ccname);
        iCardnum = (EditText)findViewById(R.id.ccnum);
        iCCV = (EditText)findViewById(R.id.ccccv);
        iZip = (EditText)findViewById(R.id.cczip);
        iExpDate = (EditText)findViewById(R.id.ccexpdate);
        finish = (Button)findViewById(R.id.finishbutton);

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardName=iCardName.getText().toString();
                expDate = iExpDate.getText().toString();
                sCardnum = iCardnum.getText().toString();
                sCCV = iCCV.getText().toString();
                sZip = iZip.getText().toString();
                Intent getEmail = getIntent();
                email = getEmail.getStringExtra(CreateAccount1.EXTRA_EMAIL);
                cardNum=Integer.parseInt(sCardnum);
                ccv=Integer.parseInt(sCCV);
                zip=Integer.parseInt(sZip);

                if(cardName==null||expDate==null||sCardnum==null||sCCV==null||sZip==null){
                    Toast missingInfo = Toast.makeText(getApplicationContext(), "Please Fill in all fields.", Toast.LENGTH_SHORT);
                    missingInfo.show();
                }else{
                    newPaymentInfo.paymentInfo(cardName,cardNum,expDate,ccv,zip,email);
                }
            }
        });

    }
}
