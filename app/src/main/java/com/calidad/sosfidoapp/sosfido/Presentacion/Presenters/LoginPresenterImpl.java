package com.calidad.sosfidoapp.sosfido.Presentacion.Presenters;

import android.content.Context;
import android.util.Log;

import com.calidad.sosfidoapp.sosfido.Data.Entities.AccessTokenEntity;
import com.calidad.sosfidoapp.sosfido.Data.Entities.PersonEntity;
import com.calidad.sosfidoapp.sosfido.Data.Repositories.Local.SessionManager;
import com.calidad.sosfidoapp.sosfido.Data.Repositories.Remote.ApiConstants;
import com.calidad.sosfidoapp.sosfido.Data.Repositories.Remote.Request.UserRequest;
import com.calidad.sosfidoapp.sosfido.Data.Repositories.Remote.ServiceFactory;
import com.calidad.sosfidoapp.sosfido.Presentacion.Activies.LoginActivity;
import com.calidad.sosfidoapp.sosfido.Presentacion.Contracts.LoginContract;
import com.calidad.sosfidoapp.sosfido.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jairbarzola on 1/10/17.
 */

public class LoginPresenterImpl  implements LoginContract.Presenter{

    LoginActivity view;
    Context context;
    private SessionManager sessionManager;

    public LoginPresenterImpl(LoginActivity view,Context context){
        this.view=view;
        sessionManager = new SessionManager(context);
        this.context=context;
    }
    @Override
    public void login(String email, String password) {
        view.setLoadingIndicator(true);
        UserRequest userRequest = ServiceFactory.createService(UserRequest.class);
        Call<AccessTokenEntity> call = userRequest.login(ApiConstants.CONTENT_TYPE,email,password);
        call.enqueue(new Callback<AccessTokenEntity>() {
            @Override
            public void onResponse(Call<AccessTokenEntity> call, Response<AccessTokenEntity> response) {
                Log.i("Response :",response.message());
                if(response.isSuccessful()){
                    AccessTokenEntity accessTokenEntity = response.body();
                    if(accessTokenEntity.isStatus()){
                        getAccount(accessTokenEntity);
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
            public void onFailure(Call<AccessTokenEntity> call, Throwable t) {
                view.setLoadingIndicator(false);
                view.setMessageError(context.getString(R.string.no_server_connection_try_it_later));
            }
        });
    }

    private void getAccount(final AccessTokenEntity accessTokenEntity) {
        UserRequest userRequest = ServiceFactory.createService(UserRequest.class);
        Call<PersonEntity> call = userRequest.getPerson(ApiConstants.CONTENT_TYPE,"Bearer "+String.valueOf(accessTokenEntity.getAccessToken()),String.valueOf(accessTokenEntity.getPersonId()));
        call.enqueue(new Callback<PersonEntity>() {
            @Override
            public void onResponse(Call<PersonEntity> call, Response<PersonEntity> response) {
                if(response.isSuccessful()){
                    openSession(accessTokenEntity,response.body());
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
    private void openSession(AccessTokenEntity accessTokenEntity, PersonEntity personEntity){
        sessionManager.openSession(accessTokenEntity.getAccessToken(),personEntity);
        view.loginSuccessfully();
        view.setLoadingIndicator(false);
    }
}
