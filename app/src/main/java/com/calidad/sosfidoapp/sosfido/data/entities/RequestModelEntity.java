package com.calidad.sosfidoapp.sosfido.data.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jairbarzola on 19/11/17.
 */

public class RequestModelEntity implements Parcelable {
    private String idRequest;
    private String idProposal;
    private String pet_name;
    private String statusProposal;
    private String descriptionProposal;
    private String dateProposal;
    private String phone_number;
    private String first_name;
    private String last_name;
    private String email;
    private String adoption_image;
    private String status;
    private String description;
    private String date;

    public RequestModelEntity(String idRequest,String idProposal,String pet_name,
                              String statusProposal,String descriptionProposal,String dateProposal,
                              String phone_number,String first_name,String last_name,String email,
                              String adoption_image,String status,String description,String date){
        this.idRequest=idRequest;
        this.idProposal=idProposal;
        this.pet_name=pet_name;
        this.statusProposal = statusProposal;
        this.descriptionProposal=descriptionProposal;
        this.dateProposal=dateProposal;
        this.phone_number=phone_number;
        this.first_name=first_name;
        this.last_name=last_name;
        this.email=email;
        this.adoption_image=adoption_image;
        this.status=status;
        this.description=description;
        this.date=date;

    }

    protected RequestModelEntity(Parcel in) {
        idRequest = in.readString();
        idProposal = in.readString();
        pet_name = in.readString();
        statusProposal = in.readString();
        descriptionProposal = in.readString();
        dateProposal = in.readString();
        phone_number = in.readString();
        first_name = in.readString();
        last_name = in.readString();
        email = in.readString();
        adoption_image = in.readString();
        status = in.readString();
        description = in.readString();
        date = in.readString();
    }

    public static final Creator<RequestModelEntity> CREATOR = new Creator<RequestModelEntity>() {
        @Override
        public RequestModelEntity createFromParcel(Parcel in) {
            return new RequestModelEntity(in);
        }

        @Override
        public RequestModelEntity[] newArray(int size) {
            return new RequestModelEntity[size];
        }
    };

    public String getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(String idRequest) {
        this.idRequest = idRequest;
    }

    public String getIdProposal() {
        return idProposal;
    }

    public void setIdProposal(String idProposal) {
        this.idProposal = idProposal;
    }

    public String getPetName() {
        return pet_name;
    }

    public void setPetName(String pet_name) {
        this.pet_name = pet_name;
    }

    public String getStatusProposal() {
        return statusProposal;
    }

    public void setStatusProposal(String statusProposal) {
        this.statusProposal = statusProposal;
    }

    public String getDescriptionProposal() {
        return descriptionProposal;
    }

    public void setDescriptionProposal(String descriptionProposal) {
        this.descriptionProposal = descriptionProposal;
    }

    public String getDateProposal() {
        return dateProposal;
    }

    public void setDateProposal(String dateProposal) {
        this.dateProposal = dateProposal;
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public void setPhoneNumber(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdoptionImage() {
        return adoption_image;
    }

    public void setAdoptionImage(String adoption_image) {
        this.adoption_image = adoption_image;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(idRequest);
        parcel.writeString(idProposal);
        parcel.writeString(pet_name);
        parcel.writeString(statusProposal);
        parcel.writeString(descriptionProposal);
        parcel.writeString(dateProposal);
        parcel.writeString(phone_number);
        parcel.writeString(first_name);
        parcel.writeString(last_name);
        parcel.writeString(email);
        parcel.writeString(adoption_image);
        parcel.writeString(status);
        parcel.writeString(description);
        parcel.writeString(date);
    }
}
