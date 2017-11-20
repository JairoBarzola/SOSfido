package com.calidad.sosfidoapp.sosfido.presentacion.activies;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.calidad.sosfidoapp.sosfido.R;
import com.calidad.sosfidoapp.sosfido.data.entities.Address;
import com.calidad.sosfidoapp.sosfido.data.entities.MyProposalAdoptionsEntity;
import com.calidad.sosfidoapp.sosfido.data.entities.ProposalAdoption;
import com.calidad.sosfidoapp.sosfido.data.entities.ReportAdoptionEntity;
import com.calidad.sosfidoapp.sosfido.data.entities.ReportEntity;
import com.calidad.sosfidoapp.sosfido.data.entities.RequestModelEntity;
import com.calidad.sosfidoapp.sosfido.data.entities.UserEntity;
import com.calidad.sosfidoapp.sosfido.data.repositories.local.SessionManager;
import com.calidad.sosfidoapp.sosfido.presentacion.contracts.DetailMarkerContract;

import com.calidad.sosfidoapp.sosfido.presentacion.presenters.DetailMarkerPresenterImpl;
import com.calidad.sosfidoapp.sosfido.utils.ProgressDialogCustom;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailMarkerActivity extends AppCompatActivity implements DetailMarkerContract.View {

    @BindView(R.id.toolbar) Toolbar tb;
    @BindView(R.id.coordinatorLayout) CoordinatorLayout container;
    @BindView(R.id.im_report) ImageView imageReport;
    @BindView(R.id.name_report) TextView nameReport;
    @BindView(R.id.description_report) TextView descriptionReport;
    @BindView(R.id.ubicación_report) TextView ubicationReport;
    @BindView(R.id.date_report) TextView dateReport;
    @BindView(R.id.collapsing) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.btn_help) FloatingActionButton btnHelp;
    @BindView(R.id.public_report) TextView publicReport;
    @BindView(R.id.cardView3) CardView cardView3;
    @BindView(R.id.cardView2) CardView cardView2;
    @BindView(R.id.cardView4) CardView cardView4;
    @BindView(R.id.description_state) TextView descrState;
    @BindView(R.id.my_request) TextView myRequest;
    @BindView(R.id.phone) TextView phone;
    @BindView(R.id.email) TextView email;

    public ReportEntity data;
    public String tag;
    public Bundle parametros;
    public Address address;
    public UserEntity userEntity;
    public ReportAdoptionEntity reportAdoption;
    public DetailMarkerContract.Presenter presenter;
    private ProgressDialogCustom mProgressDialogCustom;
    public SessionManager sessionManager;
    public MyProposalAdoptionsEntity myProposalAdoptionsEntity;
    public RequestModelEntity requestModelEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_marker);

        ButterKnife.bind(this);
        setSupportActionBar(tb);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        sessionManager = new SessionManager(getApplicationContext());

        presenter = new DetailMarkerPresenterImpl(getApplication(),this);
        mProgressDialogCustom = new ProgressDialogCustom(this, "Cargando...");
        collapsingToolbarLayout.setTitle("");
        parametros= this.getIntent().getExtras();

        tag = parametros.getString("tag");
        if (tag != null) {
            if(tag.equals("1")){
                address = parametros.getParcelable("Address");
                //personEntity = parametros.getParcelable("Person");
                userEntity = parametros.getParcelable("User");
                btnHelp.setImageResource(R.drawable.adoptedi);
                reportAdoption = parametros.getParcelable("ReportAdoption");
                nameReport.setText(reportAdoption.getPetName());
                if (reportAdoption.getAdoptionImage().equals("Sin imagen")) {
                    Picasso.with(getApplicationContext()).load(R.drawable.mph).into(imageReport);
                } else {
                    Picasso.with(getApplication()).load(reportAdoption.getAdoptionImage()).into(imageReport);
                }
                descriptionReport.setText(reportAdoption.getDescription());
                dateReport.setText(reportAdoption.getDate());
                publicReport.setText(userEntity.getFirstName()+" "+userEntity.getLastName());
                ubicationReport.setText(address.getLocation());
                invisibleView();
            }else{
                if(tag.equals("2")) {
                    cardView2.setVisibility(View.GONE);
                    btnHelp.setImageResource(R.drawable.abperi);
                    data = parametros.getParcelable("Report");
                    nameReport.setText(data.getNamePet());
                    ubicationReport.setText(data.getLocation());
                    if (data.getPhoto().equals("Sin imagen")) {
                        Picasso.with(getApplicationContext()).load(R.drawable.mph).into(imageReport);
                    } else {
                        Picasso.with(getApplication()).load(data.getPhoto()).into(imageReport);
                    }
                    descriptionReport.setText(data.getDescription());
                    dateReport.setText(data.getDate());
                    invisibleView();
                }else{
                    if(tag.equals("3")) {
                        btnHelp.setVisibility(View.GONE);
                        myProposalAdoptionsEntity = parametros.getParcelable("ReportMyAdoption");
                        nameReport.setText(myProposalAdoptionsEntity.getPetName());
                        descriptionReport.setText(myProposalAdoptionsEntity.getDescription());
                        dateReport.setText(myProposalAdoptionsEntity.getDate());
                        publicReport.setText("Mí");
                        ubicationReport.setText(sessionManager.getPersonEntity().getAddress().getLocation());
                        if (myProposalAdoptionsEntity.getAdoptionImage().equals("Sin imagen")) {
                            Picasso.with(getApplicationContext()).load(R.drawable.mph).into(imageReport);
                        } else {
                            Picasso.with(getApplication()).load(myProposalAdoptionsEntity.getAdoptionImage()).into(imageReport);
                        }
                        invisibleView();
                    }else{
                        btnHelp.setVisibility(View.GONE);
                        requestModelEntity = parametros.getParcelable("Request");
                        nameReport.setText(requestModelEntity.getPetName());
                        descriptionReport.setText(requestModelEntity.getDescriptionProposal());
                        dateReport.setText(requestModelEntity.getDateProposal());
                        publicReport.setText(requestModelEntity.getFirstName()+" "+requestModelEntity.getLastName());
                        ubicationReport.setText(sessionManager.getPersonEntity().getAddress().getLocation());
                        if (requestModelEntity.getAdoptionImage().contains("Sin")) {
                            Picasso.with(getApplicationContext()).load(R.drawable.mph).into(imageReport);
                        } else {
                            Picasso.with(getApplication()).load(requestModelEntity.getAdoptionImage()).into(imageReport);
                        }
                        // verify status
                        if(requestModelEntity.getStatus().equals("0")){
                            cardView3.setVisibility(View.VISIBLE);
                            cardView4.setVisibility(View.GONE);
                            descrState.setText("Su petición ha sido rechazada");
                            myRequest.setText(requestModelEntity.getDescription());
                        }else{
                            if(requestModelEntity.getStatus().equals("1")){
                            cardView3.setVisibility(View.VISIBLE);
                            descrState.setText("Su petición ha sido aceptada");
                            myRequest.setText(requestModelEntity.getDescription());
                            cardView4.setVisibility(View.VISIBLE);
                            phone.setText(requestModelEntity.getPhoneNumber());
                            email.setText(requestModelEntity.getEmail());
                            }else {
                                cardView3.setVisibility(View.VISIBLE);
                                cardView4.setVisibility(View.GONE);
                                descrState.setText("Su petición sigue en espera");
                                myRequest.setText(requestModelEntity.getDescription());

                            }

                        }
                    }
                }
            }

        }


        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tag.equals("1")){
                    proposal().show();
                }else{
                    //Toast.makeText(getApplicationContext(),"Implementation next sprint",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, "#AyudandoConSOSfido http://sosfido.tk/w/");
                    startActivity(Intent.createChooser(intent, "Share with"));
                }

            }
        });

    }
    void invisibleView(){
        cardView3.setVisibility(View.GONE);
        cardView4.setVisibility(View.GONE);
    }
    private AlertDialog proposal(){
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_proposal, null);
        dialog.setView(v);
        final EditText descr = (EditText) v.findViewById(R.id.edt_proposal);
        dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sendProposal(descr.getText().toString());
                dialogInterface.dismiss();
            }
        });
        dialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                 dialogInterface.dismiss();
            }
        });
        return  dialog.create();

    }

    private void sendProposal(String s) {
        if(!s.equals("")){
            presenter.sendProposalAdoption(new ProposalAdoption(reportAdoption.getIdReport(),String.valueOf(sessionManager.getPersonEntity().getId()),s));
        }else{
            Toast.makeText(getApplication(),"Ingrese la descripción",Toast.LENGTH_SHORT).show();
        }
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

    @Override
    public void setLoadingIndicator(boolean active) {
        if (mProgressDialogCustom != null) {
            if (active) {
                mProgressDialogCustom.show();
            } else {
                mProgressDialogCustom.dismiss();
            }
        }
    }

    @Override
    public void setMessageError(String error) {
        this.showMessageSnack(container, error, R.color.error_red);
    }
    public void showMessageSnack(View container, String message, int colorResource) {
        if (container != null) {
            Snackbar snackbar = Snackbar
                    .make(container, message, Snackbar.LENGTH_LONG);
            snackbar.setActionTextColor(Color.WHITE);
            View sbView = snackbar.getView();
            sbView.setBackgroundColor(ContextCompat.getColor(this, colorResource));
            TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        } else {
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }
    }
    public void setMessage(String message) {
        this.showMessageSnack(container, message, R.color.ok);
    }
}
