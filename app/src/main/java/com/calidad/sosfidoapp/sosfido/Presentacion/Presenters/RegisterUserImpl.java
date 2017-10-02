package com.calidad.sosfidoapp.sosfido.Presentacion.Presenters;

import android.content.Context;

import com.calidad.sosfidoapp.sosfido.Data.Entities.AccessTokenEntity;
import com.calidad.sosfidoapp.sosfido.Data.Entities.PersonEntity;
import com.calidad.sosfidoapp.sosfido.Data.Entities.ResponseRegisterEntity;
import com.calidad.sosfidoapp.sosfido.Data.Repositories.Local.SessionManager;
import com.calidad.sosfidoapp.sosfido.Data.Repositories.Remote.ApiConstants;
import com.calidad.sosfidoapp.sosfido.Data.Repositories.Remote.Request.UserRequest;
import com.calidad.sosfidoapp.sosfido.Data.Repositories.Remote.ServiceFactory;
import com.calidad.sosfidoapp.sosfido.Presentacion.Activies.RegisterUserActivity;
import com.calidad.sosfidoapp.sosfido.Presentacion.Contracts.RegisterUserContract;
import com.calidad.sosfidoapp.sosfido.Presentacion.Fragments.RegisterUserFragment;
import com.calidad.sosfidoapp.sosfido.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jairbarzola on 1/10/17.
 */

public class RegisterUserImpl implements RegisterUserContract.Presenter{

    RegisterUserFragment view;
    Context context;
    private SessionManager sessionManager;

    public  RegisterUserImpl(RegisterUserFragment view, Context context){
        this.view=view;
        sessionManager = new SessionManager(context);
        this.context=context;
    }


    @Override
    public void register(String firstName, String lastName, String dni, String gener, String district, String birthDate, String email, String password,String phone) {

        view.setLoadingIndicator(true);
        UserRequest userRequest = ServiceFactory.createService(UserRequest.class);
        Call<ResponseRegisterEntity> call = userRequest.registerUser(ApiConstants.CONTENT_TYPE,firstName,lastName,
                email,password,dni,gener,birthDate,phone,district);
        call.enqueue(new Callback<ResponseRegisterEntity>() {
            @Override
            public void onResponse(Call<ResponseRegisterEntity> call, Response<ResponseRegisterEntity> response) {

                if(response.isSuccessful()){
                    ResponseRegisterEntity responseRegisterEntity = response.body();
                    if(responseRegisterEntity.isStatus()){
                        openHome(responseRegisterEntity);
                    }else{
                        view.setLoadingIndicator(false);
                        view.setMessageError(context.getString(R.string.there_was_an_error_try_it_later));
                    }
                }
                else{
                    view.setLoadingIndicator(false);
                    view.setMessageError(context.getString(R.string.there_was_an_error_try_it_later));
                }
            }

            @Override
            public void onFailure(Call<ResponseRegisterEntity> call, Throwable t) {
                view.setLoadingIndicator(false);
                view.setMessageError(context.getString(R.string.no_server_connection_try_it_later));
            }
        });

    }

    private void openHome(final ResponseRegisterEntity responseRegisterEntity) {
        UserRequest userRequest = ServiceFactory.createService(UserRequest.class);
        Call<PersonEntity> call = userRequest.getPerson(ApiConstants.CONTENT_TYPE,"Bearer "+String.valueOf(responseRegisterEntity.getAccess_token()),String.valueOf(responseRegisterEntity.getPerson_id()));
        call.enqueue(new Callback<PersonEntity>() {
            @Override
            public void onResponse(Call<PersonEntity> call, Response<PersonEntity> response) {
                if(response.isSuccessful()){
                    openSession(responseRegisterEntity,response.body());
                }
                else{
                    view.setLoadingIndicator(false);
                    view.setMessageError(context.getString(R.string.there_was_an_error_try_it_later));
                }
            }

            @Override
            public void onFailure(Call<PersonEntity> call, Throwable t) {
                view.setLoadingIndicator(false);
                view.setMessageError(context.getString(R.string.no_server_connection_try_it_later));
            }
        });
    }
    private void openSession(ResponseRegisterEntity responseRegisterEntity, PersonEntity personEntity){
        view.setLoadingIndicator(false);
        sessionManager.openSession(responseRegisterEntity.getAccess_token(),personEntity);
        view.registerSuccessfully();
    }
}
