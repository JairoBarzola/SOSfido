package com.calidad.sosfidoapp.sosfido.data.entities;

/**
 * Created by jairbarzola on 13/11/17.
 */

public class DeleteProposalEntity {
    private boolean was_deleted;

    public DeleteProposalEntity(boolean was_deleted){
        this.was_deleted=was_deleted;
    }


    public boolean getWasDelete() {
        return was_deleted;
    }

    public void setWasDelete(boolean was_deleted) {
        this.was_deleted = was_deleted;
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
