package com.calidad.sosfidoapp.sosfido.Presentacion.Presenters;

import android.content.Context;

import com.calidad.sosfidoapp.sosfido.Data.Entities.ResponseReport;
import com.calidad.sosfidoapp.sosfido.Data.Repositories.Local.SessionManager;
import com.calidad.sosfidoapp.sosfido.Data.Repositories.Remote.Request.HomeRequest;
import com.calidad.sosfidoapp.sosfido.Data.Repositories.Remote.ServiceFactory;
import com.calidad.sosfidoapp.sosfido.Presentacion.Contracts.ReportContract;
import com.calidad.sosfidoapp.sosfido.Presentacion.Fragments.PublicationsFragment;
import com.calidad.sosfidoapp.sosfido.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jairbarzola on 31/10/17.
 */

public class PublicationsPresenterImpl  implements ReportContract.Presenter{

    PublicationsFragment view;
    Context context;
    SessionManager sessionManager;

    public PublicationsPresenterImpl(Context context,PublicationsFragment view){
        this.context=context;
        this.view=view;
        sessionManager= new SessionManager(context);
    }

    @Override
    public void start() {
        view.showSwipeLayout();
        HomeRequest homeRequest = ServiceFactory.createService(HomeRequest.class);
        Call<List<ResponseReport.ReportList>> call = homeRequest.getReportsAbandoned("Bearer "+sessionManager.getUserToken(),true,true);
        call.enqueue(new Callback<List<ResponseReport.ReportList>>() {
            @Override
            public void onResponse(Call<List<ResponseReport.ReportList>> call, Response<List<ResponseReport.ReportList>> response) {
                if(response.isSuccessful()){
                    List<ResponseReport.ReportList> reportList = response.body();
                    if(reportList!=null){
                        loadReportsMissing(reportList);
                    }else{
                        view.setMessageError(context.getString(R.string.no_server_connection_try_it_later));
                        view.hideSWipeLayout();
                    }

                }else{
                    view.setMessageError(context.getString(R.string.no_server_connection_try_it_later));
                    view.hideSWipeLayout();
                }
            }

            @Override
            public void onFailure(Call<List<ResponseReport.ReportList>> call, Throwable t) {
                view.setMessageError(context.getString(R.string.no_server_connection_try_it_later));
                view.hideSWipeLayout();
            }
        });
    }
    public void loadReportsMissing(final List<ResponseReport.ReportList> reportListsAbandoned) {

        HomeRequest homeRequest = ServiceFactory.createService(HomeRequest.class);
        Call<List<ResponseReport.ReportListMissing>> call = homeRequest.getReportsMissing("Bearer "+sessionManager.getUserToken(),true,true);
        call.enqueue(new Callback<List<ResponseReport.ReportListMissing>>() {
            @Override
            public void onResponse(Call<List<ResponseReport.ReportListMissing>> call, Response<List<ResponseReport.ReportListMissing>> response) {
                if(response.isSuccessful()){
                    List<ResponseReport.ReportListMissing> reportListMissing = response.body();
                    if(reportListMissing!=null){
                        loadReportsAdoption(reportListsAbandoned,reportListMissing);
                    }else{
                        view.setMessageError(context.getString(R.string.no_server_connection_try_it_later));
                        view.hideSWipeLayout();
                    }

                }else{
                    view.setMessageError(context.getString(R.string.no_server_connection_try_it_later));
                    view.hideSWipeLayout();
                }
            }
            @Override
            public void onFailure(Call<List<ResponseReport.ReportListMissing>> call, Throwable t) {
                view.setMessageError(context.getString(R.string.no_server_connection_try_it_later));
                view.hideSWipeLayout();
            }
        });
    }

    private void loadReportsAdoption(final List<ResponseReport.ReportList> reportListsAbandoned, final List<ResponseReport.ReportListMissing> reportListMissing) {
        HomeRequest homeRequest = ServiceFactory.createService(HomeRequest.class);
        Call<List<ResponseReport.ReportListAdoption>> call = homeRequest.getReportsAdoption("Bearer "+sessionManager.getUserToken(),true);
        call.enqueue(new Callback<List<ResponseReport.ReportListAdoption>>() {
            @Override
            public void onResponse(Call<List<ResponseReport.ReportListAdoption>> call, Response<List<ResponseReport.ReportListAdoption>> response) {
                if(response.isSuccessful()){
                    List<ResponseReport.ReportListAdoption> reportListAdoption = response.body();
                    if(reportListAdoption!=null){
                        view.setInitRecycler(reportListsAbandoned,reportListMissing,reportListAdoption);
                        view.hideSWipeLayout();
                    }else{
                        view.setMessageError(context.getString(R.string.no_server_connection_try_it_later));
                        view.hideSWipeLayout();
                    }

                }else{
                    view.setMessageError(context.getString(R.string.no_server_connection_try_it_later));
                    view.hideSWipeLayout();
                }
            }
            @Override
            public void onFailure(Call<List<ResponseReport.ReportListAdoption>> call, Throwable t) {
                view.setMessageError(context.getString(R.string.no_server_connection_try_it_later));
                view.hideSWipeLayout();
            }
        });
    }
}