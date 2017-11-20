package com.calidad.sosfidoapp.sosfido.presentacion.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.calidad.sosfidoapp.sosfido.R;
import com.calidad.sosfidoapp.sosfido.data.entities.ReportAdoptionEntity;
import com.calidad.sosfidoapp.sosfido.data.entities.ResponseReport;
import com.calidad.sosfidoapp.sosfido.presentacion.activies.DetailMarkerActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jairbarzola on 12/11/17.
 */

public class AdoptionRecyclerAdapter extends  RecyclerView.Adapter<AdoptionRecyclerAdapter.ViewHolder> {

    public List<ResponseReport.ReportListAdoption> reportListAdoptionList;
    public Context context;

    public AdoptionRecyclerAdapter(Context context, List<ResponseReport.ReportListAdoption> reportListAdoptionList){
        this.context=context;
        this.reportListAdoptionList=reportListAdoptionList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adoptions, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position) {
        final ResponseReport.ReportListAdoption reportListAdoption = reportListAdoptionList.get(position);

        holder.nameAdoption.setText(reportListAdoption.getPetName());
        holder.lagAdoption.setText(reportListAdoption.getOwner().getAddress().getLocation());
        if (reportListAdoption.getAdoptionImage().equals("Sin imagen")) {
            Picasso.with(context).load("http://sosfido.tk/media/photos/users/profile/3b00f90e-cda.jpg").into(holder.imageAdoption);
        } else {
            Picasso.with(context).load(reportListAdoption.getAdoptionImage()).into(holder.imageAdoption);
        }
        final ReportAdoptionEntity report = new ReportAdoptionEntity(
                reportListAdoption.getId(),
                String.valueOf(reportListAdoption.getOwner().getId()),
                reportListAdoption.getOwner().getPhoneNumber(),
                reportListAdoption.getOwner().getPersonImage(),
                reportListAdoption.getPetName(),
                reportListAdoption.getAdopter(),
                reportListAdoption.getDescription(),
                reportListAdoption.getDate(),
                reportListAdoption.getAdoptionImage());
        holder.lnAdoption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detail = new Intent(context, DetailMarkerActivity.class);
                detail.putExtra("tag","1");
                detail.putExtra("ReportAdoption",report);
                detail.putExtra("Address",reportListAdoption.getOwner().getAddress());
                detail.putExtra("User",reportListAdoption.getOwner().getUser());
                context.startActivity(detail);

            }
        });
    }

    @Override
    public int getItemCount() {
        return reportListAdoptionList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ln_adoption) LinearLayout lnAdoption;
        @BindView(R.id.name_adoption) TextView nameAdoption;
        @BindView(R.id.image_adoption) CircleImageView imageAdoption;
        @BindView(R.id.lag_adoption) TextView lagAdoption;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
