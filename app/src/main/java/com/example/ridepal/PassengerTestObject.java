package com.example.ridepal;

import com.google.android.gms.maps.model.LatLng;

public class PassengerTestObject {

    private String emailID;
    private String name;
    private String picture;
    private String destName;
    private String originName;
    private LatLng destLatLng, originLatLng;

    public PassengerTestObject(String emailID, String name, String picture, String destName, String originName, LatLng originLatLng, LatLng destLatLng) {
        this.emailID = emailID;
        this.name = name;
        this.picture = picture;
        this.destName = destName;
        this.originName = originName;
        this.destLatLng = destLatLng;
        this.originLatLng = originLatLng;
    }

    public PassengerTestObject(){

    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDestName() {
        return destName;
    }

    public void setDestName(String destName) {
        this.destName = destName;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public LatLng getDestLatLng() {
        return destLatLng;
    }

    public void setDestLatLng(LatLng destLatLng) {
        this.destLatLng = destLatLng;
    }

    public LatLng getOriginLatLng() {
        return originLatLng;
    }

    public void setOriginLatLng(LatLng originLatLng) {
        this.originLatLng = originLatLng;
    }
}
