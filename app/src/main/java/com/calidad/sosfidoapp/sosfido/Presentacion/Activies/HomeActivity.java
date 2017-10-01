package com.calidad.sosfidoapp.sosfido.Presentacion.Activies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.calidad.sosfidoapp.sosfido.Presentacion.Fragments.HomeFragment;
import com.calidad.sosfidoapp.sosfido.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.nav_view) NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        HomeFragment homeFragment = new HomeFragment().newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.body,homeFragment);
        transaction.commit();

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
            openActivity(LoginActivity.class);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    void openActivity(Class<?> activity){
        Intent actv = new Intent(HomeActivity.this,activity);
        startActivity(actv);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }
}
