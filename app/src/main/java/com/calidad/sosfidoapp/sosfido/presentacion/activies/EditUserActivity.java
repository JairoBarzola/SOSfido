package com.calidad.sosfidoapp.sosfido.presentacion.activies;

import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.calidad.sosfidoapp.sosfido.R;
import com.calidad.sosfidoapp.sosfido.presentacion.contracts.EditUserContract;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditUserActivity extends AppCompatActivity implements EditUserContract.View {
    @BindView(R.id.toolbar) Toolbar tb;
    @BindView(R.id.coordinatorLayout) CoordinatorLayout container;
    @BindView(R.id.et_first_edname) EditText etFirstName;
    @BindView(R.id.et_last_edname) EditText etLastName;
    @BindView(R.id.et_edphone) EditText etPhone;
    @BindView(R.id.et_eddistrict) EditText etDistrict;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        ButterKnife.bind(this);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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

    }

    @Override
    public void setMessageError(String error) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.ac_update) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
