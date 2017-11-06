package com.calidad.sosfidoapp.sosfido.data.entities;

/**
 * Created by Jair Barzola on 22-Oct-17.
 */

public class ResponseUser {


    public  class Photo{

        private String url_image;

        public String getUrl_image() {
            return url_image;
        }

        public void setUrl_image(String url_image) {
            this.url_image = url_image;
        }

    }
    public static class PhotoChange{
        private  String id;
        private  String image;
        private String url_image;
        private String upload_date;
        private String person;


        public  PhotoChange(String image){
            this.image=image;
        }
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }


        public String getUploadDate() {
            return upload_date;
        }

        public void setUploadDate(String upload_date) {
            this.upload_date = upload_date;
        }

        public String getPerson() {
            return person;
        }

        public void setPerson(String person) {
            this.person = person;
        }

        public String getUrlImage() {
            return url_image;
        }

        public void setUrlImage(String url_image) {
            this.url_image = url_image;
        }
    }
    public static class PhotoBody{
        private String person;
        private String image;

        public PhotoBody (String person,String image){
            this.person=person;
            this.image=image;
        }

        public String getPerson() {
            return person;
        }

        public void setPerson(String person) {
            this.person = person;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
