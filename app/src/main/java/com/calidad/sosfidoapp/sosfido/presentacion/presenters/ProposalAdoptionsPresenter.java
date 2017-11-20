package com.calidad.sosfidoapp.sosfido.presentacion.presenters;

import android.content.Context;


import com.calidad.sosfidoapp.sosfido.data.entities.MyProposalAdoptionsEntity;
import com.calidad.sosfidoapp.sosfido.data.repositories.local.SessionManager;
import com.calidad.sosfidoapp.sosfido.data.repositories.remote.ServiceFactory;
import com.calidad.sosfidoapp.sosfido.data.repositories.remote.request.ReportRequest;
import com.calidad.sosfidoapp.sosfido.presentacion.contracts.ProposalAdoptionsContract;
import com.calidad.sosfidoapp.sosfido.presentacion.fragments.ProposalAdoptionsFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jairbarzola on 12/11/17.
 */

public class ProposalAdoptionsPresenter implements ProposalAdoptionsContract.Presenter {

    public Context context;
    public SessionManager sessionManager;
    public ProposalAdoptionsFragment view;
    public ServiceFactory serviceFactory;

    public ProposalAdoptionsPresenter(Context context, ProposalAdoptionsFragment view){
        this.context=context;
        this.view=view;
        serviceFactory= new ServiceFactory();
        sessionManager = new SessionManager(context);
    }

    @Override
    public void start() {
        view.showSwipeLayout();
        ReportRequest reportRequest = serviceFactory.createService(ReportRequest.class);
        Call<List<MyProposalAdoptionsEntity>> call = reportRequest.getMyProposal("Bearer " + String.valueOf(sessionManager.getUserToken()),String.valueOf(sessionManager.getPersonEntity().getId()));
        call.enqueue(new Callback<List<MyProposalAdoptionsEntity>>() {
            @Override
            public void onResponse(Call<List<MyProposalAdoptionsEntity>> call, Response<List<MyProposalAdoptionsEntity>> response) {
                if (response.isSuccessful()) {
                    List<MyProposalAdoptionsEntity> reportListAdoption = response.body();
                    if (reportListAdoption.size()!=0) {
                        view.initRecyclerView(reportListAdoption);
                        view.hideSWipeLayout();
                        view.hideEmpty();
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
            public void onFailure(Call<List<MyProposalAdoptionsEntity>> call, Throwable t) {
                view.showEmpty();
                view.hideSWipeLayout();
            }
        });
    }
}
