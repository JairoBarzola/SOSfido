package com.calidad.sosfidoapp.sosfido.data.repositories.remote.request;

import com.calidad.sosfidoapp.sosfido.data.entities.DeleteProposalEntity;
import com.calidad.sosfidoapp.sosfido.data.entities.MyProposalAdoptionsEntity;
import com.calidad.sosfidoapp.sosfido.data.entities.ProposalAdoption;
import com.calidad.sosfidoapp.sosfido.data.entities.ResponseReport;
import com.calidad.sosfidoapp.sosfido.data.entities.ResponseStatus;
import com.calidad.sosfidoapp.sosfido.data.repositories.remote.ApiConstants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

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

    @POST(ApiConstants.SEND_PROPOSAL)
    Call<ProposalAdoption.Response> sendProposal(@Header("Content-type") String contentType, @Header("Authorization") String token, @Body ProposalAdoption proposalAdoption);

    @GET(ApiConstants.GET_PROPOSAL)
    Call<List<MyProposalAdoptionsEntity>> getMyProposal( @Header("Authorization") String token, @Query("owner_id") String owner_id);

    @PATCH(ApiConstants.DELETE_PROPOSAL)
    Call<DeleteProposalEntity.Reponse> deleteProposal(@Header("Content-type") String contentType, @Header("Authorization") String token, @Path("proposal_id") String id , @Body DeleteProposalEntity deleteProposalEntity);
}
