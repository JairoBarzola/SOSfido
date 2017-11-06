package com.calidad.sosfidoapp.sosfido.data.entities;

/**
 * Created by jairbarzola on 1/10/17.
 */

public class PersonEntity {
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


    public  class Address{
        private String location;
        private String latitude;
        private String longitude;

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
    }
}
