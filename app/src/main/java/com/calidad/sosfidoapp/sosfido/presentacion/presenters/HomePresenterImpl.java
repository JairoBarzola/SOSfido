package com.calidad.sosfidoapp.sosfido.presentacion.presenters;

import android.content.Context;

import com.calidad.sosfidoapp.sosfido.data.entities.ResponseReport;
import com.calidad.sosfidoapp.sosfido.data.repositories.Local.SessionManager;
import com.calidad.sosfidoapp.sosfido.data.repositories.Remote.Request.HomeRequest;
import com.calidad.sosfidoapp.sosfido.data.repositories.Remote.ServiceFactory;
import com.calidad.sosfidoapp.sosfido.presentacion.contracts.HomeContract;
import com.calidad.sosfidoapp.sosfido.presentacion.fragments.HomeFragment;
import com.calidad.sosfidoapp.sosfido.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jair Barzola on 22-Oct-17.
 */

public class HomePresenterImpl implements HomeContract.Presenter {
    private Context context;
    private HomeFragment view;
    private SessionManager sessionManager;
    private ServiceFactory serviceFactory;

    public HomePresenterImpl(HomeFragment view, Context context) {
        this.context = context;
        this.view = view;
        sessionManager = new SessionManager(context);
        serviceFactory = new ServiceFactory();
    }

    @Override
    public void loadReports() {


        HomeRequest homeRequest = serviceFactory.createService(HomeRequest.class);
        Call<List<ResponseReport.ReportList>> call = homeRequest.getReportsAbandoned("Bearer " + sessionManager.getUserToken(), true, true);
        call.enqueue(new Callback<List<ResponseReport.ReportList>>() {
            @Override
            public void onResponse(Call<List<ResponseReport.ReportList>> call, Response<List<ResponseReport.ReportList>> response) {
                if (response.isSuccessful()) {
                    List<ResponseReport.ReportList> reportList = response.body();
                    if (reportList != null) {
                        loadReportsMissing(reportList);
                    } else {
                        view.setMessageError(context.getString(R.string.no_server_connection_try_it_later));
                    }
                } else {
                    view.setMessageError(context.getString(R.string.no_server_connection_try_it_later));
                }
            }

            @Override
            public void onFailure(Call<List<ResponseReport.ReportList>> call, Throwable t) {
                view.setMessageError(context.getString(R.string.no_server_connection_try_it_later));
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
                    if (reportListMissing != null) {
                        loadReportsAdoption(reportListsAbandoned, reportListMissing);
                    } else {
                        view.setMessageError(context.getString(R.string.no_server_connection_try_it_later));
                    }
                } else {
                    view.setMessageError(context.getString(R.string.no_server_connection_try_it_later));
                }
            }

            @Override
            public void onFailure(Call<List<ResponseReport.ReportListMissing>> call, Throwable t) {
                view.setMessageError(context.getString(R.string.no_server_connection_try_it_later));
            }
        });
    }

    private void loadReportsAdoption(final List<ResponseReport.ReportList> reportListsAbandoned, final List<ResponseReport.ReportListMissing> reportListMissing) {
        HomeRequest homeRequest = serviceFactory.createService(HomeRequest.class);
        Call<List<ResponseReport.ReportListAdoption>> call = homeRequest.getReportsAdoption("Bearer " + sessionManager.getUserToken(), true);
        call.enqueue(new Callback<List<ResponseReport.ReportListAdoption>>() {
            @Override
            public void onResponse(Call<List<ResponseReport.ReportListAdoption>> call, Response<List<ResponseReport.ReportListAdoption>> response) {
                if (response.isSuccessful()) {
                    List<ResponseReport.ReportListAdoption> reportListAdoption = response.body();
                    if (reportListAdoption != null) {
                        view.getReportsPoints(reportListsAbandoned, reportListMissing, reportListAdoption);
                    } else {
                        view.setMessageError(context.getString(R.string.no_server_connection_try_it_later));
                    }

                } else {
                    view.setMessageError(context.getString(R.string.no_server_connection_try_it_later));
                }
            }

            @Override
            public void onFailure(Call<List<ResponseReport.ReportListAdoption>> call, Throwable t) {
                view.setMessageError(context.getString(R.string.no_server_connection_try_it_later));
            }
        });
    }
}