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
    }
    interface Presenter {
        void register(String firstName,String lastName, String location,String longitude,String latitude,
                      String birthDate,String email,String password,String phone);
    }

}
