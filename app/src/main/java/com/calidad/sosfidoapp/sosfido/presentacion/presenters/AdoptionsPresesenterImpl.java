package com.calidad.sosfidoapp.sosfido.presentacion.presenters;

import android.content.Context;

import com.calidad.sosfidoapp.sosfido.R;
import com.calidad.sosfidoapp.sosfido.data.entities.ResponseReport;
import com.calidad.sosfidoapp.sosfido.data.repositories.local.SessionManager;
import com.calidad.sosfidoapp.sosfido.data.repositories.remote.ServiceFactory;
import com.calidad.sosfidoapp.sosfido.data.repositories.remote.request.HomeRequest;
import com.calidad.sosfidoapp.sosfido.presentacion.contracts.AdoptionsContract;
import com.calidad.sosfidoapp.sosfido.presentacion.fragments.AdoptionsFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jairbarzola on 12/11/17.
 */

public class AdoptionsPresesenterImpl implements AdoptionsContract.Presenter{
    public Context context;
    public AdoptionsFragment view;
    public SessionManager sessionManager;
    public ServiceFactory serviceFactory;
    public  AdoptionsPresesenterImpl(Context context,AdoptionsFragment view){
        this.context= context;
        this.view=view;
        sessionManager = new SessionManager(context);
        serviceFactory = new ServiceFactory();
    }

    @Override
    public void start() {
        view.showSwipeLayout();
        HomeRequest homeRequest = serviceFactory.createService(HomeRequest.class);
        Call<List<ResponseReport.ReportListAdoption>> call = homeRequest.getReportsAdoption("Bearer " + sessionManager.getUserToken(), true);
        call.enqueue(new Callback<List<ResponseReport.ReportListAdoption>>() {
            @Override
            public void onResponse(Call<List<ResponseReport.ReportListAdoption>> call, Response<List<ResponseReport.ReportListAdoption>> response) {
                if (response.isSuccessful()) {
                    List<ResponseReport.ReportListAdoption> reportListAdoption = response.body();
                    if (reportListAdoption.size()!=0) {
                        view.initRecyclerView(reportListAdoption);
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
            public void onFailure(Call<List<ResponseReport.ReportListAdoption>> call, Throwable t) {
                view.showEmpty();
                view.hideSWipeLayout();
            }
        });

    }

    @Override
    public void registerReport() {

    }
}
