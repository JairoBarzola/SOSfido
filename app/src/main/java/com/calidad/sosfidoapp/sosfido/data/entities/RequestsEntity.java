package com.calidad.sosfidoapp.sosfido.data.entities;

/**
 * Created by jairbarzola on 13/11/17.
 */

public class RequestsEntity {

    private String id;
    private PersonEntity requester;
    private String status;
    private String description;
    private String date;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public PersonEntity getRequester() {
        return requester;
    }

    public void setRequester(PersonEntity requester) {
        this.requester = requester;
    }
}
