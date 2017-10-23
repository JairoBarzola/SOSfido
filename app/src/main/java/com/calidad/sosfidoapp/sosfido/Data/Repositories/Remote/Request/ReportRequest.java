package com.calidad.sosfidoapp.sosfido.Data.Repositories.Remote.Request;

import com.calidad.sosfidoapp.sosfido.Data.Entities.ReportResponse;
import com.calidad.sosfidoapp.sosfido.Data.Entities.ResponseStatus;
import com.calidad.sosfidoapp.sosfido.Data.Repositories.Remote.ApiConstants;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by Jair Barzola on 22-Oct-17.
 */

public interface ReportRequest {

    @FormUrlEncoded
    @POST(ApiConstants.SEND_REPORT)
    Call<ReportResponse> sendReport(@Header("Content-type") String contentType,@Header("Authorization") String token,
                                    @Field("person") String person,@Field("location") String location,
                                    @Field("latitud") String latitud,@Field("longitude") String longitude,
                                    @Field("description") String description,@Field("image") String image,
                                    @Field("Name") String name ,@Field("phone") String phone);

    @FormUrlEncoded
    @POST(ApiConstants.SEND_PHOTO)
    Call<ResponseStatus> sendPhoto(@Header("Content-type") String contentType, @Header("Authorization") String token,
                                   @Field("report") String report, @Field("image") String image);

}
