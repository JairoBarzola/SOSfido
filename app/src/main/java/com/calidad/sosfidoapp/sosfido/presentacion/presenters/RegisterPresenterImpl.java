package com.calidad.sosfidoapp.sosfido.presentacion.presenters;

import android.content.Context;

import com.calidad.sosfidoapp.sosfido.data.entities.ResponseReport;
import com.calidad.sosfidoapp.sosfido.data.entities.ResponseStatus;
import com.calidad.sosfidoapp.sosfido.data.repositories.local.SessionManager;
import com.calidad.sosfidoapp.sosfido.data.repositories.remote.ApiConstants;
import com.calidad.sosfidoapp.sosfido.data.repositories.remote.request.ReportRequest;
import com.calidad.sosfidoapp.sosfido.data.repositories.remote.ServiceFactory;
import com.calidad.sosfidoapp.sosfido.presentacion.contracts.RegisterContract;
import com.calidad.sosfidoapp.sosfido.presentacion.fragments.RegisterFragment;
import com.calidad.sosfidoapp.sosfido.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jair Barzola on 22-Oct-17.
 */

public class RegisterPresenterImpl implements RegisterContract.Presenter {

    private RegisterFragment view;
    private SessionManager sessionManager;
    private Context context;
    private ServiceFactory serviceFactory;

    public RegisterPresenterImpl(RegisterFragment view, Context context) {
        this.view = view;
        this.context = context;
        sessionManager = new SessionManager(context);
        serviceFactory = new ServiceFactory();
    }

    @Override
    public void start(String location, String latitud, String longitude, String description, final String image, String name, String phone, int tag) {
        view.setLoadingIndicator(true);

        if (tag == 1) {
            sendReportMissing(new ResponseReport.SendMissing(String.valueOf(sessionManager.getPersonEntity().getId()), name, new ResponseReport.Place(location, latitud, longitude), description), image);
        } else if (tag == 2) {
            sendReportAbandoned(new ResponseReport.Send(String.valueOf(sessionManager.getPersonEntity().getId()), new ResponseReport.Place(location, latitud, longitude), description), image);
        } else {
            sendAdoption(new ResponseReport.SendAdoption(String.valueOf(sessionManager.getPersonEntity().getId()), name, description), image);
        }


    }

    private void sendAdoption(ResponseReport.SendAdoption sendAdoption, final String image) {
        final ReportRequest reportRequest = serviceFactory.createService(ReportRequest.class);
        Call<ResponseReport> call = reportRequest.sendReportAdoption(ApiConstants.CONTENT_TYPE_JSON, "Bearer " + String.valueOf(sessionManager.getUserToken())
                , sendAdoption);
        call.enqueue(new Callback<ResponseReport>() {
            @Override
            public void onResponse(Call<ResponseReport> call, Response<ResponseReport> response) {
                if (response.isSuccessful()) {
                    ResponseReport responseReport = response.body();
                    if (responseReport.getId() != null) {
                        uploadPhotoAdoption("data:image/jpeg;base64," + image, responseReport.getId());
                    } else {
                        view.setMessageError(context.getString(R.string.no_server_connection_try_it_later));
                        view.setLoadingIndicator(false);
                    }
                } else {
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

    private void sendReportMissing(ResponseReport.SendMissing sendMissing, final String image) {
        final ReportRequest reportRequest = serviceFactory.createService(ReportRequest.class);
        Call<ResponseReport> call = reportRequest.sendReportMissing(ApiConstants.CONTENT_TYPE_JSON, "Bearer " + String.valueOf(sessionManager.getUserToken())
                , sendMissing);
        call.enqueue(new Callback<ResponseReport>() {
            @Override
            public void onResponse(Call<ResponseReport> call, Response<ResponseReport> response) {
                if (response.isSuccessful()) {
                    ResponseReport responseReport = response.body();
                    if (responseReport.getId() != null) {
                        uploadPhoto("data:image/jpeg;base64," + image, responseReport.getId());
                    } else {
                        view.setMessageError(context.getString(R.string.no_server_connection_try_it_later));
                        view.setLoadingIndicator(false);
                    }
                } else {
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

    private void sendReportAbandoned(final ResponseReport.Send send, final String image) {
        final ReportRequest reportRequest = serviceFactory.createService(ReportRequest.class);
        Call<ResponseReport> call = reportRequest.sendReport(ApiConstants.CONTENT_TYPE_JSON, "Bearer " + String.valueOf(sessionManager.getUserToken())
                , send);
        call.enqueue(new Callback<ResponseReport>() {
            @Override
            public void onResponse(Call<ResponseReport> call, Response<ResponseReport> response) {
                if (response.isSuccessful()) {
                    ResponseReport responseReport = response.body();
                    if (responseReport.getId() != null) {
                        uploadPhoto("data:image/jpeg;base64," + image, responseReport.getId());
                    } else {
                        view.setMessageError(context.getString(R.string.no_server_connection_try_it_later));
                        view.setLoadingIndicator(false);
                    }
                } else {
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
        ReportRequest reportRequest = serviceFactory.createService(ReportRequest.class);
        Call<ResponseStatus> call = reportRequest.sendPhoto(ApiConstants.CONTENT_TYPE_JSON,
                "Bearer " + String.valueOf(sessionManager.getUserToken()), new ResponseReport.SendPhoto(id_report, image));
        call.enqueue(new Callback<ResponseStatus>() {
            @Override
            public void onResponse(Call<ResponseStatus> call, Response<ResponseStatus> response) {
                if (response.isSuccessful()) {
                    ResponseStatus responseStatus = response.body();
                    if (responseStatus.getUrlImage().contains("http")) {
                        view.backToHome();
                        view.setLoadingIndicator(false);
                    } else {
                        view.setMessageError(context.getString(R.string.no_server_connection_try_it_later));
                        view.setLoadingIndicator(false);
                    }
                } else {
                    view.setMessageError(context.getString(R.string.no_server_connection_try_it_later));
                    view.setLoadingIndicator(false);
                }
            }

            @Override
            public void onFailure(Call<ResponseStatus> call, Throwable t) {
                view.setMessageError(context.getString(R.string.no_server_connection_try_it_later));
                view.setLoadingIndicator(false);
            }
        });
    }

    //enviar foto de animal en adopcion
    private void uploadPhotoAdoption(String image, String adoption_proposal) {
        ReportRequest reportRequest = serviceFactory.createService(ReportRequest.class);
        Call<ResponseStatus> call = reportRequest.sendPhotoAdoption(ApiConstants.CONTENT_TYPE_JSON, "Bearer " + String.valueOf(sessionManager.getUserToken()), new ResponseReport.SendPhotoAdoption(adoption_proposal, image));
        call.enqueue(new Callback<ResponseStatus>() {
            @Override
            public void onResponse(Call<ResponseStatus> call, Response<ResponseStatus> response) {
                if (response.isSuccessful()) {
                    ResponseStatus responseStatus = response.body();
                    if (responseStatus.getUrlImage().contains("http")) {
                        view.backToHome();
                        view.setLoadingIndicator(false);
                    } else {
                        view.setMessageError(context.getString(R.string.no_server_connection_try_it_later));
                        view.setLoadingIndicator(false);
                    }
                } else {
                    view.setMessageError(context.getString(R.string.no_server_connection_try_it_later));
                    view.setLoadingIndicator(false);
                }
            }

            @Override
            public void onFailure(Call<ResponseStatus> call, Throwable t) {
                view.setMessageError(context.getString(R.string.no_server_connection_try_it_later));
                view.setLoadingIndicator(false);
            }
        });
    }


}
