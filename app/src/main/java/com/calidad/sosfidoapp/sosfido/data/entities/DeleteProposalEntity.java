package com.calidad.sosfidoapp.sosfido.data.entities;

/**
 * Created by jairbarzola on 13/11/17.
 */

public class DeleteProposalEntity {
    private String was_delete;

    public String getWasDelete() {
        return was_delete;
    }

    public void setWasDelete(String was_delete) {
        this.was_delete = was_delete;
    }


    public class Reponse{
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
