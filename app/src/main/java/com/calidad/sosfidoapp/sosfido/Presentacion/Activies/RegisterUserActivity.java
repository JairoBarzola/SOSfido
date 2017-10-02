package com.calidad.sosfidoapp.sosfido.Presentacion.Activies;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.calidad.sosfidoapp.sosfido.Presentacion.Contracts.LoginContract;
import com.calidad.sosfidoapp.sosfido.Presentacion.Contracts.RegisterUserContract;

import com.calidad.sosfidoapp.sosfido.Presentacion.Fragments.RegisterUserFragment;
import com.calidad.sosfidoapp.sosfido.Presentacion.Presenters.RegisterUserImpl;
import com.calidad.sosfidoapp.sosfido.R;
import com.calidad.sosfidoapp.sosfido.Utils.ProgressDialogCustom;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jairbarzola on 29/09/17.
 */

public class RegisterUserActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        RegisterUserFragment fragment = (RegisterUserFragment) getSupportFragmentManager().findFragmentById(R.id.body);
        if(fragment==null){
            fragment = RegisterUserFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.body,fragment);
            transaction.commit();

        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}