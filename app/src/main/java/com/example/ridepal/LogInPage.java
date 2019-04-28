package com.example.ridepal;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.Manifest.permission.CALL_PHONE;

public class LogInPage extends AppCompatActivity {
    Button createAccount;
    Button logIn;
    Button showBtn, callButton;
    Button test;
    String Email;
    EditText email;
    TextView displayList;
    EditText password;
    DataBaseHelper logInPage;
    String emailValue, pwdValue;
    CameraManager mCameraManager;
    String mCameraId;
    boolean isFlashAvailable;
    boolean flashLightChecked = true;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        logInPage = new DataBaseHelper(this);
        setContentView(R.layout.activity_log_in_page);
        isFlashAvailable = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        //test button code. remove before demo.
        test = (Button) findViewById(R.id.test);
        callButton = (Button) findViewById(R.id.call);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent testmap = new Intent(LogInPage.this, DriverDriveToPassenger.class);
                startActivity(testmap);
            }
        });


        createAccount = (Button) findViewById(R.id.createaccount);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createAccount = new Intent(LogInPage.this, CreateAccount1.class);
                startActivity(createAccount);

            }
        });

        /*showBtn = (Button) findViewById(R.id.show);
        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayList = (TextView) findViewById(R.id.displayValue);
                logInPage.list(displayList);
            }
        });*/

        logIn = (Button) findViewById(R.id.signInButton);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = (EditText) findViewById(R.id.emailSignIn);
                password = (EditText) findViewById(R.id.passwordSignIn);
                emailValue = email.getText().toString();
                pwdValue = password.getText().toString();
                if (!emailValue.isEmpty() && !pwdValue.isEmpty()) {

                    String result = logInPage.logIn(emailValue, pwdValue);
                    if (!result.equals("Invalid Password") && !result.equals("User Does Not Exist")) {
                        Intent next = new Intent(LogInPage.this, ModeSelect.class);
                        next.putExtra("userName", result);
                        next.putExtra("emailID", emailValue);
                        startActivity(next);
                    } else if (result.equals("Invalid Password")) {
                        Toast.makeText(getApplicationContext(), "Invalid password, Please try again", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "User does not exist", Toast.LENGTH_SHORT).show();
                    }

                } else if (!emailValue.isEmpty() && pwdValue.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter password", Toast.LENGTH_SHORT).show();
                } else if (emailValue.isEmpty() && !pwdValue.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter Email ID", Toast.LENGTH_SHORT);
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter Email ID & Password", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        logIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (email != null && password != null) {
//                    emailValue = email.getText().toString();
//                    pwdValue = password.getText().toString();
//                   String result=  logInPage.logIn(emailValue, pwdValue);
//                   if (result == "Success") {
//                       Intent next = new Intent(LogInPage.this, ModeSelect.class);
//                       startActivity(next);
//                   }
//                   else if (result == "Invalid Password"){
//                        Toast.makeText(getApplicationContext(), "Invalid password, Please try again", Toast.LENGTH_SHORT);
//                   }
//                   else{
//                       Toast.makeText(getApplicationContext(), "User does not exist", Toast.LENGTH_SHORT);
//                   }
//                }
//}
//        });
    }

    //@RequiresApi(api = Build.VERSION_CODES.M)
//    public void callMethod(View view) {
//        Intent i = new Intent(Intent.ACTION_CALL);
//        i.setData(Uri.parse("tel:123"));
//                    /*
//                    Intent i = new Intent(Intent.ACTION_DIAL);
//                    i.setData(Uri.parse("tel:0612312312"));
//                    if (i.resolveActivity(getPackageManager()) != null) {
//                          startActivity(i);
//                    }*/
//        if (ContextCompat.checkSelfPermission(getApplicationContext(), CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
//            startActivity(i);
//        } else {
//            requestPermissions(new String[]{CALL_PHONE}, 1);
//        }
//    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void callMethod (View view){
        {
            //TODO Implement flashlight flash for signalling driver.
            if (!isFlashAvailable) {
                AlertDialog alert = new AlertDialog.Builder(LogInPage.this).create();
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
            flashLightChecked= false;
        }
    }
}
