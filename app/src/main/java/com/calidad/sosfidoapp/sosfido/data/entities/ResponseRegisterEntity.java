package com.calidad.sosfidoapp.sosfido.data.entities;

/**
 * Created by jairbarzola on 1/10/17.
 */

public class ResponseRegisterEntity {
    private int person_id;
    private String access_token;
    private boolean status;


    public int getPersonId() {
        return person_id;
    }

    public void setPersonId(int person_id) {
        this.person_id = person_id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


    public String getAccessToken() {
        return access_token;
    }

    public void setAccessToken(String access_token) {
        this.access_token = access_token;
    }
}
