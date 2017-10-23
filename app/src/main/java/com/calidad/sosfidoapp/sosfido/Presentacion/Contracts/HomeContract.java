package com.calidad.sosfidoapp.sosfido.Presentacion.Contracts;

import com.calidad.sosfidoapp.sosfido.Data.Entities.PersonEntity;

/**
 * Created by jairbarzola on 2/10/17.
 */

public interface HomeContract {
    //interfaces para el modelo Vista-Presentador
    interface View {
        void setLoadingIndicator(boolean active);
        void setMessageError(String error);
        void setDialogMessage(String message);
    }
    interface Presenter {
        void loadReports();
    }
}
