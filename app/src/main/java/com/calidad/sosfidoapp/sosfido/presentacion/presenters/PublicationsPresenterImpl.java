package com.calidad.sosfidoapp.sosfido.presentacion.presenters;

import android.content.Context;

import com.calidad.sosfidoapp.sosfido.data.entities.ResponseReport;
import com.calidad.sosfidoapp.sosfido.data.repositories.local.SessionManager;
import com.calidad.sosfidoapp.sosfido.data.repositories.remote.request.HomeRequest;
import com.calidad.sosfidoapp.sosfido.data.repositories.remote.ServiceFactory;
import com.calidad.sosfidoapp.sosfido.presentacion.contracts.ReportContract;
import com.calidad.sosfidoapp.sosfido.presentacion.fragments.PublicationsFragment;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jairbarzola on 31/10/17.
 */

public class PublicationsPresenterImpl implements ReportContract.Presenter {

    private PublicationsFragment view;
    private Context context;
    private SessionManager sessionManager;
    private ServiceFactory serviceFactory;

    public PublicationsPresenterImpl(Context context, PublicationsFragment view) {
        this.context = context;
        this.view = view;
        sessionManager = new SessionManager(context);
        serviceFactory = new ServiceFactory();
    }

    @Override
    public void start() {
        view.showSwipeLayout();
        HomeRequest homeRequest = serviceFactory.createService(HomeRequest.class);
        Call<List<ResponseReport.ReportList>> call = homeRequest.getReportsAbandoned("Bearer " + sessionManager.getUserToken(), true, true);
        call.enqueue(new Callback<List<ResponseReport.ReportList>>() {
            @Override
            public void onResponse(Call<List<ResponseReport.ReportList>> call, Response<List<ResponseReport.ReportList>> response) {
                if (response.isSuccessful()) {
                    List<ResponseReport.ReportList> reportList = response.body();
                    loadReportsMissing(reportList);
                } else {
                    view.showEmpty();
                    view.hideSWipeLayout();
                }
            }

            @Override
            public void onFailure(Call<List<ResponseReport.ReportList>> call, Throwable t) {
                view.showEmpty();
                view.hideSWipeLayout();
            }
        });
    }

    public void loadReportsMissing(final List<ResponseReport.ReportList> reportListsAbandoned) {

        HomeRequest homeRequest = serviceFactory.createService(HomeRequest.class);
        Call<List<ResponseReport.ReportListMissing>> call = homeRequest.getReportsMissing("Bearer " + sessionManager.getUserToken(), true, true);
        call.enqueue(new Callback<List<ResponseReport.ReportListMissing>>() {
            @Override
            public void onResponse(Call<List<ResponseReport.ReportListMissing>> call, Response<List<ResponseReport.ReportListMissing>> response) {
                if (response.isSuccessful()) {
                    List<ResponseReport.ReportListMissing> reportListMissing = response.body();
                    if (reportListMissing!=null) {
                            view.setInitRecycler(reportListsAbandoned, reportListMissing);
                            view.hideSWipeLayout();
                    } else {
                        view.hideSWipeLayout();
                        view.showEmpty();
                    }

                } else {
                    view.showEmpty();
                    view.hideSWipeLayout();
                }
            }

            @Override
            public void onFailure(Call<List<ResponseReport.ReportListMissing>> call, Throwable t) {
                view.showEmpty();
                view.hideSWipeLayout();
            }
        });
    }
}
