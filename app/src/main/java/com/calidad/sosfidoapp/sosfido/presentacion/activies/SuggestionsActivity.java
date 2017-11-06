package com.calidad.sosfidoapp.sosfido.presentacion.activies;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.calidad.sosfidoapp.sosfido.presentacion.fragments.SuggenstionsFragment;
import com.calidad.sosfidoapp.sosfido.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SuggestionsActivity extends AppCompatActivity {

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
        SuggenstionsFragment publicationsFragment = new SuggenstionsFragment().newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.body, publicationsFragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
