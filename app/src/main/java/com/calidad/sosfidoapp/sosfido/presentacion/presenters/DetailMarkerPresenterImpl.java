package com.calidad.sosfidoapp.sosfido.presentacion.presenters;

import android.content.Context;

import com.calidad.sosfidoapp.sosfido.R;
import com.calidad.sosfidoapp.sosfido.data.entities.ProposalAdoption;
import com.calidad.sosfidoapp.sosfido.data.repositories.local.SessionManager;
import com.calidad.sosfidoapp.sosfido.data.repositories.remote.ApiConstants;
import com.calidad.sosfidoapp.sosfido.data.repositories.remote.ServiceFactory;
import com.calidad.sosfidoapp.sosfido.data.repositories.remote.request.ReportRequest;
import com.calidad.sosfidoapp.sosfido.presentacion.activies.DetailMarkerActivity;
import com.calidad.sosfidoapp.sosfido.presentacion.contracts.DetailMarkerContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jairbarzola on 12/11/17.
 */

public class DetailMarkerPresenterImpl implements DetailMarkerContract.Presenter {

    public Context context;
    public DetailMarkerActivity view;
    public SessionManager sessionManager;
    public ServiceFactory serviceFactory;
    public DetailMarkerPresenterImpl(Context context,DetailMarkerActivity view){
        this.context=context;
        this.view=view;
        sessionManager= new SessionManager(context);
        serviceFactory = new ServiceFactory();
    }

    @Override
    public void sendProposalAdoption(ProposalAdoption proposalAdoption) {
        view.setLoadingIndicator(true);
        ReportRequest reportRequest =serviceFactory.createService(ReportRequest.class);
        Call<ProposalAdoption.Response> call = reportRequest.sendProposal(ApiConstants.CONTENT_TYPE_JSON, "Bearer " + sessionManager.getUserToken(),proposalAdoption);
        call.enqueue(new Callback<ProposalAdoption.Response>() {
            @Override
            public void onResponse(Call<ProposalAdoption.Response> call, Response<ProposalAdoption.Response> response) {
                if(response.isSuccessful()){
                    view.setMessage("Enviado");
                    view.setLoadingIndicator(false);
                }else{
                    view.setMessageError("Hubo un error, intente mas tarde");
                    view.setLoadingIndicator(false);
                }
            }
            @Override
            public void onFailure(Call<ProposalAdoption.Response> call, Throwable t) {
                view.setMessageError(context.getString(R.string.no_server_connection_try_it_later));
                view.setLoadingIndicator(false);
            }
        });
    }
}
