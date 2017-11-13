package com.calidad.sosfidoapp.sosfido.presentacion.activies;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.calidad.sosfidoapp.sosfido.R;
import com.calidad.sosfidoapp.sosfido.presentacion.fragments.AdoptionsFragment;
import com.calidad.sosfidoapp.sosfido.presentacion.fragments.MyRequestsFragment;
import com.calidad.sosfidoapp.sosfido.presentacion.fragments.ProposalAdoptionsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HandleAdoptionsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar tb;
    @BindView(R.id.coordinatorLayout) CoordinatorLayout container;
    @BindView(R.id.view_pager) ViewPager mPager;
    @BindView(R.id.tabs) TabLayout mTabs;
    public final int CODE_REGISTER_REPORT = 140;
    public ViewPagerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_handle_adoptions);
        ButterKnife.bind(this);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setupViewPager(mPager);
        mTabs.setupWithViewPager(mPager);

    }

    private void setupViewPager(ViewPager mPager) {

        adapter= new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AdoptionsFragment(),"Buscar");
        adapter.addFragment(new MyRequestsFragment(), "Mis solicitudes");
        adapter.addFragment(new ProposalAdoptionsFragment(), "Mis propuestas ");
        mPager.setAdapter(adapter);
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


    //View pager
    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adoption, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.ac_add) {
            openRegisterActivity(3);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openRegisterActivity(int idReport) {
        Intent a = new Intent(this, RegisterActivity.class);
        a.putExtra("idReport", idReport);
        startActivityForResult(a, CODE_REGISTER_REPORT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_REGISTER_REPORT && resultCode == RESULT_OK) {
            setMessage("Registrado");
        }
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
    public void setMessage(String message) {
        this.showMessageSnack(container, message, R.color.ok);
    }


}
