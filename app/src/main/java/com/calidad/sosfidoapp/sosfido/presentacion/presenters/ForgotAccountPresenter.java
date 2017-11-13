package com.calidad.sosfidoapp.sosfido.presentacion.presenters;



import com.calidad.sosfidoapp.sosfido.R;
import com.calidad.sosfidoapp.sosfido.data.entities.ResponseUser;
import com.calidad.sosfidoapp.sosfido.data.repositories.remote.ApiConstants;
import com.calidad.sosfidoapp.sosfido.data.repositories.remote.ServiceFactory;
import com.calidad.sosfidoapp.sosfido.data.repositories.remote.request.UserRequest;
import com.calidad.sosfidoapp.sosfido.presentacion.activies.ForgotAccountActivity;
import com.calidad.sosfidoapp.sosfido.presentacion.contracts.ForgotAccountContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jairbarzola on 7/11/17.
 */

public class ForgotAccountPresenter implements ForgotAccountContract.Presenter{


    public ForgotAccountActivity view;
    public ForgotAccountPresenter (ForgotAccountActivity view){
        this.view=view;
    }

    @Override
    public void start(String email, String password,String userId) {
        if(!email.equals("")){
            checkEmail(email);
        }else if(email.equals("")&&!userId.equals("")){
            updatePassword(password,userId);
        }
    }

    private void updatePassword(String password,String userId) {
        view.setLoadingIndicator(true);
        ServiceFactory serviceFactory = new ServiceFactory();
        UserRequest userRequest = serviceFactory.createService(UserRequest.class);
        Call<ResponseUser.ForgotAccount> call = userRequest.updatePassword(ApiConstants.CONTENT_TYPE,userId,password);
        call.enqueue(new Callback<ResponseUser.ForgotAccount>() {
            @Override
            public void onResponse(Call<ResponseUser.ForgotAccount> call, Response<ResponseUser.ForgotAccount> response) {
                if(response.isSuccessful()){
                    ResponseUser.ForgotAccount forgotAccountR =response.body();
                    if(forgotAccountR.isStatus()){
                        view.closeActivity();
                        view.setLoadingIndicator(false);
                    }else{
                        view.setLoadingIndicator(false);
                        view.showMessage("Hubo un error, intente mas tarde");
                    }
                }else{
                    view.setLoadingIndicator(false);
                    view.showMessage("No hubo conexión al servidor, por favor intente más tarde");
                }
            }

            @Override
            public void onFailure(Call<ResponseUser.ForgotAccount> call, Throwable t) {
                view.setLoadingIndicator(false);
                view.showMessage("No hubo conexión al servidor, por favor intente más tarde");
            }
        });
    }

    private void checkEmail(final String email) {
        view.setLoadingIndicator(true);
        ServiceFactory serviceFactory = new ServiceFactory();
        UserRequest userRequest = serviceFactory.createService(UserRequest.class);
        Call<ResponseUser.ForgotAccount> call = userRequest.sendEmail(ApiConstants.CONTENT_TYPE,email);
        call.enqueue(new Callback<ResponseUser.ForgotAccount>() {
            @Override
            public void onResponse(Call<ResponseUser.ForgotAccount> call, Response<ResponseUser.ForgotAccount> response) {
               if(response.isSuccessful()){
                ResponseUser.ForgotAccount forgotAccountR =response.body();
                if(forgotAccountR.isStatus()){
                    view.showUpdatePassword(email,forgotAccountR.getUserId());
                    view.setLoadingIndicator(false);
                }else{
                    view.setLoadingIndicator(false);
                    view.showMessage("Este correo es incorrecto");
                }
               }else{
                   view.setLoadingIndicator(false);
                   view.showMessage("No hubo conexión al servidor, por favor intente más tarde");
               }
            }
            @Override
            public void onFailure(Call<ResponseUser.ForgotAccount> call, Throwable t) {
                view.setLoadingIndicator(false);
                view.showMessage("No hubo conexión al servidor, por favor intente más tarde");
            }
        });

    }
}
