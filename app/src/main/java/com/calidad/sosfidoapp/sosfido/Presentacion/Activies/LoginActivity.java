package com.calidad.sosfidoapp.sosfido.Presentacion.Activies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import com.calidad.sosfidoapp.sosfido.R;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_login)
    void login(){
        openHome();
    }
    public void openHome() {
        Intent i= new Intent(LoginActivity.this,HomeActivity.class);
        finish();
        startActivity(i);
       // overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

    }

}
