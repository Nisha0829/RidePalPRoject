package com.example.ridepal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.EditText;

import java.util.Date;


public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String Database_Name = "passenger.db";
    private static final int DATABASE_VERSION = 4;


    public DataBaseHelper(Context context) {
        super(context, Database_Name, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS vehicleInfo");
        onCreate(db);

    }

    @Override

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS customerInfo(FIRSTNAME TEXT not null,LASTNAME TEXT not null,BIRTHDAY DATE not null,EMAILID TEXT PRIMARY KEY,GENDER TEXT, PASSWORD TEXT not null, PHOTO TEXT);");
        db.execSQL("CREATE TABLE IF NOT EXISTS vehicleInfo(MAKE TEXT,MODEL TEXT,YEAR INTEGER, COLOR TEXT,LICENCEPLATE TEXT, EMAILID TEXT, FOREIGN KEY(EMAILID) REFERENCES customerInfo(EMAILID) );");
        db.execSQL("CREATE TABLE IF NOT EXISTS customer_preference(MAXAGE INTEGER,MINRANGE INTEGER,GENDER TEXT, EMAILID TEXT, FOREIGN KEY(EMAILID) REFERENCES customerInfo(EMAILID) );");
        db.execSQL("CREATE TABLE IF NOT EXISTS cust_destination(ORIGIN TEXT,DESTINATION TEXT,SEARCHMILES INTEGER, EMAILID TEXT, FOREIGN KEY(EMAILID) REFERENCES customerInfo(EMAILID) );");
        db.execSQL("CREATE TABLE IF NOT EXISTS payment_info(CARDNAME TEXT, CCNUMBER INTEGER, EXPDATE DATE, CCV INTEGER, ZIPCODE INTEGER, EMAILID TEXT, FOREIGN KEY(EMAILID) REFERENCES customerInfo(EMAILID) );");
        //db.execSQL("create table if not exists imageTb ( a blob )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS customerInfo");
        db.execSQL("DROP TABLE IF EXISTS customer_preference");
        // db.execSQL("DROP TABLE IF EXISTS imageTb");
        db.execSQL("DROP TABLE IF EXISTS vehicleInfo");
        db.execSQL("DROP TABLE IF EXISTS payment_info");
        onCreate(db);
    }



    public String createAccount(String firstName, String lastName, String birthday , String emailId, String gender , String  password, String photoString) //createcreateAccount with 7 parameters
     {
        ContentValues contentValues = new ContentValues();
        contentValues.put("FIRSTNAME", firstName);
        contentValues.put("LASTNAME", lastName);
        contentValues.put("BIRTHDAY", birthday);
        contentValues.put("EMAILID", emailId);
        contentValues.put("GENDER", gender);
        contentValues.put("PASSWORD", password);
        contentValues.put("PHOTO", photoString);
        this.getWritableDatabase().insert("customerInfo", null, contentValues);
        return "done";
    }
    public void custPreference(int max, int min, String gender, String emailId) //5 paramteres with 1 emailid to be passes explicitly
    {
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("MAXAGE", max);
        contentValues2.put("MINRANGE", min);
        contentValues2.put("GENDER", gender);
        contentValues2.put("EMAILID", emailId);
        this.getWritableDatabase().insert("customer_preference", null, contentValues2);
    }
    public void vehicleInfo(String make, String model, int year, String color, String licencePlate, String emailId){ // 6 paramteres with 1 email Id explicitly
        ContentValues contentValues3 = new ContentValues();
        contentValues3.put("MAKE", make);
        contentValues3.put("MODEL", model);
        contentValues3.put("YEAR", year);
        contentValues3.put("COLOR", color);
        contentValues3.put("LICENCEPLATE", licencePlate);
        contentValues3.put("EMAILID", emailId);
        this.getWritableDatabase().insert("vehicleInfo", null, contentValues3);


    }

    public void paymentInfo(String cardName, int ccNum, String expDate, int ccv, int zipCode, String emailId){
        ContentValues contentValues4 = new ContentValues();
        contentValues4.put("CARDNAME", cardName);
        contentValues4.put("CCNUMBER", ccNum);
        contentValues4.put("EXPDATE", expDate);
        contentValues4.put("CCV", ccv);
        contentValues4.put("ZIPCODE", zipCode);
        contentValues4.put("EMAILID", emailId);
        this.getWritableDatabase().insert("payment_info", null, contentValues4);
    }

    public void logIn(String emailId, String pwd)  //User Id and password
    {

    }

    public void DestinationMatch(String destination, String emailId){

    }
//    public void list(EditText editText1, EditText editText2, EditText editText3) {
//        Cursor cursorInfo = this.getReadableDatabase().rawQuery("Select * from customerInfo", null);
//        while (cursorInfo.moveToNext()) {
//            editText1.append(cursorInfo.getString(1) + " " + cursorInfo.getString(2) + " " + cursorInfo.getString(3));
//        }
//        Cursor cursorPref = this.getReadableDatabase().rawQuery("Select * from customer_preference", null);
//        while (cursorPref.moveToNext()) {
//            //      editText2.append(cursorPref.getString(1) + " " + cursorPref.getString(2) + " " + cursorPref.getString(3));
//        }
//        Cursor cursorVeh = this.getReadableDatabase().rawQuery("Select * from vehicleInfo", null);
//        while (cursorVeh.moveToNext()) {
//            editText3.append(cursorVeh.getString(1) + " " + cursorVeh.getString(2) + " " + cursorVeh.getString(3));
//        }
//
//        Cursor cursorQuery = this.getReadableDatabase().rawQuery("Select pref.MAXAGE, pref.MINRANGE from customer_preference as pref, customerInfo as info where pref.EMAILID = info.EMAILID", null);
//        while (cursorQuery.moveToNext()) {
//            editText2.append(cursorQuery.getString(1));
//        }
//    }


}






