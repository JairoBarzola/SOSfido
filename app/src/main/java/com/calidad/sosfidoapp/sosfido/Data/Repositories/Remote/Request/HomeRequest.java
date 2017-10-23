package com.calidad.sosfidoapp.sosfido.Data.Repositories.Remote.Request;

import com.calidad.sosfidoapp.sosfido.Data.Entities.ReportResponse;
import com.calidad.sosfidoapp.sosfido.Data.Entities.ResponseReports;
import com.calidad.sosfidoapp.sosfido.Data.Repositories.Remote.ApiConstants;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by Jair Barzola on 22-Oct-17.
 */

public interface HomeRequest {

    @FormUrlEncoded
    @POST(ApiConstants.GET_REPORTS)
    Call<ResponseReports> getReports(@Header("Content-type") String contentType, @Header("Authorization") String token);
}
