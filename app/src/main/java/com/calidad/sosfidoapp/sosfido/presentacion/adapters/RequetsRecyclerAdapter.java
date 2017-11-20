package com.calidad.sosfidoapp.sosfido.presentacion.adapters;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.calidad.sosfidoapp.sosfido.R;
import com.calidad.sosfidoapp.sosfido.data.entities.ChangedStatusRequest;
import com.calidad.sosfidoapp.sosfido.data.entities.RequestsEntity;

import com.calidad.sosfidoapp.sosfido.data.repositories.local.SessionManager;
import com.calidad.sosfidoapp.sosfido.data.repositories.remote.ApiConstants;
import com.calidad.sosfidoapp.sosfido.data.repositories.remote.ServiceFactory;
import com.calidad.sosfidoapp.sosfido.data.repositories.remote.request.UserRequest;
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

public class RequetsRecyclerAdapter extends  RecyclerView.Adapter<RequetsRecyclerAdapter.ViewHolder>{

    public List<RequestsEntity> requestsEntityList;
    public Context context;
    private ProgressDialogCustom mProgressDialogCustom;
    public ServiceFactory serviceFactory;
    public SessionManager sessionManager;



    public RequetsRecyclerAdapter(Activity context, List<RequestsEntity> requestsEntityList){
        this.context=context;
        this.requestsEntityList=requestsEntityList;
        mProgressDialogCustom = new ProgressDialogCustom(context, "Enviando...");
        serviceFactory= new ServiceFactory();
        sessionManager = new SessionManager(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_requests, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final RequestsEntity requestsEntity = requestsEntityList.get(position);
        holder.nameRequest.setText(requestsEntity.getRequester().getUser().getFirstName());
        holder.dateRequest.setText(requestsEntity.getDate().substring(11,16)+"-"+requestsEntity.getDate().substring(0,10));
        if(requestsEntity.getRequester().getPersonImage().contains("Sin")){
            Picasso.with(context).load(R.drawable.uph).into(holder.imageRequest);
        }else{
            Picasso.with(context).load(requestsEntity.getRequester().getPersonImage()).into(holder.imageRequest);
        }
        holder.descrRequest.setText(requestsEntity.getDescription());

        holder.lnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(requestsEntity.getStatus().equals("1") || requestsEntity.getStatus().equals("0")) {
                    Toast.makeText(context,"Ya respondio esta solicitud",Toast.LENGTH_SHORT).show();
                }else{
                    openDialog(requestsEntity.getRequester().getUser().getFirstName(), requestsEntity.getDescription(), requestsEntity.getId()).show();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return requestsEntityList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_request) CircleImageView imageRequest;
        @BindView(R.id.name_request) TextView nameRequest;
        @BindView(R.id.date_request) TextView dateRequest;
        @BindView(R.id.ln_request) RelativeLayout lnRequest;
        @BindView(R.id.desc_request) TextView descrRequest;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private AlertDialog openDialog(String name, String description, final String id){
        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialog_request, null);
        dialog.setView(v);
        TextView descrip = v.findViewById(R.id.descr_requester);
        TextView title = v.findViewById(R.id.t_request);
        title.setText(name+" te envió una solicitud");
        descrip.setText(description);

        dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                start(1,id);
                dialogInterface.dismiss();
            }
        });
        dialog.setNegativeButton("Rechazar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                start(0,id);
                dialogInterface.dismiss();
            }
        });
        return  dialog.create();

    }

    private void start(int i,String idRequester) {
        mProgressDialogCustom.show();
        UserRequest userRequest = serviceFactory.createService(UserRequest.class);
        Call<ChangedStatusRequest.Response> call = userRequest.changedState(ApiConstants.CONTENT_TYPE_JSON, "Bearer " + sessionManager.getUserToken(),idRequester,new ChangedStatusRequest(i));
        call.enqueue(new Callback<ChangedStatusRequest.Response>() {
            @Override
            public void onResponse(Call<ChangedStatusRequest.Response> call, Response<ChangedStatusRequest.Response> response) {
                if(response.isSuccessful()){
                    mProgressDialogCustom.dismiss();
                    Toast.makeText(context,"Enviado",Toast.LENGTH_SHORT).show();

                }else{
                    mProgressDialogCustom.dismiss();
                    Toast.makeText(context,"Hubo un error, intente mas tarde",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChangedStatusRequest.Response> call, Throwable t) {
                mProgressDialogCustom.dismiss();
                Toast.makeText(context,"No hubo conexión al servidor, por favor intente más tarde",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
