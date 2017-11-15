package com.calidad.sosfidoapp.sosfido.data.entities;

/**
 * Created by jairbarzola on 14/11/17.
 */

public class MyRequestEntity {
    private String id;
    private ProposalRequestEntity adoption_proposal;
    private String status;
    private String description;
    private String date;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProposalRequestEntity getAdoptionProposal() {
        return adoption_proposal;
    }

    public void setAdoptionProposal(ProposalRequestEntity adoption_proposal) {
        this.adoption_proposal = adoption_proposal;
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
}
