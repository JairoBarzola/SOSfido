package com.calidad.sosfidoapp.sosfido.data.entities;

/**
 * Created by jairbarzola on 18/11/17.
 */

public class PersonDeviceEntity {
    private String person;
    private String id_device;

    public PersonDeviceEntity(String person,String id_device){
        this.person=person;
        this.id_device=id_device;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getIdDevice() {
        return id_device;
    }

    public void setIdDevice(String id_device) {
        this.id_device = id_device;
    }


    public class ResponseDevice{
        private String id;
        private String id_device;
        private boolean is_active;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIdDevice() {
            return id_device;
        }

        public void setIdDevice(String id_device) {
            this.id_device = id_device;
        }

        public boolean isActive() {
            return is_active;
        }

        public void setActive(boolean is_active) {
            this.is_active = is_active;
        }
    }
}
