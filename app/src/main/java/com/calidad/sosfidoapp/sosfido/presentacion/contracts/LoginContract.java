package com.calidad.sosfidoapp.sosfido.presentacion.contracts;

/**
 * Created by jairbarzola on 1/10/17.
 */

public interface LoginContract {
    //interfaces para el modelo Vista-Presentador
    interface View {
        void loginSuccessfully();

        void setLoadingIndicator(boolean active);

        void setMessageError(String error);

        void setDialogMessage(String message);

        boolean isActive();
    }

    interface Presenter {
        void login(String email, String password);
    }
}
