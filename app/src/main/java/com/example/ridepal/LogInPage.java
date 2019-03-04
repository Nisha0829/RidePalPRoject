package com.example.ridepal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LogInPage extends AppCompatActivity {
    Button createAccount;
    Button logIn;
    Button showBtn;
    String Email;
    EditText email;
    TextView displayList;
    EditText password;
    DataBaseHelper logInPage;
    String emailValue, pwdValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        logInPage = new DataBaseHelper(this);
        setContentView(R.layout.activity_log_in_page);
        createAccount = (Button) findViewById(R.id.createaccount);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createAccount = new Intent(LogInPage.this, CreateAccount1.class);
                startActivity(createAccount);

            }
        });

        showBtn = (Button) findViewById(R.id.show);
        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayList = (TextView) findViewById(R.id.displayValue);
                logInPage.list(displayList);
            }
        });

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
                    if (result != "Invalid Password" && result != "User Does Not Exist") {
                        Intent next = new Intent(LogInPage.this, ModeSelect.class);
                        next.putExtra("userName", result);
                        startActivity(next);
                    } else if (result == "Invalid Password") {
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
}
