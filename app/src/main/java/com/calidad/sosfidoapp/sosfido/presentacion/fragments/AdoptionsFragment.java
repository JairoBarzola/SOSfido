package com.calidad.sosfidoapp.sosfido.presentacion.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.calidad.sosfidoapp.sosfido.R;
import com.calidad.sosfidoapp.sosfido.data.entities.ResponseReport;
import com.calidad.sosfidoapp.sosfido.data.repositories.local.SessionManager;
import com.calidad.sosfidoapp.sosfido.presentacion.activies.RegisterActivity;
import com.calidad.sosfidoapp.sosfido.presentacion.adapters.AdoptionRecyclerAdapter;
import com.calidad.sosfidoapp.sosfido.presentacion.contracts.AdoptionsContract;
import com.calidad.sosfidoapp.sosfido.presentacion.presenters.AdoptionsPresesenterImpl;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class AdoptionsFragment extends Fragment implements AdoptionsContract.View {

    private Unbinder unbinder;
    @BindView(R.id.recyclerViewA) RecyclerView recyclerView;
    @BindView(R.id.empty) TextView emptyView;
    @BindView(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefresh;
    private AdoptionsContract.Presenter presenter;
    public LinearLayoutManager layoutManager;
    public AdoptionRecyclerAdapter adapter;
    public final int CODE_REGISTER_REPORT = 140;
    public SessionManager sessionManager;



    public static AdoptionsFragment newInstance(String param1, String param2) {
        return new AdoptionsFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_adoptions, container, false);
        unbinder = ButterKnife.bind(this, root);
        sessionManager = new SessionManager(getContext());
        //presenter
        presenter = new AdoptionsPresesenterImpl(getContext(), this);
        // recyclerview
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        //swipeRefreshLayout
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.start();
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        presenter.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    @Override
    public void initRecyclerView(List<ResponseReport.ReportListAdoption> reportListAdoptionList) {

        adapter = new AdoptionRecyclerAdapter(getContext(),filterList(reportListAdoptionList));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private List<ResponseReport.ReportListAdoption> filterList(List<ResponseReport.ReportListAdoption> reportListAdoptionList) {
    List<ResponseReport.ReportListAdoption> list = new ArrayList<>();
    for(int i=0;i<reportListAdoptionList.size();i++){
        Log.i("ID ",String.valueOf(reportListAdoptionList.get(i).getOwner().getId())+" "+sessionManager.getPersonEntity().getId());
        if(reportListAdoptionList.get(i).getOwner().getId()!=sessionManager.getPersonEntity().getId()){
            list.add(reportListAdoptionList.get(i));
        }
    }
    return list;
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





}
