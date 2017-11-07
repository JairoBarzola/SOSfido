package com.calidad.sosfidoapp.sosfido.presentacion.activies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.calidad.sosfidoapp.sosfido.R;

public class DetailMarkerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_marker);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }
}
