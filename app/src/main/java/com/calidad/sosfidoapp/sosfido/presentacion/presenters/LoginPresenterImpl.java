package com.calidad.sosfidoapp.sosfido.presentacion.presenters;

import android.content.Context;
import android.util.Log;

import com.calidad.sosfidoapp.sosfido.data.entities.AccessTokenEntity;
import com.calidad.sosfidoapp.sosfido.data.entities.PersonDeviceEntity;
import com.calidad.sosfidoapp.sosfido.data.entities.PersonEntity;
import com.calidad.sosfidoapp.sosfido.data.repositories.local.SessionManager;
import com.calidad.sosfidoapp.sosfido.data.repositories.remote.ApiConstants;
import com.calidad.sosfidoapp.sosfido.data.repositories.remote.request.UserRequest;
import com.calidad.sosfidoapp.sosfido.data.repositories.remote.ServiceFactory;
import com.calidad.sosfidoapp.sosfido.presentacion.activies.LoginActivity;
import com.calidad.sosfidoapp.sosfido.presentacion.contracts.LoginContract;
import com.calidad.sosfidoapp.sosfido.R;
import com.onesignal.OneSignal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jairbarzola on 1/10/17.
 */

public class LoginPresenterImpl implements LoginContract.Presenter {

    private LoginActivity view;
    private Context context;
    private SessionManager sessionManager;
    private ServiceFactory serviceFactory;

    public LoginPresenterImpl(LoginActivity view, Context context) {
        this.view = view;
        sessionManager = new SessionManager(context);
        this.context = context;
        serviceFactory = new ServiceFactory();
    }

    @Override
    public void login(String email, String password) {
        saveIdDevice();
        String idDevice = sessionManager.getIdDevice();
        view.setLoadingIndicator(true);
        UserRequest userRequest = serviceFactory.createService(UserRequest.class);
        Call<AccessTokenEntity> call = userRequest.login(ApiConstants.CONTENT_TYPE, email, password,idDevice);
        call.enqueue(new Callback<AccessTokenEntity>() {
            @Override
            public void onResponse(Call<AccessTokenEntity> call, Response<AccessTokenEntity> response) {
                if (response.isSuccessful()) {
                    AccessTokenEntity accessTokenEntity = response.body();
                    if (accessTokenEntity.isStatus()) {
                        getAccount(accessTokenEntity);
                    } else {
                        view.setLoadingIndicator(false);
                        view.setMessageError("El usuario y la contraseña no coinciden");
                    }
                } else {
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
        UserRequest userRequest = serviceFactory.createService(UserRequest.class);
        Call<PersonEntity> call = userRequest.getPerson(ApiConstants.CONTENT_TYPE, "Bearer " + String.valueOf(accessTokenEntity.getAccessToken()), String.valueOf(accessTokenEntity.getPersonId()));
        call.enqueue(new Callback<PersonEntity>() {
            @Override
            public void onResponse(Call<PersonEntity> call, Response<PersonEntity> response) {
                if (response.isSuccessful()) {
                    openSession(accessTokenEntity, response.body());
                } else {
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

    private void openSession(AccessTokenEntity accessTokenEntity, PersonEntity personEntity) {
        sessionManager.openSession(accessTokenEntity.getAccessToken(), personEntity);
        if(accessTokenEntity.isIsRegistered()){
           // sessionManager.openSession(accessTokenEntity.getAccessToken(), personEntity);
            Log.i("TAG","REGISTRADO EN CON ESTE TELEFONO");
        }else{
            //guardar id del telefono
            OneSignal.startInit(context)
                    .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                    .unsubscribeWhenNotificationsAreDisabled(true)
                    .init();
            registerMyDevice();
            Log.i("TAG","SE ACABA DE REGISTRA EL TELEFONO");
        }
        view.loginSuccessfully();
        view.setLoadingIndicator(false);
    }

    private void registerMyDevice(){
        String idDevice = sessionManager.getIdDevice();
        UserRequest  userRequest = serviceFactory.createService(UserRequest.class);
        PersonDeviceEntity personDeviceEntity = new PersonDeviceEntity(String.valueOf(sessionManager.getPersonEntity().getId()),idDevice);
        Call<PersonDeviceEntity.ResponseDevice> call = userRequest.registerDevide(ApiConstants.CONTENT_TYPE_JSON, "Bearer " + sessionManager.getUserToken(),personDeviceEntity);
        call.enqueue(new Callback<PersonDeviceEntity.ResponseDevice>() {
            @Override
            public void onResponse(Call<PersonDeviceEntity.ResponseDevice> call, Response<PersonDeviceEntity.ResponseDevice> response) {
                if(response.isSuccessful()){
                    Log.i("DEVICE","DEVICE ID REGISTRADO");
                }
            }

            @Override
            public void onFailure(Call<PersonDeviceEntity.ResponseDevice> call, Throwable t) {
                view.setLoadingIndicator(false);
                view.setMessageError(context.getString(R.string.no_server_connection_try_it_later));
            }
        });

    }
    private void saveIdDevice() {

        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable(String userId, String registrationId) {
                sessionManager.saveDevice(userId);
            }
        });
    }
}
