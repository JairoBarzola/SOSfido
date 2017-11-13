package com.calidad.sosfidoapp.sosfido.data.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jairbarzola on 12/11/17.
 */

public class MyProposalAdoptionsEntity implements Parcelable{
    private String id;
    private String pet_name;
    private String status;
    private String description;
    private String date;
    private String adoption_image;


    protected MyProposalAdoptionsEntity(Parcel in) {
        id = in.readString();
        pet_name = in.readString();
        status = in.readString();
        description = in.readString();
        date = in.readString();
        adoption_image = in.readString();
    }

    public static final Creator<MyProposalAdoptionsEntity> CREATOR = new Creator<MyProposalAdoptionsEntity>() {
        @Override
        public MyProposalAdoptionsEntity createFromParcel(Parcel in) {
            return new MyProposalAdoptionsEntity(in);
        }

        @Override
        public MyProposalAdoptionsEntity[] newArray(int size) {
            return new MyProposalAdoptionsEntity[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPet_name() {
        return pet_name;
    }

    public void setPet_name(String pet_name) {
        this.pet_name = pet_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getAdoptionImage() {
        return adoption_image;
    }

    public void setAdoptionImage(String adoption_image) {
        this.adoption_image = adoption_image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(pet_name);
        parcel.writeString(status);
        parcel.writeString(description);
        parcel.writeString(date);
        parcel.writeString(adoption_image);
    }
}
