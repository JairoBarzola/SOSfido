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
import android.widget.TextView;
import android.widget.Toast;

import com.calidad.sosfidoapp.sosfido.R;
import com.calidad.sosfidoapp.sosfido.data.entities.MyProposalAdoptionsEntity;
import com.calidad.sosfidoapp.sosfido.presentacion.activies.RequestsActivity;
import com.calidad.sosfidoapp.sosfido.presentacion.fragments.ProposalAdoptionsFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.http.Body;

/**
 * Created by jairbarzola on 12/11/17.
 */

public class MyAdoptionRecylerAdapter extends  RecyclerView.Adapter<MyAdoptionRecylerAdapter.ViewHolder> {

    public List<MyProposalAdoptionsEntity> myPAList;
    public Context context;


    public MyAdoptionRecylerAdapter(Context context, List<MyProposalAdoptionsEntity> myPAList){
        this.context=context;
        this.myPAList=myPAList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_proposals, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MyProposalAdoptionsEntity myProposalAdoptionsEntity = myPAList.get(position);

        holder.nameProposal.setText(myProposalAdoptionsEntity.getPet_name());
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
                deleteProposal().show();
            }
        });

        holder.ivLook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });


    }

    public AlertDialog deleteProposal(){
        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Mensaje");
        dialog.setMessage("¿Desea eliminar esta propuesta?");
        dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
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
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
