package com.example.ridepal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class CreateAccount1 extends AppCompatActivity {
    DataBaseHelper newUserInfo;
    ImageButton pickPhoto;
    Button submit;
    String photo, firstname, lastname, email, password, passowrdconfirm, gender, birthday;
    RadioGroup genderbuttons;
    EditText ifirstname, ilastname, iemail, ipassword, ipasswordconfirm, ibirthday;
    boolean photoSelected = false;
    public static final String EXTRA_EMAIL = "com.example.ridepal.EMAIL";

    private static final int RESULT_LOAD_IMAGE = 1;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            pickPhoto.setImageURI(selectedImage);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account1);
        newUserInfo = new DataBaseHelper(this);

        pickPhoto = (ImageButton) findViewById(R.id.picturebutton);
        submit = (Button) findViewById(R.id.nextbutton);
        ifirstname = (EditText) findViewById(R.id.firstname);
        ilastname = (EditText) findViewById(R.id.lastname);
        iemail = (EditText) findViewById(R.id.newemail);
        ipassword = (EditText) findViewById(R.id.createpassword);
        ipasswordconfirm = (EditText) findViewById(R.id.comfirmpassword);
        ibirthday = (EditText) findViewById(R.id.bday);
        genderbuttons = (RadioGroup) findViewById(R.id.setgender);


        pickPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                photoSelected = true;
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Code to transform image Uri to bitmap and then to string.
                Bitmap image = ((BitmapDrawable) pickPhoto.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] b = baos.toByteArray();
                photo = Base64.encodeToString(b, Base64.DEFAULT);

                //filling in strings with input values.
                firstname = ifirstname.getText().toString();
                lastname = ilastname.getText().toString();
                email = iemail.getText().toString();
                birthday = ibirthday.getText().toString();
                password = ipassword.getText().toString();
                passowrdconfirm = ipasswordconfirm.getText().toString();

                switch (genderbuttons.getCheckedRadioButtonId()) {
                    case R.id.malebutton:
                        gender = "Male";
                        break;
                    case R.id.femalebutton:
                        gender = "Female";
                        break;
                }
                //Instances of missing fields.

                if (firstname == null || lastname == null || email == null || birthday == null || password == null || passowrdconfirm == null || gender == null) {
                    Toast completeFields = Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT);
                    completeFields.show();
                }
                if (password != null && passowrdconfirm != null && password != passowrdconfirm) {
                    Toast passwordMismatch = Toast.makeText(getApplicationContext(), "Passwords do not match.", Toast.LENGTH_SHORT);
                    passwordMismatch.show();
                }
                if (!photoSelected) {
                    Toast photoMissing = Toast.makeText(getApplicationContext(), "You must upload photo to continue.", Toast.LENGTH_SHORT);
                    photoMissing.show();
                }


                //Adding information and moving to next screen if all fields are entered.
                if (firstname != null && lastname != null && email != null && birthday != null && password != null && passowrdconfirm != null && gender != null)
                {
                   // photo = "ZDfxgcv";  for testing
                    System.out.println("firstname" +firstname +" " + "lastname" + lastname+ " " + "email" + email + " "+ " birthday" + birthday + " " + "password"+ password+ " "+ "gender" + gender); //testing
                    String result = newUserInfo.createAccount(firstname, lastname, birthday, email, gender, password, photo);
                    System.out.println("Result "+ result);  //for testing
                    Toast success = Toast.makeText(getApplicationContext(), "User Info Saved Successfully!", Toast.LENGTH_SHORT);
                    success.show();
                    Intent next = new Intent(CreateAccount1.this, EditPrefernces.class);
                    next.putExtra(EXTRA_EMAIL, email);

                    startActivity(next);
                }


            }
        });


    }
}
