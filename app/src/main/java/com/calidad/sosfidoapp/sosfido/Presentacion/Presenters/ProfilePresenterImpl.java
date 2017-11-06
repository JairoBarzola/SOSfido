package com.calidad.sosfidoapp.sosfido.Presentacion.Presenters;

import android.content.Context;

import com.calidad.sosfidoapp.sosfido.Data.Entities.PersonEntity;
import com.calidad.sosfidoapp.sosfido.Data.Entities.ResponseUser;
import com.calidad.sosfidoapp.sosfido.Data.Repositories.Local.SessionManager;
import com.calidad.sosfidoapp.sosfido.Data.Repositories.Remote.ApiConstants;
import com.calidad.sosfidoapp.sosfido.Data.Repositories.Remote.Request.UserRequest;
import com.calidad.sosfidoapp.sosfido.Data.Repositories.Remote.ServiceFactory;
import com.calidad.sosfidoapp.sosfido.Presentacion.Contracts.ProfileContract;
import com.calidad.sosfidoapp.sosfido.Presentacion.Fragments.ProfileFragment;
import com.calidad.sosfidoapp.sosfido.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jairbarzola on 2/10/17.
 */

public class ProfilePresenterImpl implements ProfileContract.Presenter{

    private ProfileFragment view;
    private SessionManager sessionManager;
    private Context context;
    public ProfilePresenterImpl(ProfileFragment view, Context context){
        this.view=view;
        this.context=context;
        sessionManager = new SessionManager(context);
    }
    @Override
    public void start() {
        PersonEntity personEntity = sessionManager.getPersonEntity();
        if(personEntity.getPersonImage().contains("http")){
            view.loadUser(personEntity,true);
        }else{
            view.loadUser(personEntity,false);
        }
    }

    @Override
    public void uploadPhoto(final String path) {
        view.setLoadingIndicator(true);
        UserRequest userRequest = ServiceFactory.createService(UserRequest.class);
        Call<ResponseUser.Photo> call = userRequest.uploadPhoto(ApiConstants.CONTENT_TYPE_JSON,"Bearer "+sessionManager.getUserToken(),
                new ResponseUser.PhotoBody(String.valueOf(sessionManager.getPersonEntity().getId()),path));
        call.enqueue(new Callback<ResponseUser.Photo>() {
            @Override
            public void onResponse(Call<ResponseUser.Photo> call, Response<ResponseUser.Photo> response) {
                if(response.isSuccessful()){
                    ResponseUser.Photo responsePhoto = response.body();
                    if(responsePhoto.getUrl_image().contains("http")){
                        view.updateNav();
                        view.setImage(responsePhoto.getUrl_image());
                        view.setLoadingIndicator(false);
                    }else{
                        view.setLoadingIndicator(false);
                        view.setMessageError(context.getString(R.string.no_server_connection_try_it_later));
                    }
                }else{
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
        UserRequest userRequest = ServiceFactory.createService(UserRequest.class);
        Call<ResponseUser.PhotoChange> call = userRequest.changePhoto(ApiConstants.CONTENT_TYPE_JSON,"Bearer "+sessionManager.getUserToken(),
                      new ResponseUser.PhotoChange(path),String.valueOf(sessionManager.getPersonEntity().getId()));

        call.enqueue(new Callback<ResponseUser.PhotoChange>() {
            @Override
            public void onResponse(Call<ResponseUser.PhotoChange> call, Response<ResponseUser.PhotoChange> response) {
                if(response.isSuccessful()){
                    ResponseUser.PhotoChange responsePhoto = response.body();
                    if(responsePhoto.getUrlImage().contains("http")){
                        view.updateNav();
                        view.setImage(responsePhoto.getUrlImage());
                        view.setLoadingIndicator(false);
                    }else{
                        view.setLoadingIndicator(false);
                        view.setMessageError(context.getString(R.string.no_server_connection_try_it_later));
                    }
                }else{
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
