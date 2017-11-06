package com.calidad.sosfidoapp.sosfido.presentacion.activies;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.calidad.sosfidoapp.sosfido.data.entities.PersonEntity;
import com.calidad.sosfidoapp.sosfido.data.entities.ResponseEntity;
import com.calidad.sosfidoapp.sosfido.data.repositories.local.SessionManager;
import com.calidad.sosfidoapp.sosfido.data.repositories.remote.ApiConstants;
import com.calidad.sosfidoapp.sosfido.data.repositories.remote.Request.UserRequest;
import com.calidad.sosfidoapp.sosfido.data.repositories.remote.ServiceFactory;
import com.calidad.sosfidoapp.sosfido.presentacion.fragments.HomeFragment;
import com.calidad.sosfidoapp.sosfido.R;
import com.calidad.sosfidoapp.sosfido.utils.ProgressDialogCustom;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.toolbar)
    Toolbar tb;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout container;
    //code profile
    private final int CODE_PROFILE = 120;
    //code report
    private final int CODE_REGISTER_REPORT = 130;
    View hView;
    PersonEntity personEntity;
    private TextView navName;
    private TextView navAddress;
    private CircleImageView navImage;
    private SessionManager sessionManager;
    private ProgressDialogCustom mProgressDialogCustom;
    HomeFragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setSupportActionBar(tb);
        sessionManager = new SessionManager(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, tb, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //codigo para setear los valores del nav_header_home
        hView = navigationView.getHeaderView(0);

        navName = hView.findViewById(R.id.nav_name);
        navAddress = hView.findViewById(R.id.nav_address);
        navImage = hView.findViewById(R.id.nav_image);
        navImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity(ProfileActivity.class, CODE_PROFILE);
            }
        });
        updateData();
        homeFragment = new HomeFragment().newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.body, homeFragment);
        transaction.commit();

        //ProgressDialog para el logout
        mProgressDialogCustom = new ProgressDialogCustom(this, "Cerrando Sesión...");

    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.ac_registrer_report) {
            openDialog();
            //  openRegisterActivity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openDialog() {

        final Dialog dialog = new Dialog(this);
        //LayoutInflater inflater = this.getLayoutInflater();
        dialog.setContentView(R.layout.dialog_report);
        TextView missing = dialog.findViewById(R.id.report_p);
        TextView abandoned = dialog.findViewById(R.id.report_a);
        TextView adopcion = dialog.findViewById(R.id.report_ad);

        missing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegisterActivity(1);
                dialog.dismiss();
            }
        });

        abandoned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegisterActivity(2);
                dialog.dismiss();
            }
        });


        adopcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegisterActivity(3);
                dialog.dismiss();
            }
        });
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    private void openRegisterActivity(int idReport) {
        Intent a = new Intent(HomeActivity.this, RegisterActivity.class);
        a.putExtra("idReport", idReport);
        startActivityForResult(a, CODE_REGISTER_REPORT);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_publications) {
            openActivity(PublicationsActivity.class);
        } else if (id == R.id.nav_profile) {
            openActivity(ProfileActivity.class, CODE_PROFILE);
        } else if (id == R.id.nav_record) {
            openActivity(RecordActivity.class);
        } else if (id == R.id.nav_suggestions) {
            openActivity(SuggestionsActivity.class);
        } else if (id == R.id.nav_logout) {
            closeSession();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //metodo para cerrar sesion
    private void closeSession() {
        setLoadingIndicator(true);
        PersonEntity personEntity = sessionManager.getPersonEntity();
        ServiceFactory serviceFactory = new ServiceFactory();
        UserRequest userRequest = serviceFactory.createService(UserRequest.class);
        Call<ResponseEntity> call = userRequest.logout(ApiConstants.CONTENT_TYPE, "Bearer " + sessionManager.getUserToken(), String.valueOf(personEntity.getId()));
        call.enqueue(new Callback<ResponseEntity>() {
            @Override
            public void onResponse(Call<ResponseEntity> call, Response<ResponseEntity> response) {
                if (response.isSuccessful()) {
                    ResponseEntity responseEntity = response.body();
                    if (responseEntity.isStatus()) {
                        sessionManager.closeSession();
                        setLoadingIndicator(false);
                        Intent actv = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(actv);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                    } else {
                        setLoadingIndicator(false);
                        showMessageError(getString(R.string.there_was_an_error_try_it_later));
                    }
                } else {
                    setLoadingIndicator(false);
                    showMessageError(getString(R.string.there_was_an_error_try_it_later));
                }
            }

            @Override
            public void onFailure(Call<ResponseEntity> call, Throwable t) {
                setLoadingIndicator(false);
                showMessageError(getString(R.string.no_server_connection_try_it_later));
            }
        });


    }

    void openActivity(Class<?> activity) {
        Intent actv = new Intent(HomeActivity.this, activity);
        startActivity(actv);
    }

    void openActivity(Class<?> activity, int code) {
        Intent actv = new Intent(HomeActivity.this, activity);
        startActivityForResult(actv, code);
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

    public void showMessageError(String message) {
        CoordinatorLayout container = findViewById(R.id.coordinatorLayout);
        this.showMessageSnack(container, message, R.color.error_red);

    }

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_PROFILE) {
            if (resultCode == RESULT_OK) {
                updateData();
            }
        }
        if (requestCode == CODE_REGISTER_REPORT) {
            if (resultCode == RESULT_OK) {
                homeFragment.loadMap();
            }
        }
    }

    public void updateData() {
        personEntity = sessionManager.getPersonEntity();
        if (personEntity.getPersonImage().contains("http")) {
            Picasso.with(getApplicationContext()).load(sessionManager.getPersonEntity().getPersonImage()).into(navImage);
        } else {
            navImage.setImageDrawable(getResources().getDrawable(R.drawable.user));
        }
        navName.setText(personEntity.getUser().getFirstName());
        navAddress.setText(personEntity.getUser().getEmail());
    }

}
