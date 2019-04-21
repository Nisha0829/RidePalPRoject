package com.example.ridepal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String Database_Name = "passenger.db";
    private static final int DATABASE_VERSION = 4;
    private SQLiteDatabase sqLliteDatabase;


    public DataBaseHelper(Context context) {
        super(context, Database_Name, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        // db.execSQL("DROP TABLE IF EXISTS vehicleInfo");
        onCreate(db);

    }

    @Override

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS customerInfo(FIRSTNAME TEXT not null,LASTNAME TEXT not null,BIRTHDAY DATE not null,EMAILID TEXT PRIMARY KEY,GENDER TEXT, PASSWORD TEXT not null, PHOTO TEXT);");
        db.execSQL("CREATE TABLE IF NOT EXISTS vehicleInfo(MAKE TEXT,MODEL TEXT,YEAR INTEGER, COLOR TEXT,LICENCEPLATE TEXT, EMAILID TEXT not null, FOREIGN KEY(EMAILID) REFERENCES customerInfo(EMAILID) );");
        db.execSQL("CREATE TABLE IF NOT EXISTS customer_preference(MAXAGE INTEGER,MINRANGE INTEGER,GENDER TEXT, EMAILID TEXT not null, FOREIGN KEY(EMAILID) REFERENCES customerInfo(EMAILID) );");
        db.execSQL("CREATE TABLE IF NOT EXISTS cust_destination(NAME TEXT, ORIGIN TEXT,DESTINATION TEXT, LATITUDE INTEGER,  LONGITUDE INTEGER,SEARCHMILES INTEGER, EMAILID TEXT not null, FOREIGN KEY(EMAILID) REFERENCES customerInfo(EMAILID) );");
        db.execSQL("CREATE TABLE IF NOT EXISTS payment_info(CARDNAME TEXT, CCNUMBER INTEGER, EXPDATE DATE, CCV INTEGER, ZIPCODE INTEGER, EMAILID TEXT not null, FOREIGN KEY(EMAILID) REFERENCES customerInfo(EMAILID) );");
       // db.execSQL("CREATE TABLE IF NOT EXISTS feature_Info(FEATURE TEXT, EMAILID TEXT not null, FOREIGN KEY(EMAILID) REFERENCES customerInfo(EMAILID) );");
        db.execSQL("CREATE TABLE IF NOT EXISTS feedback_Info(FEATURE TEXT, STAR INTEGER, EMAILID TEXT not null, FOREIGN KEY(EMAILID) REFERENCES customerInfo(EMAILID) );");
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


    public String createAccount(String firstName, String lastName, String birthday, String emailId, String gender, String password, String photoString) //createcreateAccount with 7 parameters
    {
        String result;
        try {

            ContentValues contentValues = new ContentValues();
            contentValues.put("FIRSTNAME", firstName);
            contentValues.put("LASTNAME", lastName);
            contentValues.put("BIRTHDAY", birthday);
            contentValues.put("EMAILID", emailId);
            contentValues.put("GENDER", gender);
            contentValues.put("PASSWORD", password);
            contentValues.put("PHOTO", photoString);
            this.getWritableDatabase().insert("customerInfo", null, contentValues);
            result = "done";
        } catch (SQLiteConstraintException ex) {
            result = "error";
        }
        return result;
    }

    public String custPreference(int max, int min, String gender, String emailId) //5 paramteres with 1 emailid to be passes explicitly
    {
        String result;
        try {

            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("MAXAGE", max);
            contentValues2.put("MINRANGE", min);
            contentValues2.put("GENDER", gender);
            contentValues2.put("EMAILID", emailId);
            this.getWritableDatabase().insert("customer_preference", null, contentValues2);
            result = "done";
        } catch (SQLiteConstraintException ex) {
            result = "error";
        }
        return result;
    }

    public String vehicleInfo(String make, String model, int year, String color, String licencePlate, String emailId) { // 6 paramteres with 1 email Id explicitly
        String result;
        try {
            ContentValues contentValues3 = new ContentValues();
            contentValues3.put("MAKE", make);
            contentValues3.put("MODEL", model);
            contentValues3.put("YEAR", year);
            contentValues3.put("COLOR", color);
            contentValues3.put("LICENCEPLATE", licencePlate);
            contentValues3.put("EMAILID", emailId);
            this.getWritableDatabase().insert("vehicleInfo", null, contentValues3);
            result = "done";
        } catch (SQLiteException ex) {
            System.out.print("Enter in Exception" + ex);
            result = "error";
        }
        return result;
    }

    public void paymentInfo(String cardName, int ccNum, String expDate, int ccv, int zipCode, String emailId) {
        ContentValues contentValues4 = new ContentValues();
        contentValues4.put("CARDNAME", cardName);
        contentValues4.put("CCNUMBER", ccNum);
        contentValues4.put("EXPDATE", expDate);
        contentValues4.put("CCV", ccv);
        contentValues4.put("ZIPCODE", zipCode);
        contentValues4.put("EMAILID", emailId);
        this.getWritableDatabase().insert("payment_info", null, contentValues4);
    }
//
//    public void addFeature(String feature, String email){
//        ContentValues contentValues4 = new ContentValues();
//        contentValues4.put("FEATURE", feature);
//        contentValues4.put("EMAILID", email);
//        this.getWritableDatabase().insert("feature_Info", null, contentValues4);
//    }
    public void addFeedBack(String feedBack, int star, String email){
        ContentValues contentValues4 = new ContentValues();
        contentValues4.put("FEATURE", feedBack);
        contentValues4.put("STAR", star);
        contentValues4.put("EMAILID", email);
        this.getWritableDatabase().insert("feedback_Info", null, contentValues4);
    }

    public String logIn(String emailId, String pwd)  //Success // Password is wrong, user does not exist

    {
        String result = "User Does Not Exist";
        Cursor cursor = this.getReadableDatabase().query("customerInfo", new String[]{"EMAILID", "PASSWORD", "FIRSTNAME"}, null, null, null, null, null, null);
        if (cursor != null & cursor.getCount() > 0) {
            while(cursor.moveToNext()){
                if (emailId.equalsIgnoreCase(cursor.getString(0)) && pwd.equals(cursor.getString(1))) {
               result = cursor.getString(2);
               break;
           } else if (emailId.equalsIgnoreCase(cursor.getString(0))) {
              result = "Invalid Password";
               break;
           }
            }
        }
//        Cursor cursorInfo = this.getReadableDatabase().rawQuery("Select * from customerInfo;", null);
//
//        while (cursorInfo.moveToNext()) {
//            if (emailId.equalsIgnoreCase(cursorInfo.getString(3)) && pwd.equals(cursorInfo.getString(5))) {
//                result = cursorInfo.getString(1);
//                break;
//            } else if (emailId.equalsIgnoreCase(cursorInfo.getString(3))) {
//                result = "Invalid Password";
//                break;
//            }
//
//        }
        return result;
    }

    public List<DestinationValues> DestinationMatch(String email, double latitude, double longitude)//String destination, String emailId) { // 5 is the password // 3 is emailId
    {
        double userLatG = latitude * Math.PI / 180;
        double userLonG = longitude * Math.PI / 180;
        List<DestinationValues> listDestinationValues = null;
        Cursor cursorInfo = this.getReadableDatabase().rawQuery("Select * from cust_destination where EMAILID =" + email + "; ", null);
        while (cursorInfo.moveToNext()) {
            DestinationValues destinationValues = null;
            double destLatg = cursorInfo.getDouble(0) * Math.PI / 180;
            double destLong = cursorInfo.getDouble(1) * Math.PI / 180;
            double phi = Math.abs(destLatg - destLong);
            double distance = (Math.acos(Math.cos(phi) * Math.cos(userLatG) * Math.cos(destLatg) + Math.sin(userLonG) * Math.sin(destLong))) * 6387;
            if (distance <= 5) {
                destinationValues = new DestinationValues(cursorInfo.getString(0), cursorInfo.getString(1), cursorInfo.getString(2));
            }
            if (listDestinationValues.isEmpty()) {
                listDestinationValues = new ArrayList<DestinationValues>();
            }
            if (null != destinationValues)
                listDestinationValues.add(destinationValues);

        }
        return listDestinationValues;
    }

    public Object cust_Destination(String name, String origin, String destination, double latitude, double longitude, int searchMiles, String email) {
        Object result = "";
        Cursor cursorInfo = this.getReadableDatabase().rawQuery("Select * from cust_destination where EMAILID =" + email + "; ", null);
        if (!cursorInfo.moveToNext()) {
            try {
                this.getWritableDatabase().execSQL("update student_table set ORIGIN = '" + origin + ",' DESTINATION = ' " + destination + " ,'LATITUDE = ' " + latitude + " , 'LONGITUDE ='" + longitude + ",'SEARCHMILES ='" + searchMiles + "'  WHERE EMAILID ='" + email + "'", null);
            } catch (SQLiteException ex) {

            }

        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put("Name", name);
            contentValues.put("ORIGIN", origin);
            contentValues.put("DESTINATION", destination);
            contentValues.put("LATITUDE", latitude);
            contentValues.put("LONGITUDE", longitude);
            contentValues.put("SEARCHMILES", searchMiles);
            contentValues.put("EMAILID", email);
            this.getWritableDatabase().insert("payment_info", null, contentValues);

        }
        return result = DestinationMatch(email, latitude, longitude);
    }

    public void list(TextView textView) { // For testing purpose
        // column 0,1 and 2 for respectively max, min and emailId
        //this.getReadableDatabase().rawQuery("Delete from customer_preference where EMAILID ='12345@gmail.com'", null);
        String email = "af";
        String sqlQuery = "Select * from customerInfo where EMAILID = '" + email + "';";
        Cursor cursorInfo = this.getReadableDatabase().rawQuery(sqlQuery, null);// where EMAILID = 'ad'", null);
        while (cursorInfo.moveToNext()) {
            textView.append(cursorInfo.getString(3) + " " + cursorInfo.getString(5) + " " + cursorInfo.getString(1));// + " " + cursorInfo.getString(3));
//                    " " + cursorInfo.getString(4)+" " + cursorInfo.getString(5)+" " + cursorInfo.getString(6));
        }
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
    }


}






