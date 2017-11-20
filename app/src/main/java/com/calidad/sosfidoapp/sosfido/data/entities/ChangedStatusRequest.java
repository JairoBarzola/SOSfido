package com.calidad.sosfidoapp.sosfido.data.entities;

/**
 * Created by jairbarzola on 19/11/17.
 */

public class ChangedStatusRequest {

    private int status;

    public ChangedStatusRequest(int status){
        this.status=status;
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public class Response{
        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
