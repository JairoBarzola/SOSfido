package com.calidad.sosfidoapp.sosfido.Data.Repositories.Remote.Request;

import com.calidad.sosfidoapp.sosfido.Data.Entities.ReportResponse;
import com.calidad.sosfidoapp.sosfido.Data.Entities.ResponseReport;
import com.calidad.sosfidoapp.sosfido.Data.Entities.ResponseStatus;
import com.calidad.sosfidoapp.sosfido.Data.Repositories.Remote.ApiConstants;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by Jair Barzola on 22-Oct-17.
 */

public interface ReportRequest {

    // enviar reporte de animal abandonado
    @POST(ApiConstants.SEND_REPORT)
    Call<ResponseReport> sendReport(@Header("Content-type") String contentType, @Header("Authorization") String token,
                                    @Body ResponseReport.Send responseReport);

    // enviar reporte de animal perdido
    @POST(ApiConstants.SEND_REPORT)
    Call<ResponseReport> sendReportMissing(@Header("Content-type") String contentType, @Header("Authorization") String token,
                                @Body ResponseReport.SendMissing responseReport);

    // enviar reporte de animal en adopcion
    @POST(ApiConstants.SEND_ADOPTION)
    Call<ResponseReport> sendReportAdoption(@Header("Content-type") String contentType, @Header("Authorization") String token,
                                           @Body ResponseReport.SendAdoption responseReport);

    //enviar foto de animal perdido, abandonado y adopcion
    @POST(ApiConstants.SEND_PHOTO)
    Call<ResponseStatus> sendPhoto(@Header("Content-type") String contentType, @Header("Authorization") String token,
                                   @Body ResponseReport.SendPhoto responsePhoto);



}
