package com.calidad.sosfidoapp.sosfido.Data.Repositories.Remote.Request;

import com.calidad.sosfidoapp.sosfido.Data.Entities.ResponseReport;
import com.calidad.sosfidoapp.sosfido.Data.Repositories.Remote.ApiConstants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by Jair Barzola on 22-Oct-17.
 */

public interface HomeRequest {


    @GET(ApiConstants.GET_REPORTS)
    Call<List<ResponseReport.ReportList>> getReports(@Header("Authorization") String token, @Query("all_reports") boolean value);
}
