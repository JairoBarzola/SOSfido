package com.calidad.sosfidoapp.sosfido.data.repositories.remote.Request;

import com.calidad.sosfidoapp.sosfido.data.entities.ResponseReport;
import com.calidad.sosfidoapp.sosfido.data.repositories.remote.ApiConstants;

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
    Call<List<ResponseReport.ReportList>> getReportsAbandoned(@Header("Authorization") String token, @Query("all_reports") boolean value, @Query("abandoned_pet") boolean values2);

    @GET(ApiConstants.GET_REPORTS)
    Call<List<ResponseReport.ReportListMissing>> getReportsMissing(@Header("Authorization") String token, @Query("all_reports") boolean value, @Query("missing_pet") boolean values2);
    @GET(ApiConstants.GET_REPORTS_ADOPTION)
    Call<List<ResponseReport.ReportListAdoption>> getReportsAdoption(@Header("Authorization") String token, @Query("all_adoptions") boolean value);
}
