package com.calidad.sosfidoapp.sosfido.presentacion.activies;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.calidad.sosfidoapp.sosfido.R;
import com.calidad.sosfidoapp.sosfido.data.entities.ReportEntity;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailMarkerActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar tb;
    @BindView(R.id.im_report) ImageView imageReport;
    @BindView(R.id.name_report) TextView nameReport;
    @BindView(R.id.description_report) TextView descriptionReport;
    @BindView(R.id.ubicación_report) TextView ubicationReport;
    @BindView(R.id.date_report) TextView dateReport;
    @BindView(R.id.collapsing) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.btn_help) Button btnHelp;
    ReportEntity data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_marker);

        ButterKnife.bind(this);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        collapsingToolbarLayout.setTitle("");
        Bundle parametros = this.getIntent().getExtras();
        data = parametros.getParcelable("Report");

        if (data != null) {

            nameReport.setText(data.getNamePet());
            if (data.getPhoto().equals("Sin imagen")) {
                Picasso.with(getApplication()).load("http://sosfido.tk/media/photos/users/profile/3b00f90e-cda.jpg").into(imageReport);
            } else {
                Picasso.with(getApplication()).load(data.getPhoto()).into(imageReport);
            }
            descriptionReport.setText(data.getDescription());
            ubicationReport.setText(data.getLocation());
            dateReport.setText(data.getDate());

        }

        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Implementation next sprint",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
