package com.calidad.sosfidoapp.sosfido.data.repositories.remote.request;

import com.calidad.sosfidoapp.sosfido.data.entities.ResponseReport;
import com.calidad.sosfidoapp.sosfido.data.entities.ResponseStatus;
import com.calidad.sosfidoapp.sosfido.data.repositories.remote.ApiConstants;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by Jair Barzola on 22-Oct-17.
 */

public interface ReportRequest {

    // enviar reporte de animal abandonado
    @POST(ApiConstants.SEND_REPORT)
    Call<ResponseReport> sendReport(@Header("Content-type") String contentType, @Header("Authorization") String token, @Body ResponseReport.Send responseReport);

    // enviar reporte de animal perdido
    @POST(ApiConstants.SEND_REPORT)
    Call<ResponseReport> sendReportMissing(@Header("Content-type") String contentType, @Header("Authorization") String token, @Body ResponseReport.SendMissing responseReport);

    // enviar reporte de animal en adopcion
    @POST(ApiConstants.SEND_ADOPTION)
    Call<ResponseReport> sendReportAdoption(@Header("Content-type") String contentType, @Header("Authorization") String token, @Body ResponseReport.SendAdoption responseReport);

    //enviar foto de animal perdido, abandonado
    @POST(ApiConstants.SEND_PHOTO)
    Call<ResponseStatus> sendPhoto(@Header("Content-type") String contentType, @Header("Authorization") String token, @Body ResponseReport.SendPhoto responsePhoto);

    // enviar foto de animal adoptado
    @POST(ApiConstants.SEND_PHOTO_ADOPTION)
    Call<ResponseStatus> sendPhotoAdoption(@Header("Content-type") String contentType, @Header("Authorization") String token, @Body ResponseReport.SendPhotoAdoption responsePhoto);


}
