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
    public String getAdoptionProposal() {
        return adoption_proposal;
    }

    public void setAdoptionProposal(String adoption_proposal) {
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
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
