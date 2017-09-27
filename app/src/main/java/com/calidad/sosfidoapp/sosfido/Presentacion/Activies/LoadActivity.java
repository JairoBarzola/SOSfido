package com.calidad.sosfidoapp.sosfido.Presentacion.Activies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.calidad.sosfidoapp.sosfido.R;

public class LoadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        Thread t = new Thread(){
            public void run(){
                try {
                    sleep(2000);
                    openLogin();
                    finish();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }
    void openLogin(){
        Intent i = new Intent(LoadActivity.this,LoginActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        finish();
    }
}
