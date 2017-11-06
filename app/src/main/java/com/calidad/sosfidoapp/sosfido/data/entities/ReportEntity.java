package com.calidad.sosfidoapp.sosfido.data.entities;

/**
 * Created by jairbarzola on 31/10/17.
 */

public class ReportEntity {

    private String idReport;
    private String location;
    private String latitude;
    private String longitude;
    private String date;
    private String photo;
    private String namePet;
    private String description;
    private String typeReport;


    public ReportEntity(String idReport,String location,String latitude,String longitude,String date,String photo,String namePet,String description
                        ,String typeReport){
        this.idReport=idReport;
        this.location=location;
        this.latitude=latitude;
        this.longitude=longitude;
        this.date=date;
        this.photo=photo;
        this.namePet=namePet;
        this.description=description;
        this.typeReport=typeReport;
    }

    public String getIdReport() {
        return idReport;
    }

    public void setIdReport(String idReport) {
        this.idReport = idReport;
    }

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getNamePet() {
        return namePet;
    }

    public void setNamePet(String namePet) {
        this.namePet = namePet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTypeReport() {
        return typeReport;
    }

    public void setTypeReport(String typeReport) {
        this.typeReport = typeReport;
    }
}
