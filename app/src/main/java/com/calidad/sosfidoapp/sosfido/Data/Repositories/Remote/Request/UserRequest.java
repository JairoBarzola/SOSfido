package com.calidad.sosfidoapp.sosfido.Data.Repositories.Remote.Request;

import com.calidad.sosfidoapp.sosfido.Data.Entities.AccessTokenEntity;
import com.calidad.sosfidoapp.sosfido.Data.Entities.LoginEntity;
import com.calidad.sosfidoapp.sosfido.Data.Entities.PersonEntity;
import com.calidad.sosfidoapp.sosfido.Data.Repositories.Remote.ApiConstants;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by jairbarzola on 24/09/17.
 */

public interface UserRequest {


    @FormUrlEncoded
    @POST(ApiConstants.LOGIN)
    Call<AccessTokenEntity> login (@Header("Content-type") String contentType,
                                   @Field("email") String email,@Field("password") String password);

    @GET(ApiConstants.USER_DESCRIPTION)
    Call<PersonEntity> getPerson (@Header("Content-type") String contentType,
                                  @Header("Authorization") String token,
                                  @Path("person_id") String person_id);
}
