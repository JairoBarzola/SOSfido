package com.calidad.sosfidoapp.sosfido.presentacion.activies;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.calidad.sosfidoapp.sosfido.R;
import com.calidad.sosfidoapp.sosfido.data.entities.RequestsEntity;

import com.calidad.sosfidoapp.sosfido.presentacion.adapters.RequetsRecyclerAdapter;
import com.calidad.sosfidoapp.sosfido.presentacion.contracts.RequestContract;
import com.calidad.sosfidoapp.sosfido.presentacion.presenters.RequestPresenterImpl;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RequestsActivity extends AppCompatActivity implements RequestContract.View{

    private Unbinder unbinder;
    @BindView(R.id.toolbar) Toolbar tb;
    @BindView(R.id.recyclerViewR) RecyclerView recyclerView;
    @BindView(R.id.empty) TextView emptyView;
    @BindView(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.coordinatorLayout) CoordinatorLayout container;

    private RequestContract.Presenter presenter;
    public LinearLayoutManager layoutManager;
    public RequetsRecyclerAdapter adapter;


    public Bundle parametros;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);
        unbinder = ButterKnife.bind(this);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        presenter = new RequestPresenterImpl(getApplicationContext(),this);
        parametros= this.getIntent().getExtras();
        // recyclerview
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        //swipeRefreshLayout
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.start(parametros.getString("id"));
            }
        });

        presenter.start(parametros.getString("id"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void initRecyclerView(List<RequestsEntity> requestsEntityList) {

        adapter = new RequetsRecyclerAdapter(this,requestsEntityList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showSwipeLayout() {
        swipeRefresh.setRefreshing(true);

    }

    @Override
    public void hideSWipeLayout() {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void showEmpty() {
        recyclerView.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmpty() {
        recyclerView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
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

}
