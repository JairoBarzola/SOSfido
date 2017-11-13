package com.calidad.sosfidoapp.sosfido.presentacion.presenters;

import android.content.Context;

import com.calidad.sosfidoapp.sosfido.data.entities.RequestsEntity;
import com.calidad.sosfidoapp.sosfido.data.repositories.local.SessionManager;
import com.calidad.sosfidoapp.sosfido.data.repositories.remote.ServiceFactory;
import com.calidad.sosfidoapp.sosfido.data.repositories.remote.request.UserRequest;
import com.calidad.sosfidoapp.sosfido.presentacion.activies.RequestsActivity;
import com.calidad.sosfidoapp.sosfido.presentacion.contracts.RequestContract;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jairbarzola on 13/11/17.
 */

public class RequestPresenterImpl implements RequestContract.Presenter{
    public Context context;
    public SessionManager sessionManager;
    public ServiceFactory serviceFactory;
    public RequestsActivity view;

    public RequestPresenterImpl(Context context,RequestsActivity view){
        this.context=context;
        this.view=view;
        serviceFactory = new ServiceFactory();
        sessionManager = new SessionManager(context);
    }

   @Override
    public void start(String id) {
        view.showSwipeLayout();
        UserRequest userRequest =serviceFactory.createService(UserRequest.class);
       Call<List<RequestsEntity>> call = userRequest.getRequest("Bearer " + String.valueOf(sessionManager.getUserToken()),id);
       call.enqueue(new Callback<List<RequestsEntity>>() {
           @Override
           public void onResponse(Call<List<RequestsEntity>> call, Response<List<RequestsEntity>> response) {
               if (response.isSuccessful()) {
                   List<RequestsEntity> requestsEntityList = response.body();
                   if (requestsEntityList.size()!=0) {
                       view.initRecyclerView(requestsEntityList);
                       view.hideSWipeLayout();
                   } else {
                       view.showEmpty();
                       view.hideSWipeLayout();
                   }

               } else {
                   view.showEmpty();
                   view.hideSWipeLayout();
               }
           }

           @Override
           public void onFailure(Call<List<RequestsEntity>> call, Throwable t) {
               view.showEmpty();
               view.hideSWipeLayout();
           }
       });
    }
}
