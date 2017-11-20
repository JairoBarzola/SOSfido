package com.calidad.sosfidoapp.sosfido.presentacion.presenters;

import android.content.Context;
import android.util.Log;


import com.calidad.sosfidoapp.sosfido.data.entities.PersonDeviceEntity;
import com.calidad.sosfidoapp.sosfido.data.entities.PersonEntity;
import com.calidad.sosfidoapp.sosfido.data.entities.ResponseRegisterEntity;
import com.calidad.sosfidoapp.sosfido.data.repositories.local.SessionManager;
import com.calidad.sosfidoapp.sosfido.data.repositories.remote.ApiConstants;
import com.calidad.sosfidoapp.sosfido.data.repositories.remote.request.UserRequest;
import com.calidad.sosfidoapp.sosfido.data.repositories.remote.ServiceFactory;
import com.calidad.sosfidoapp.sosfido.presentacion.contracts.RegisterUserContract;
import com.calidad.sosfidoapp.sosfido.presentacion.fragments.RegisterUserFragment;
import com.calidad.sosfidoapp.sosfido.R;
import com.onesignal.OneSignal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jairbarzola on 1/10/17.
 */

public class RegisterUserPresenterImpl implements RegisterUserContract.Presenter {

    private RegisterUserFragment view;
    private Context context;
    private SessionManager sessionManager;
    private ServiceFactory serviceFactory;

    public RegisterUserPresenterImpl(RegisterUserFragment view, Context context) {
        this.view = view;
        sessionManager = new SessionManager(context);
        this.context = context;
        serviceFactory = new ServiceFactory();
    }

    @Override
    public void register(String firstName, String lastName, String location, String longitude, String latitude, String birthDate, String email, String password, String phone) {

        view.setLoadingIndicator(true);
        UserRequest userRequest = serviceFactory.createService(UserRequest.class);
        Call<ResponseRegisterEntity> call = userRequest.registerUser(ApiConstants.CONTENT_TYPE, firstName, lastName,
                email, password, birthDate, location, latitude, longitude, phone);
        call.enqueue(new Callback<ResponseRegisterEntity>() {
            @Override
            public void onResponse(Call<ResponseRegisterEntity> call, Response<ResponseRegisterEntity> response) {

                if (response.isSuccessful()) {
                    ResponseRegisterEntity responseRegisterEntity = response.body();
                    if (responseRegisterEntity.isStatus()) {
                        openHome(responseRegisterEntity);
                    } else {
                        view.setLoadingIndicator(false);
                        view.setMessageError(context.getString(R.string.there_was_an_error_try_it_later));
                    }
                } else {
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
        UserRequest userRequest = serviceFactory.createService(UserRequest.class);
        Call<PersonEntity> call = userRequest.getPerson(ApiConstants.CONTENT_TYPE, "Bearer " + String.valueOf(responseRegisterEntity.getAccessToken())
                , String.valueOf(responseRegisterEntity.getPersonId()));
        call.enqueue(new Callback<PersonEntity>() {
            @Override
            public void onResponse(Call<PersonEntity> call, Response<PersonEntity> response) {
                if (response.isSuccessful()) {
                    openSession(responseRegisterEntity, response.body());
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

    private void openSession(ResponseRegisterEntity responseRegisterEntity, PersonEntity personEntity) {

        sessionManager.openSession(responseRegisterEntity.getAccessToken(), personEntity);
        registerMyDevice();
        view.registerSuccessfully();
    }

    private void registerMyDevice() {
        OneSignal.startInit(context)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
        saveIdDevice();
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
