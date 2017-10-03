package com.calidad.sosfidoapp.sosfido.Presentacion.Contracts;

/**
 * Created by jairbarzola on 1/10/17.
 */

public interface RegisterUserContract {
    //interfaces para el modelo Vista-Presentador
    interface View {
        void registerSuccessfully();
        void setLoadingIndicator(boolean active);
        void setMessageError(String error);
        void setDialogMessage(String message);
    }
    interface Presenter {
        void register(String firstName,String lastName, String district,
                      String birthDate,String email,String password,String phone);
    }

}
