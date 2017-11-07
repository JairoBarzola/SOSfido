package com.calidad.sosfidoapp.sosfido.data.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jairbarzola on 31/10/17.
 */

public class ReportEntity implements Parcelable {

    private String idReport;
    private String location;
    private String latitude;
    private String longitude;
    private String date;
    private String photo;
    private String namePet;
    private String description;
    private String typeReport;


    public ReportEntity(String idReport,String location,String latitude,String longitude,String date,String photo,String namePet,String description
                        ,String typeReport){
        this.idReport=idReport;
        this.location=location;
        this.latitude=latitude;
        this.longitude=longitude;
        this.date=date;
        this.photo=photo;
        this.namePet=namePet;
        this.description=description;
        this.typeReport=typeReport;
    }

    protected ReportEntity(Parcel in) {
        idReport = in.readString();
        location = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        date = in.readString();
        photo = in.readString();
        namePet = in.readString();
        description = in.readString();
        typeReport = in.readString();
    }

    public static final Creator<ReportEntity> CREATOR = new Creator<ReportEntity>() {
        @Override
        public ReportEntity createFromParcel(Parcel in) {
            return new ReportEntity(in);
        }

        @Override
        public ReportEntity[] newArray(int size) {
            return new ReportEntity[size];
        }
    };

    public String getIdReport() {
        return idReport;
    }

    public void setIdReport(String idReport) {
        this.idReport = idReport;
    }

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getNamePet() {
        return namePet;
    }

    public void setNamePet(String namePet) {
        this.namePet = namePet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTypeReport() {
        return typeReport;
    }

    public void setTypeReport(String typeReport) {
        this.typeReport = typeReport;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(idReport);
        parcel.writeString(location);
        parcel.writeString(latitude);
        parcel.writeString(longitude);
        parcel.writeString(date);
        parcel.writeString(photo);
        parcel.writeString(namePet);
        parcel.writeString(description);
        parcel.writeString(typeReport);
    }
}
