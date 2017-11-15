package com.calidad.sosfidoapp.sosfido.presentacion.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.calidad.sosfidoapp.sosfido.data.entities.ReportEntity;
import com.calidad.sosfidoapp.sosfido.R;
import com.calidad.sosfidoapp.sosfido.presentacion.activies.DetailMarkerActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jairbarzola on 31/10/17.
 */

public class ReportRecyclerAdapter extends RecyclerView.Adapter<ReportRecyclerAdapter.ViewHolder> {

    private List<ReportEntity> reportEntityList = new ArrayList<>();
    private Context context;

    public ReportRecyclerAdapter(Context context, List<ReportEntity> reportEntityList) {
        this.context = context;
        this.reportEntityList = reportEntityList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_publication, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ReportEntity reportEntity = reportEntityList.get(position);
        if (reportEntity.getTypeReport().equals("1")) {
            holder.image.setBorderColor(ContextCompat.getColor(context, R.color.colorMissing));
        } else {
            if (reportEntity.getTypeReport().equals("2")) {
                holder.image.setBorderColor(ContextCompat.getColor(context, R.color.colorAbandoned));
            }
            /*else {
                holder.image.setBorderColor(ContextCompat.getColor(context, R.color.colorAdoption));
            }*/
        }
        if (reportEntity.getPhoto().equals("Sin imagen")) {
            Picasso.with(context).load("http://sosfido.tk/media/photos/users/profile/3b00f90e-cda.jpg").into(holder.image);
        } else {
            Picasso.with(context).load(reportEntity.getPhoto()).into(holder.image);
        }
        holder.name.setText(reportEntity.getNamePet());
        holder.lag.setText(reportEntity.getDescription());
        holder.lnPublication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detail = new Intent (context, DetailMarkerActivity.class);
                detail.putExtra("Report",reportEntity);
                detail.putExtra("tag","2");
                context.startActivity(detail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return reportEntityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_publication) CircleImageView image;
        @BindView(R.id.name_publication) TextView name;
        @BindView(R.id.txtLag) TextView lag;
        @BindView(R.id.ln_publication) LinearLayout lnPublication;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
