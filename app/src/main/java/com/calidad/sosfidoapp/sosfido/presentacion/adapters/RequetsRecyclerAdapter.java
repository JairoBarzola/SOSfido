package com.calidad.sosfidoapp.sosfido.presentacion.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.calidad.sosfidoapp.sosfido.R;
import com.calidad.sosfidoapp.sosfido.data.entities.RequestsEntity;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jairbarzola on 12/11/17.
 */

public class RequetsRecyclerAdapter extends  RecyclerView.Adapter<RequetsRecyclerAdapter.ViewHolder>{

    public List<RequestsEntity> requestsEntityList;
    public Context context;

    public RequetsRecyclerAdapter(Context context, List<RequestsEntity> requestsEntityList){
        this.context=context;
        this.requestsEntityList=requestsEntityList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_requests, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RequestsEntity requestsEntity = requestsEntityList.get(position);
        holder.nameRequest.setText(requestsEntity.getRequester().getUser().getFirstName()+" "+requestsEntity.getRequester().getUser().getFirstName());
        holder.dateRequest.setText(requestsEntity.getDate());
        Picasso.with(context).load(requestsEntity.getRequester().getPersonImage()).into(holder.imageRequest);


    }

    @Override
    public int getItemCount() {
        return requestsEntityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_request) CircleImageView imageRequest;
        @BindView(R.id.name_request) TextView nameRequest;
        @BindView(R.id.date_request) TextView dateRequest;
        @BindView(R.id.ln_request) LinearLayout lnRequest;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
