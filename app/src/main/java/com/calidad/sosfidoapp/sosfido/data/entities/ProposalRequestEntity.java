package com.calidad.sosfidoapp.sosfido.data.entities;

/**
 * Created by jairbarzola on 13/11/17.
 */

public class ProposalRequestEntity {
    private String id;
    private PersonEntity owner;
    private String pet_name;
    private String status;
    private String description;
    private String date;
    private String adoption_image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PersonEntity getOwner() {
        return owner;
    }

    public void setOwner(PersonEntity owner) {
        this.owner = owner;
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

    public String getAdoption_image() {
        return adoption_image;
    }

    public void setAdoption_image(String adoption_image) {
        this.adoption_image = adoption_image;
    }

}
