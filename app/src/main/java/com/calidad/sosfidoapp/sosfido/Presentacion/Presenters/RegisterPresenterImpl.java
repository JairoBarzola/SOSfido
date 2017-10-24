package com.calidad.sosfidoapp.sosfido.Presentacion.Presenters;

import android.content.Context;

import com.calidad.sosfidoapp.sosfido.Data.Entities.ReportResponse;
import com.calidad.sosfidoapp.sosfido.Data.Entities.ResponseReport;
import com.calidad.sosfidoapp.sosfido.Data.Entities.ResponseStatus;
import com.calidad.sosfidoapp.sosfido.Data.Repositories.Local.SessionManager;
import com.calidad.sosfidoapp.sosfido.Data.Repositories.Remote.ApiConstants;
import com.calidad.sosfidoapp.sosfido.Data.Repositories.Remote.Request.ReportRequest;
import com.calidad.sosfidoapp.sosfido.Data.Repositories.Remote.ServiceFactory;
import com.calidad.sosfidoapp.sosfido.Presentacion.Activies.RegisterActivity;
import com.calidad.sosfidoapp.sosfido.Presentacion.Contracts.RegisterContract;
import com.calidad.sosfidoapp.sosfido.Presentacion.Fragments.RegisterFragment;
import com.calidad.sosfidoapp.sosfido.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

/**
 * Created by Jair Barzola on 22-Oct-17.
 */

public class RegisterPresenterImpl implements RegisterContract.Presenter {

    RegisterFragment view;
    SessionManager sessionManager;
    Context context;

    public RegisterPresenterImpl(RegisterFragment view,Context context){
        this.view= view;
        this.context=context;
        sessionManager = new SessionManager(context);
    }
    @Override
    public void start(String location, String latitud, String longitude, String description, final String image, String name, String phone) {
        view.setLoadingIndicator(true);

        location="prueba-4";
        latitud="-12.052270";
        longitude="-77.085460";
        final ReportRequest reportRequest = ServiceFactory.createService(ReportRequest.class);
        Call<ResponseReport> call = reportRequest.sendReport(ApiConstants.CONTENT_TYPE_JSON,"Bearer "+String.valueOf(sessionManager.getUserToken())
                                                            , new ResponseReport.Send(String.valueOf(sessionManager.getPersonEntity().getId()),new ResponseReport.Place(location,latitud,longitude),description));
        call.enqueue(new Callback<ResponseReport>() {
            @Override
            public void onResponse(Call<ResponseReport> call, Response<ResponseReport> response) {
                if(response.isSuccessful()){
                    ResponseReport responseReport = response.body();
                    if(responseReport.getId()!=null) {
                        uploadPhoto("data:image/jpeg;base64,"+image,responseReport.getId());
                    }else{
                        view.setMessageError(context.getString(R.string.no_server_connection_try_it_later));
                        view.setLoadingIndicator(false);
                    }
                }else{
                    view.setMessageError(context.getString(R.string.no_server_connection_try_it_later));
                    view.setLoadingIndicator(false);
                }
            }

            @Override
            public void onFailure(Call<ResponseReport> call, Throwable t) {
                view.setMessageError(context.getString(R.string.no_server_connection_try_it_later));
                view.setLoadingIndicator(false);
            }
        });

    }

    private void uploadPhoto(String image, String id_report) {
        ReportRequest reportRequest = ServiceFactory.createService(ReportRequest.class);
        Call<ResponseStatus> call = reportRequest.sendPhoto(ApiConstants.CONTENT_TYPE_JSON,
                                                            "Bearer "+String.valueOf(sessionManager.getUserToken()),new ResponseReport.SendPhoto(id_report,image));
        call.enqueue(new Callback<ResponseStatus>() {
            @Override
            public void onResponse(Call<ResponseStatus> call, Response<ResponseStatus> response) {
                if(response.isSuccessful()){
                    ResponseStatus responseStatus= response.body();
                    if(responseStatus.getUrl_image().contains("http")){
                        view.backToHome();
                        view.setLoadingIndicator(false);
                    }else{
                        view.setMessageError(context.getString(R.string.no_server_connection_try_it_later));
                        view.setLoadingIndicator(false);
                    }
                }else{
                    view.setMessageError(context.getString(R.string.no_server_connection_try_it_later));
                    view.setLoadingIndicator(false);}
            }

            @Override
            public void onFailure(Call<ResponseStatus> call, Throwable t) {
                view.setMessageError(context.getString(R.string.no_server_connection_try_it_later));
                view.setLoadingIndicator(false);
            }
        });
    }

}
