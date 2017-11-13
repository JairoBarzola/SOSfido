package com.calidad.sosfidoapp.sosfido.data.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jairbarzola on 1/10/17.
 */

public class PersonEntity implements Parcelable {
    private int id;
    private UserEntity user;
    private String born_date;
    private String phone_number;
    private Address address;
    private String person_image;

    public PersonEntity (int id,UserEntity user,String born_date,String phone_number,Address address,String person_image){
        this.id=id;
        this.user=user;
        this.born_date=born_date;
        this.phone_number=phone_number;
        this.address=address;
        this.person_image=person_image;
    }


    protected PersonEntity(Parcel in) {
        id = in.readInt();
        born_date = in.readString();
        phone_number = in.readString();
        person_image = in.readString();
    }

    public static final Creator<PersonEntity> CREATOR = new Creator<PersonEntity>() {
        @Override
        public PersonEntity createFromParcel(Parcel in) {
            return new PersonEntity(in);
        }

        @Override
        public PersonEntity[] newArray(int size) {
            return new PersonEntity[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getBornDate() {
        return born_date;
    }

    public void setBornDate(String born_date) {
        this.born_date = born_date;
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public void setPhoneNumber(String phone_number) {
        this.phone_number = phone_number;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPersonImage() {
        return person_image;
    }

    public void setPersonImage(String person_image) {
        this.person_image = person_image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(born_date);
        parcel.writeString(phone_number);
        parcel.writeString(person_image);
        parcel.writeParcelable(address,i);
        parcel.writeParcelable(user,i);
    }
}
