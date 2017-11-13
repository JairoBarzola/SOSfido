package com.calidad.sosfidoapp.sosfido.presentacion.contracts;

import com.calidad.sosfidoapp.sosfido.data.entities.ProposalAdoption;

/**
 * Created by jairbarzola on 12/11/17.
 */

public interface DetailMarkerContract {

    interface View{
        void setLoadingIndicator(boolean active);

        void setMessageError(String error);
    }
    interface Presenter{

        void sendProposalAdoption(ProposalAdoption proposalAdoption);
    }
}
