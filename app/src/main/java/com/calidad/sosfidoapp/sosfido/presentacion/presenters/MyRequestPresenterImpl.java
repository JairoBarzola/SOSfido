package com.calidad.sosfidoapp.sosfido.presentacion.presenters;

import android.content.Context;

import com.calidad.sosfidoapp.sosfido.data.entities.MyRequestEntity;

import com.calidad.sosfidoapp.sosfido.data.repositories.local.SessionManager;
import com.calidad.sosfidoapp.sosfido.data.repositories.remote.ApiConstants;
import com.calidad.sosfidoapp.sosfido.data.repositories.remote.ServiceFactory;
import com.calidad.sosfidoapp.sosfido.data.repositories.remote.request.UserRequest;
import com.calidad.sosfidoapp.sosfido.presentacion.contracts.MyRequestContract;
import com.calidad.sosfidoapp.sosfido.presentacion.fragments.MyRequestsFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jairbarzola on 18/11/17.
 */

public class MyRequestPresenterImpl implements MyRequestContract.Presenter{

    public Context context;
    public SessionManager sessionManager;
    public ServiceFactory serviceFactory;
    public MyRequestsFragment view;

    public MyRequestPresenterImpl(Context context,MyRequestsFragment view){
        this.context=context;
        this.view=view;
        serviceFactory = new ServiceFactory();
        sessionManager = new SessionManager(context);
    }

    @Override
    public void start() {
        view.showSwipeLayout();
        UserRequest userRequest = serviceFactory.createService(UserRequest.class);
        Call<List<MyRequestEntity>> call = userRequest.getRequestByPerson("Bearer " + String.valueOf(sessionManager.getUserToken()),String.valueOf(sessionManager.getPersonEntity().getId()));
        call.enqueue(new Callback<List<MyRequestEntity>>() {
            @Override
            public void onResponse(Call<List<MyRequestEntity>> call, Response<List<MyRequestEntity>> response) {
                if(response.isSuccessful()){
                    List<MyRequestEntity> requestsEntities = response.body();
                    if (requestsEntities.size()!=0) {
                        view.initRecyclerView(requestsEntities);
                        view.hideSWipeLayout();
                        view.hideEmpty();
                    } else {
                        view.showEmpty();
                        view.hideSWipeLayout();
                    }
                }else{
                    view.showEmpty();
                    view.hideSWipeLayout();
                }
            }

            @Override
            public void onFailure(Call<List<MyRequestEntity>> call, Throwable t) {
                view.showEmpty();
                view.hideSWipeLayout();
            }
        });
    }
}
