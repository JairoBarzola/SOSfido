package com.calidad.sosfidoapp.sosfido.presentacion.presenters;

import android.content.Context;

import com.calidad.sosfidoapp.sosfido.data.entities.PersonEntity;
import com.calidad.sosfidoapp.sosfido.data.entities.ResponseUser;
import com.calidad.sosfidoapp.sosfido.data.repositories.local.SessionManager;
import com.calidad.sosfidoapp.sosfido.data.repositories.remote.ApiConstants;
import com.calidad.sosfidoapp.sosfido.data.repositories.remote.request.UserRequest;
import com.calidad.sosfidoapp.sosfido.data.repositories.remote.ServiceFactory;
import com.calidad.sosfidoapp.sosfido.presentacion.contracts.ProfileContract;
import com.calidad.sosfidoapp.sosfido.presentacion.fragments.ProfileFragment;
import com.calidad.sosfidoapp.sosfido.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jairbarzola on 2/10/17.
 */

public class ProfilePresenterImpl implements ProfileContract.Presenter {

    private ServiceFactory serviceFactory;
    private ProfileFragment view;
    private SessionManager sessionManager;
    private Context context;

    public ProfilePresenterImpl(ProfileFragment view, Context context) {
        this.view = view;
        this.context = context;
        sessionManager = new SessionManager(context);
        serviceFactory = new ServiceFactory();
    }

    @Override
    public void start() {
        PersonEntity personEntity = sessionManager.getPersonEntity();
        if (personEntity.getPersonImage().contains("http")) {
            view.loadUser(personEntity, true);
        } else {
            view.loadUser(personEntity, false);
        }
    }

    @Override
    public void uploadPhoto(final String path) {
        view.setLoadingIndicator(true);
        UserRequest userRequest = serviceFactory.createService(UserRequest.class);
        Call<ResponseUser.Photo> call = userRequest.uploadPhoto(ApiConstants.CONTENT_TYPE_JSON, "Bearer " + sessionManager.getUserToken(),
                new ResponseUser.PhotoBody(String.valueOf(sessionManager.getPersonEntity().getId()), path));
        call.enqueue(new Callback<ResponseUser.Photo>() {
            @Override
            public void onResponse(Call<ResponseUser.Photo> call, Response<ResponseUser.Photo> response) {
                if (response.isSuccessful()) {
                    ResponseUser.Photo responsePhoto = response.body();
                    if (responsePhoto.getUrlImage().contains("http")) {
                        view.updateNav();
                        view.setImage(responsePhoto.getUrlImage());
                        view.setLoadingIndicator(false);
                    } else {
                        view.setLoadingIndicator(false);
                        view.setMessageError(context.getString(R.string.no_server_connection_try_it_later));
                    }
                } else {
                    view.setLoadingIndicator(false);
                    view.setMessageError(context.getString(R.string.no_server_connection_try_it_later));
                }
            }

            @Override
            public void onFailure(Call<ResponseUser.Photo> call, Throwable t) {
                view.setLoadingIndicator(false);
                view.setMessageError(context.getString(R.string.no_server_connection_try_it_later));
            }
        });
    }

    @Override
    public void changePhoto(final String path) {
        view.setLoadingIndicator(true);
        UserRequest userRequest = serviceFactory.createService(UserRequest.class);
        Call<ResponseUser.PhotoChange> call = userRequest.changePhoto(ApiConstants.CONTENT_TYPE_JSON, "Bearer " + sessionManager.getUserToken(),
                new ResponseUser.PhotoChange(path), String.valueOf(sessionManager.getPersonEntity().getId()));

        call.enqueue(new Callback<ResponseUser.PhotoChange>() {
            @Override
            public void onResponse(Call<ResponseUser.PhotoChange> call, Response<ResponseUser.PhotoChange> response) {
                if (response.isSuccessful()) {
                    ResponseUser.PhotoChange responsePhoto = response.body();
                    if (responsePhoto.getUrlImage().contains("http")) {
                        view.updateNav();
                        view.setImage(responsePhoto.getUrlImage());
                        view.setLoadingIndicator(false);
                    } else {
                        view.setLoadingIndicator(false);
                        view.setMessageError(context.getString(R.string.no_server_connection_try_it_later));
                    }
                } else {
                    view.setLoadingIndicator(false);
                    view.setMessageError(context.getString(R.string.no_server_connection_try_it_later));
                }
            }

            @Override
            public void onFailure(Call<ResponseUser.PhotoChange> call, Throwable t) {
                view.setLoadingIndicator(false);
                view.setMessageError(context.getString(R.string.no_server_connection_try_it_later));
            }
        });

    }
}
