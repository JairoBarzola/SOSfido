package com.calidad.sosfidoapp.sosfido.presentacion.contracts;

import com.calidad.sosfidoapp.sosfido.data.entities.PersonEntity;

/**
 * Created by jairbarzola on 18/11/17.
 */

public interface EditUserContract {

    interface View{
        void setLoadingIndicator(boolean active);
        void setMessageError(String error);
    }
    interface Presenter{
        void sendDataChanged(PersonEntity personEntity);
    }
}
