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
import com.calidad.sosfidoapp.sosfido.data.entities.MyProposalAdoptionsEntity;
import com.calidad.sosfidoapp.sosfido.data.repositories.local.SessionManager;
import com.calidad.sosfidoapp.sosfido.data.repositories.remote.ApiConstants;
import com.calidad.sosfidoapp.sosfido.data.repositories.remote.ServiceFactory;
import com.calidad.sosfidoapp.sosfido.data.repositories.remote.request.ReportRequest;
import com.calidad.sosfidoapp.sosfido.presentacion.activies.DetailMarkerActivity;
import com.calidad.sosfidoapp.sosfido.presentacion.activies.RequestsActivity;
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
 * Created by jairbarzola on 12/11/17.
 */

public class MyAdoptionRecyclerAdapter extends  RecyclerView.Adapter<MyAdoptionRecyclerAdapter.ViewHolder> {

    public List<MyProposalAdoptionsEntity> myPAList;
    public Context context;
    public SessionManager sessionManager;
    private ProgressDialogCustom mProgressDialogCustom;


    public MyAdoptionRecyclerAdapter(Context context, List<MyProposalAdoptionsEntity> myPAList){
        this.context=context;
        this.myPAList=myPAList;
        sessionManager = new SessionManager(context);
        mProgressDialogCustom = new ProgressDialogCustom(context, "Eliminando...");
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_proposals, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final MyProposalAdoptionsEntity myProposalAdoptionsEntity = myPAList.get(position);

        holder.nameProposal.setText(myProposalAdoptionsEntity.getPetName());
        holder.dateProposal.setText(myProposalAdoptionsEntity.getDate());
        if (myProposalAdoptionsEntity.getAdoptionImage().equals("Sin imagen")) {
            Picasso.with(context).load("http://sosfido.tk/media/photos/users/profile/3b00f90e-cda.jpg").into(holder.imageProposal);
        } else {
            Picasso.with(context).load(myProposalAdoptionsEntity.getAdoptionImage()).into(holder.imageProposal);
        }
        holder.ivlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detail = new Intent(context, RequestsActivity.class);
                detail.putExtra("id",myProposalAdoptionsEntity.getId());
                context.startActivity(detail);
            }
        });

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProposal(myProposalAdoptionsEntity.getId(),position).show();
            }
        });

        holder.lnMyAdoption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detail = new Intent(context, DetailMarkerActivity.class);
                detail.putExtra("ReportMyAdoption",myProposalAdoptionsEntity);
                detail.putExtra("tag","3");
                context.startActivity(detail);
            }
        });
    }
    public AlertDialog deleteProposal(final String id,final int pos){
        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Mensaje");
        dialog.setMessage("¿Desea eliminar esta propuesta?");
        dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                start(id,pos);

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
        ServiceFactory serviceFactory = new ServiceFactory();
        ReportRequest reportRequest = serviceFactory.createService(ReportRequest.class);
        Call<DeleteProposalEntity.Reponse> call = reportRequest.deleteProposal(ApiConstants.CONTENT_TYPE_JSON, "Bearer " + sessionManager.getUserToken(),id,new DeleteProposalEntity(true));
        call.enqueue(new Callback<DeleteProposalEntity.Reponse>() {
            @Override
            public void onResponse(Call<DeleteProposalEntity.Reponse> call, Response<DeleteProposalEntity.Reponse> response) {
                mProgressDialogCustom.show();
                if(response.isSuccessful()){
                    deletItem(pos);
                    Toast.makeText(context,"Eliminado exitosamente",Toast.LENGTH_SHORT).show();
                    mProgressDialogCustom.dismiss();
                }else{
                    Toast.makeText(context,"Hubo un error, intentelo mas tarde.",Toast.LENGTH_SHORT).show();
                    mProgressDialogCustom.dismiss();
                }
            }

            @Override
            public void onFailure(Call<DeleteProposalEntity.Reponse> call, Throwable t) {
                Toast.makeText(context,"Hubo un error, intentelo mas tarde.",Toast.LENGTH_SHORT).show();
                mProgressDialogCustom.dismiss();
            }
        });
    }
    public void deletItem(int pos){
        myPAList.remove(pos);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return myPAList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_proposal) CircleImageView imageProposal;
        @BindView(R.id.name_proposal) TextView nameProposal;
        @BindView(R.id.date_proposal) TextView dateProposal;
        @BindView(R.id.iv_list) ImageView ivlist;
        @BindView(R.id.iv_look) ImageView ivLook;
        @BindView(R.id.iv_delete) ImageView ivDelete;
        @BindView(R.id.ln_myadoption) RelativeLayout lnMyAdoption;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


}
