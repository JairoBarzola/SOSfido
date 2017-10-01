package com.calidad.sosfidoapp.sosfido.Data.Entities;

/**
 * Created by jairbarzola on 1/10/17.
 */

public class AccessTokenEntity {
    private boolean status;
    private int person_id;
    private String access_token;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }


    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}
