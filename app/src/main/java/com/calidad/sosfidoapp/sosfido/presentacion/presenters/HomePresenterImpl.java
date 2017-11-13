package com.calidad.sosfidoapp.sosfido.presentacion.presenters;

import android.content.Context;

import com.calidad.sosfidoapp.sosfido.data.entities.ResponseReport;
import com.calidad.sosfidoapp.sosfido.data.repositories.local.SessionManager;
import com.calidad.sosfidoapp.sosfido.data.repositories.remote.request.HomeRequest;
import com.calidad.sosfidoapp.sosfido.data.repositories.remote.ServiceFactory;
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
                        view.setMessageError(context.getString(R.string.there_was_an_error_try_it_later));
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
                        view.getReportsPoints(reportListsAbandoned, reportListMissing);
                    } else {
                        view.setMessageError(context.getString(R.string.there_was_an_error_try_it_later));
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
}
