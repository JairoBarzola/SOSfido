package com.calidad.sosfidoapp.sosfido.data.entities;

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
            this.person=person;
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

    public static class SendPhotoAdoption{
        private String adoption_proposal;
        private String image;

        public SendPhotoAdoption(String adoption_proposal,String image){
            this.adoption_proposal=adoption_proposal;
            this.image=image;
        }

        public String getAdoptionProposal() {
            return adoption_proposal;
        }

        public void setAdoptionProposal(String adoption_proposal) {
            this.adoption_proposal = adoption_proposal;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
    public static class SendPhoto{
        private String report;
        private String image;


        public SendPhoto(String report,String image){
            this.report=report;
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

        public String getReportImage() {
            return report_image;
        }

        public void setReportImage(String report_image) {
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

    public static class SendMissing{
        private String person;
        private String pet_name;
        private Place place;
        private String description;

        public SendMissing(String person, String pet_name, Place place, String description) {
            this.person=person;
            this.pet_name=pet_name;
            this.place=place;
            this.description=description;
        }

        public String getPetName() {
            return pet_name;
        }

        public void setPetName(String pet_name) {
            this.pet_name = pet_name;
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

    public static class ReportListMissing{
        private String id;
        private Person person;
        private String pet_name;
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

        public String getPetName() {
            return pet_name;
        }

        public void setPetName(String pet_name) {
            this.pet_name = pet_name;
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

        public void setReportImage(String report_image) {
            this.report_image = report_image;
        }
    }

    public static class SendAdoption{
        private String owner;
        private String pet_name;
        private String description;

        public SendAdoption(String owner,String pet_name,String description){
            this.owner=owner;
            this.pet_name=pet_name;
            this.description=description;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public String getPetName() {
            return pet_name;
        }

        public void setPetName(String pet_name) {
            this.pet_name = pet_name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public static class ReportListAdoption{
        private String id;
        private PersonEntity owner;
        private String pet_name;
        private String adopter;
        private String description;
        private String date;
        private String adoption_image;

        public PersonEntity getOwner() {
            return owner;
        }

        public void setOwner(PersonEntity owner) {
            this.owner = owner;
        }

        public String getPetName() {
            return pet_name;
        }

        public void setPetName(String pet_name) {
            this.pet_name = pet_name;
        }

        public String getAdopter() {
            return adopter;
        }

        public void setAdopter(String adopter) {
            this.adopter = adopter;
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

        public String getAdoptionImage() {
            return adoption_image;
        }

        public void setAdoptionImage(String adoption_image) {
            this.adoption_image = adoption_image;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

}
