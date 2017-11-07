package com.calidad.sosfidoapp.sosfido.presentacion.contracts;

/**
 * Created by jairbarzola on 7/11/17.
 */

public interface ForgotAccountContract {
    //interfaces para el modelo Vista-Presentador
    interface View {
        void showUpdatePassword(String message,String userID);
        void setLoadingIndicator(boolean active);
        void showMessage(String message);
        void closeActivity();

    }

    interface Presenter {

        void start(String email,String password,String userId);
    }
}
