package com.calidad.sosfidoapp.sosfido.Presentacion.Activies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.calidad.sosfidoapp.sosfido.Data.Repositories.Local.SessionManager;
import com.calidad.sosfidoapp.sosfido.R;

public class LoadActivity extends AppCompatActivity {
    private SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        sessionManager = new SessionManager(getApplicationContext());
        Thread t = new Thread(){
            public void run(){
                try {
                    sleep(2000);
                    verifyToken();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }

    private void verifyToken() {
        if(sessionManager.isLogin()==true){
            openActivity(HomeActivity.class);
        }else{
            openActivity(LoginActivity.class);
        }
    }

    void openActivity(Class<?> activity){

        Intent i = new Intent(LoadActivity.this,activity);
        startActivity(i);
        finish();
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }
}
