package com.calidad.sosfidoapp.sosfido.Data.Entities;

/**
 * Created by Jair Barzola on 24-Oct-17.
 */

public class ResponseReport {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public static class Place {
        private String location;
        private String latitude;
        private String longitude;

        public Place(String location,String latitude,String longitude){
            this.latitude=latitude;
            this.location=location;
            this.longitude=longitude;
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
    }

    public static class Send{
        private String person;
        private Place place;
        private String description;

        public Send(String person , Place place ,String description){
            this.setPerson(person);
            this.place=place;
            this.description=description;
        }
        public Place getPlace() {
            return place;
        }

        public void setPlace(Place place) {
            this.place = place;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPerson() {
            return person;
        }

        public void setPerson(String person) {
            this.person = person;
        }
    }

    public static class SendPhoto{
        private String report;
        private String image;


        public SendPhoto(String report,String image){
            this.setReport(report);
            this.image=image;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getReport() {
            return report;
        }

        public void setReport(String report) {
            this.report = report;
        }
    }

    public static class ReportList{
        private String id;
        private Person person;
        private Place place;
        private String description;
        private String date;
        private String report_image;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Person getPerson() {
            return person;
        }

        public void setPerson(Person person) {
            this.person = person;
        }

        public Place getPlace() {
            return place;
        }

        public void setPlace(Place place) {
            this.place = place;
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

        public String getReport_image() {
            return report_image;
        }

        public void setReport_image(String report_image) {
            this.report_image = report_image;
        }
    }

    public class Person{
        private String id;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

}
