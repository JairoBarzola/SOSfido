package com.calidad.sosfidoapp.sosfido.presentacion.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.calidad.sosfidoapp.sosfido.R;
import com.calidad.sosfidoapp.sosfido.data.entities.DeleteProposalEntity;
import com.calidad.sosfidoapp.sosfido.data.entities.MyRequestEntity;
import com.calidad.sosfidoapp.sosfido.data.entities.ReportAdoptionEntity;
import com.calidad.sosfidoapp.sosfido.data.entities.RequestModelEntity;
import com.calidad.sosfidoapp.sosfido.data.repositories.local.SessionManager;
import com.calidad.sosfidoapp.sosfido.data.repositories.remote.ApiConstants;
import com.calidad.sosfidoapp.sosfido.data.repositories.remote.ServiceFactory;
import com.calidad.sosfidoapp.sosfido.data.repositories.remote.request.ReportRequest;
import com.calidad.sosfidoapp.sosfido.presentacion.activies.DetailMarkerActivity;
import com.calidad.sosfidoapp.sosfido.utils.ProgressDialogCustom;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jairbarzola on 14/11/17.
 */

public class MyRequestRecyclerAdapter extends RecyclerView.Adapter<MyRequestRecyclerAdapter.ViewHolder> {

    public Context context;
    public SessionManager sessionManager;
    public ServiceFactory serviceFactory;
    public List<MyRequestEntity> requestsEntityList;
    private ProgressDialogCustom mProgressDialogCustom;

    public MyRequestRecyclerAdapter(Context context, List<MyRequestEntity> requestsEntityList) {
        this.context = context;
        this.requestsEntityList = requestsEntityList;
        serviceFactory = new ServiceFactory();
        sessionManager = new SessionManager(context);
        mProgressDialogCustom = new ProgressDialogCustom(context, "Eliminando...");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_request, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final MyRequestEntity requestsEntity = requestsEntityList.get(position);
        holder.nameRequest.setText(requestsEntity.getAdoptionProposal().getPetName());
        holder.dateRequest.setText(requestsEntity.getDescription());
        if(requestsEntity.getStatus().equals("0")){
            holder.imageRequest.setBorderColor(context.getResources().getColor(R.color.rechazado));
        }else{
            if(requestsEntity.getStatus().equals("1")){
                holder.imageRequest.setBorderColor(context.getResources().getColor(R.color.aceptado));
            }
        }
        if (requestsEntity.getAdoptionProposal().getAdoptionImage().contains("Sin")) {
            Picasso.with(context).load(R.drawable.mph).into(holder.imageRequest);
        } else {
            Picasso.with(context).load(requestsEntity.getAdoptionProposal().getAdoptionImage()).into(holder.imageRequest);
        }
        holder.deleteRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProposal(requestsEntity.getId(), position).show();
            }
        });
        holder.lnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RequestModelEntity requestModelEntity = new RequestModelEntity(
                        requestsEntity.getId(),requestsEntity.getAdoptionProposal().getId(),
                        requestsEntity.getAdoptionProposal().getPetName(),requestsEntity.getAdoptionProposal().getStatus(),
                        requestsEntity.getAdoptionProposal().getDescription(),
                        requestsEntity.getAdoptionProposal().getDate(),requestsEntity.getAdoptionProposal().getOwner().getPhoneNumber(),
                        requestsEntity.getAdoptionProposal().getOwner().getUser().getFirstName(),requestsEntity.getAdoptionProposal().getOwner().getUser().getLastName(),
                        requestsEntity.getAdoptionProposal().getOwner().getUser().getEmail(),requestsEntity.getAdoptionProposal().getAdoptionImage(),
                        requestsEntity.getStatus(),requestsEntity.getDescription(),requestsEntity.getDate());
                Intent detail = new Intent(context, DetailMarkerActivity.class);
                detail.putExtra("Request",requestModelEntity);
                detail.putExtra("tag","4");
                context.startActivity(detail);
            }
        });

    }

    @Override
    public int getItemCount() {
        return requestsEntityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_rq_adp) CircleImageView imageRequest;
        @BindView(R.id.name_rq_adp) TextView nameRequest;
        @BindView(R.id.date_rq_adp) TextView dateRequest;
        @BindView(R.id.ln_rq_adp) RelativeLayout lnRequest;
        @BindView(R.id.iv_delete_rq) ImageView deleteRequest;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public AlertDialog deleteProposal(final String id, final int pos) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Mensaje");
        dialog.setMessage("¿Desea eliminar esta petición?");
        dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                start(id, pos);

            }
        });
        dialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        return dialog.create();
    }

    private void start(String id, final int pos) {
        mProgressDialogCustom.show();
        ReportRequest reportRequest = serviceFactory.createService(ReportRequest.class);
        Call<DeleteProposalEntity.Reponse> call = reportRequest.deleteRequest(ApiConstants.CONTENT_TYPE_JSON, "Bearer " + sessionManager.getUserToken(), id, new DeleteProposalEntity(true));
        call.enqueue(new Callback<DeleteProposalEntity.Reponse>() {
            @Override
            public void onResponse(Call<DeleteProposalEntity.Reponse> call, Response<DeleteProposalEntity.Reponse> response) {
                if (response.isSuccessful()) {
                    deletItem(pos);
                    Toast.makeText(context, "Eliminado exitosamente", Toast.LENGTH_SHORT).show();
                    mProgressDialogCustom.dismiss();
                } else {
                    Toast.makeText(context, "Hubo un error, intentelo mas tarde", Toast.LENGTH_SHORT).show();
                    mProgressDialogCustom.dismiss();
                }
            }

            @Override
            public void onFailure(Call<DeleteProposalEntity.Reponse> call, Throwable t) {
                Toast.makeText(context, "Hubo un error, intentelo mas tarde", Toast.LENGTH_SHORT).show();
                mProgressDialogCustom.dismiss();
            }
        });
    }

    public void deletItem(int pos) {
        requestsEntityList.remove(pos);
        notifyDataSetChanged();
    }
}
