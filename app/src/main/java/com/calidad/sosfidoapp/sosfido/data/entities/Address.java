package com.calidad.sosfidoapp.sosfido.data.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jairbarzola on 12/11/17.
 */

public  class Address implements Parcelable {
    private String location;
    private String latitude;
    private String longitude;

    protected Address(Parcel in) {
        location = in.readString();
        latitude = in.readString();
        longitude = in.readString();
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(location);
        parcel.writeString(latitude);
        parcel.writeString(longitude);
    }
}