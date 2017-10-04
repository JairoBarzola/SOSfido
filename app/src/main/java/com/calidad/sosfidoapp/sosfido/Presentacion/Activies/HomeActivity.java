package com.calidad.sosfidoapp.sosfido.Presentacion.Activies;

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

import com.calidad.sosfidoapp.sosfido.Data.Entities.PersonEntity;
import com.calidad.sosfidoapp.sosfido.Data.Entities.ResponseEntity;
import com.calidad.sosfidoapp.sosfido.Data.Repositories.Local.SessionManager;
import com.calidad.sosfidoapp.sosfido.Data.Repositories.Remote.ApiConstants;
import com.calidad.sosfidoapp.sosfido.Data.Repositories.Remote.Request.UserRequest;
import com.calidad.sosfidoapp.sosfido.Data.Repositories.Remote.ServiceFactory;
import com.calidad.sosfidoapp.sosfido.Presentacion.Fragments.HomeFragment;
import com.calidad.sosfidoapp.sosfido.R;
import com.calidad.sosfidoapp.sosfido.Utils.ProgressDialogCustom;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.coordinatorLayout) CoordinatorLayout container;

    private SessionManager sessionManager;
    private ProgressDialogCustom mProgressDialogCustom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        sessionManager = new SessionManager(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //codigo para setear los valores del nav_header_home
        View hView =  navigationView.getHeaderView(0);
        TextView navName = (TextView) hView.findViewById(R.id.nav_name);
        TextView navAddress = (TextView) hView.findViewById(R.id.nav_address);
        PersonEntity personEntity = sessionManager.getPersonEntity();
        navName.setText(personEntity.getUser().getFirst_name()+" "+personEntity.getUser().getLast_name());
        navAddress.setText(personEntity.getAddress());


        HomeFragment homeFragment = new HomeFragment().newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.body,homeFragment);
        transaction.commit();

        //ProgressDialog para el logout
        mProgressDialogCustom = new ProgressDialogCustom(this,"Cerrando Sesión...");

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
            openRegisterActivity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openRegisterActivity() {
        Intent a = new Intent(HomeActivity.this, RegisterActivity.class);
        startActivity(a);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_publications) {
            openActivity(PublicationsActivity.class);
        } else if (id == R.id.nav_profile) {
            openActivity(ProfileActivity.class);
        } else if (id == R.id.nav_record) {
            openActivity(RecordActivity.class);
        } else if (id == R.id.nav_suggestions) {
            openActivity(SuggestionsActivity.class);
        }else if(id == R.id.nav_logout) {
            closeSession();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //metodo para cerrar sesion
    private void closeSession() {
        setLoadingIndicator(true);
        PersonEntity personEntity = sessionManager.getPersonEntity();
        UserRequest userRequest = ServiceFactory.createService(UserRequest.class);
        Call<ResponseEntity> call = userRequest.logout(ApiConstants.CONTENT_TYPE,"Bearer "+sessionManager.getUserToken(),String.valueOf(personEntity.getId()));
        call.enqueue(new Callback<ResponseEntity>() {
            @Override
            public void onResponse(Call<ResponseEntity> call, Response<ResponseEntity> response) {
                if(response.isSuccessful()){
                    ResponseEntity responseEntity = response.body();
                    if(responseEntity.isStatus()) {
                        sessionManager.closeSession();
                        setLoadingIndicator(false);
                        Intent actv = new Intent(HomeActivity.this,LoginActivity.class);
                        startActivity(actv);
                        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                        finish();
                    }else{
                        setLoadingIndicator(false);
                        showMessageError(getString(R.string.there_was_an_error_try_it_later));
                    }
                }
                else{
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

    void openActivity(Class<?> activity){
        Intent actv = new Intent(HomeActivity.this,activity);
        startActivity(actv);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }

    public void showMessageSnack(View container, String message, int colorResource) {
        if (container != null) {
            Snackbar snackbar = Snackbar
                    .make(container, message, Snackbar.LENGTH_LONG);
            snackbar.setActionTextColor(Color.WHITE);
            View sbView = snackbar.getView();
            sbView.setBackgroundColor(ContextCompat.getColor(this, colorResource));
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        } else {
            Toast toast =
                    Toast.makeText(getApplicationContext(),
                            message, Toast.LENGTH_LONG);

            toast.show();
        }

    }
    public void showMessageError(String message) {
        CoordinatorLayout container = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        this.showMessageSnack(container, message, R.color.error_red);

    }
    public void setLoadingIndicator(boolean active) {
        if(mProgressDialogCustom!=null){
            if(active){
                mProgressDialogCustom.show();
            }
            else{
                mProgressDialogCustom.dismiss();
            }
        }
    }
}
