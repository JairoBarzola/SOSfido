package com.calidad.sosfidoapp.sosfido.data.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jairbarzola on 12/11/17.
 */

public class ReportAdoptionEntity implements Parcelable {
    private String idReport;
    private String idPerson;
    private String phoneNumber;
    private String personImage;
    private String petName;
    private String adopter;
    private String description;
    private String date;
    private String adoptionImage;

    public ReportAdoptionEntity(String idReport,String idPerson,
                                String phoneNumber,String personImage,String petName,
                                String adopter,String description,String date,String adoptionImage){
        this.idReport=idReport;
        this.phoneNumber=phoneNumber;
        this.personImage=personImage;
        this.petName=petName;
        this.adopter=adopter;
        this.description=description;
        this.date=date;
        this.adoptionImage=adoptionImage;
        this.idPerson=idPerson;

    }

    protected ReportAdoptionEntity(Parcel in) {
        idReport = in.readString();
        phoneNumber = in.readString();
        personImage = in.readString();
        petName = in.readString();
        adopter = in.readString();
        description = in.readString();
        date = in.readString();
        setAdoptionImage(in.readString());
    }

    public static final Creator<ReportAdoptionEntity> CREATOR = new Creator<ReportAdoptionEntity>() {
        @Override
        public ReportAdoptionEntity createFromParcel(Parcel in) {
            return new ReportAdoptionEntity(in);
        }

        @Override
        public ReportAdoptionEntity[] newArray(int size) {
            return new ReportAdoptionEntity[size];
        }
    };

    public String getIdReport() {
        return idReport;
    }

    public void setIdReport(String idReport) {
        this.idReport = idReport;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPersonImage() {
        return personImage;
    }

    public void setPersonImage(String personImage) {
        this.personImage = personImage;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }
    public String getAdopter() {
        return adopter;
    }

    public void setAdopter(String adopter) {
        this.adopter = adopter;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(idReport);
        parcel.writeString(phoneNumber);
        parcel.writeString(personImage);
        parcel.writeString(petName);
        parcel.writeString(adopter);
        parcel.writeString(description);
        parcel.writeString(date);
        parcel.writeString(getAdoptionImage());
    }

    public String getAdoptionImage() {
        return adoptionImage;
    }

    public void setAdoptionImage(String adoptionImage) {
        this.adoptionImage = adoptionImage;
    }

    public String getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(String idPerson) {
        this.idPerson = idPerson;
    }
}
