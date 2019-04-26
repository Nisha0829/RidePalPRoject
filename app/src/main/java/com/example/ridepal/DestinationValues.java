package com.example.ridepal;

import android.os.Parcel;
import android.os.Parcelable;

public class DestinationValues implements Parcelable {
    public final String origin;
    public final String destination;
    public final double originLat;
    public final double originLog;
    public final double destLat;
    public final double destLog;
    public final String name;
    public final String emailId;
    public final String photoString;

    public DestinationValues(Parcel source){
        this.name = source.readString();
        this.origin = source.readString();// name
        this.destination = source.readString();
        this.emailId= source.readString();
        this.photoString = source.readString();
        this.originLat = source.readDouble();
        this.originLog = source.readDouble();
        this.destLat =source.readDouble();
        this.destLog = source.readDouble();

    }

    DestinationValues(String origin, String destination, String name, String emailId, String photoString, double originLat, double originLog, double destLat, double destLog ) {
        this.origin = origin;
        this.destination = destination;
        this.name = name;
        this.emailId= emailId;
        this.photoString = photoString;
        this.originLat = originLat;
        this.originLog = originLog;
        this.destLat =destLat;
        this.destLog =destLog;
    }

    public int describeContents() {
        return this.hashCode();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(origin);
        dest.writeString(destination);
        dest.writeString(name);
        dest.writeString(emailId);
        dest.writeString(photoString);
        dest.writeDouble(originLat);
        dest.writeDouble(originLog);
        dest.writeDouble(destLat);
        dest.writeDouble(destLog);
    }

    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public DestinationValues createFromParcel(Parcel in) {
            return new DestinationValues(in);
        }

        public DestinationValues[] newArray(int size) {
            return new DestinationValues[size];
        }
    };
}
