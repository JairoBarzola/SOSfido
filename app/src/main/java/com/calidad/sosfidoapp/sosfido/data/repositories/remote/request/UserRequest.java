package com.calidad.sosfidoapp.sosfido.data.repositories.remote.request;

import com.calidad.sosfidoapp.sosfido.data.entities.AccessTokenEntity;
import com.calidad.sosfidoapp.sosfido.data.entities.ChangedStatusRequest;
import com.calidad.sosfidoapp.sosfido.data.entities.MyRequestEntity;
import com.calidad.sosfidoapp.sosfido.data.entities.PersonDeviceEntity;
import com.calidad.sosfidoapp.sosfido.data.entities.PersonEntity;
import com.calidad.sosfidoapp.sosfido.data.entities.RequestsEntity;
import com.calidad.sosfidoapp.sosfido.data.entities.ResponseEntity;
import com.calidad.sosfidoapp.sosfido.data.entities.ResponseRegisterEntity;
import com.calidad.sosfidoapp.sosfido.data.entities.ResponseUser;
import com.calidad.sosfidoapp.sosfido.data.repositories.remote.ApiConstants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by jairbarzola on 24/09/17.
 */

public interface UserRequest {


    @FormUrlEncoded
    @POST(ApiConstants.LOGIN)
    Call<AccessTokenEntity> login (@Header("Content-type") String contentType, @Field("email") String email, @Field("password") String password,@Field("id_onesignal") String id_onesignal);

    @GET(ApiConstants.U_DESCRIPTION)
    Call<PersonEntity> getPerson (@Header("Content-type") String contentType, @Header("Authorization") String token, @Path("person_id") String person_id);
    @FormUrlEncoded
    @POST(ApiConstants.LOGOUT)
    Call<ResponseEntity> logout (@Header("Content-type") String contentType, @Header("Authorization") String token, @Field("person_id") String personEntity);

    @FormUrlEncoded
    @POST(ApiConstants.REGISTER)
    Call<ResponseRegisterEntity> registerUser (@Header("Content-type") String contentType, @Field("first_name") String firstname,
                                               @Field("last_name") String lastname, @Field("email") String email,
                                               @Field("password") String password, @Field("born_date") String borndate,
                                               @Field("location") String location, @Field("latitude") String longitude,
                                               @Field("longitude") String latitude, @Field("phone_number") String phone);


    @POST(ApiConstants.UPLOAD_PHOTO)
    Call<ResponseUser.Photo> uploadPhoto (@Header("Content-type") String contentType, @Header("Authorization") String token, @Body ResponseUser.PhotoBody photoBody);


    @PATCH(ApiConstants.CHANGE_PHOTO)
    Call<ResponseUser.PhotoChange> changePhoto (@Header("Content-type") String contentType, @Header("Authorization") String token, @Body ResponseUser.PhotoChange photoBody, @Path("person_id") String person_id);

    @FormUrlEncoded
    @POST(ApiConstants.FIND_EMAIL)
    Call<ResponseUser.ForgotAccount> sendEmail(@Header("Content-type") String contentType,@Field("email") String email);

    @FormUrlEncoded
    @POST(ApiConstants.UPDATE_PASS)
    Call<ResponseUser.ForgotAccount> updatePassword(@Header("Content-type") String contentType, @Field("user_id") String person_id, @Field("password") String password);


    @GET(ApiConstants.GET_REQUESTS)
    Call<List<RequestsEntity>> getRequest(@Header("Authorization") String token, @Query("proposal_id") String requester);

    @GET(ApiConstants.GET_REQUESTS)
    Call<List<MyRequestEntity>> getMyRequest(@Header("Authorization") String token, @Query("requester_id") String requester);

    @PATCH(ApiConstants.EDIT_U)
    Call<PersonEntity> editPerson(@Header("Content-type") String contentType, @Header("Authorization") String token,@Path("person_id") String person_id,@Body PersonEntity personEntity);

    @GET(ApiConstants.GET_REQUEST_PERSON)
    Call<List<MyRequestEntity>> getRequestByPerson(@Header("Authorization") String token, @Query("requester_id") String requester_id);

    @POST(ApiConstants.REGISTER_DEVICE)
    Call<PersonDeviceEntity.ResponseDevice> registerDevide(@Header("Content-type") String contentType, @Header("Authorization") String token, @Body PersonDeviceEntity personDeviceEntity);

    @PATCH(ApiConstants.CHANGED_STATE)
    Call<ChangedStatusRequest.Response> changedState(@Header("Content-type") String contentType, @Header("Authorization") String token, @Path("request_id") String requester_id, @Body ChangedStatusRequest changedStatusRequest);
}
