package com.calidad.sosfidoapp.sosfido.data.entities;

/**
 * Created by jairbarzola on 12/11/17.
 */

public class ProposalAdoption {

    private String adoption_proposal;
    private String requester;
    private String description;

    public ProposalAdoption(String adoption_proposal,String requester,String description){
        this.adoption_proposal=adoption_proposal;
        this.requester=requester;
        this.description=description;
    }
    public String getAdoption_proposal() {
        return adoption_proposal;
    }

    public void setAdoption_proposal(String adoption_proposal) {
        this.adoption_proposal = adoption_proposal;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public class Response{
        String id;
    }
}
