package com.calidad.sosfidoapp.sosfido.Presentacion.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.calidad.sosfidoapp.sosfido.Data.Entities.ReportEntity;
import com.calidad.sosfidoapp.sosfido.Data.Entities.ResponseReport;
import com.calidad.sosfidoapp.sosfido.Presentacion.Activies.PublicationsActivity;
import com.calidad.sosfidoapp.sosfido.Presentacion.Contracts.ReportContract;
import com.calidad.sosfidoapp.sosfido.Presentacion.Presenters.PublicationsPresenterImpl;
import com.calidad.sosfidoapp.sosfido.Presentacion.ReportRecyclerAdapter;
import com.calidad.sosfidoapp.sosfido.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by jairbarzola on 28/09/17.
 */

public class PublicationsFragment extends Fragment implements ReportContract.View {

    Unbinder unbinder;
    @BindView(R.id.recyclerViewP)
    RecyclerView recyclerView;
    @BindView(R.id.empty)
    TextView empty;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    ReportContract.Presenter presenter;
    private LinearLayoutManager layoutManager;
    ReportRecyclerAdapter adapter;
    public PublicationsFragment() {
    }

    public static PublicationsFragment newInstance() {
        return new PublicationsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_publications, container, false);
        unbinder=ButterKnife.bind(this, root);
        //presenter
        presenter = new PublicationsPresenterImpl(getContext(),this);

        // recyclerview
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        //swipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.start();
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setInitRecycler(List<ResponseReport.ReportList> reportListsAbandoned, List<ResponseReport.ReportListMissing> reportListsMissing, List<ResponseReport.ReportListAdoption> reportListAdoption) {

        List<ReportEntity> list = convertOneList(reportListAdoption,reportListsAbandoned,reportListsMissing);
        if(list!=null){
            hideEmpty();
        adapter = new ReportRecyclerAdapter(getContext(),convertOneList(reportListAdoption,reportListsAbandoned,reportListsMissing));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        }else{
            showEmpty();
        }
    }

    private List<ReportEntity> convertOneList(List<ResponseReport.ReportListAdoption> reportListAdoption, List<ResponseReport.ReportList> reportListsAbandoned, List<ResponseReport.ReportListMissing> reportListsMissing) {
        List<ReportEntity> reportList = new ArrayList<>();
        for (ResponseReport.ReportListAdoption entity: reportListAdoption){
            reportList.add(new ReportEntity(entity.getId(),entity.getOwner().getAddress().getLocation(),
                    entity.getOwner().getAddress().getLatitude(),entity.getOwner().getAddress().getLongitude(),
                    entity.getDate(),entity.getAdoption_image(),entity.getPet_name(),entity.getDescription(),"3"));
        }
        for (ResponseReport.ReportListMissing entity: reportListsMissing){
            reportList.add(new ReportEntity(entity.getId(),entity.getPlace().getLocation(),entity.getPlace().getLatitude(),
                    entity.getPlace().getLongitude(),entity.getDate(),entity.getReport_image(),entity.getPet_name(),entity.getDescription(),"1"));
        }
        for (ResponseReport.ReportList entity: reportListsAbandoned){
            reportList.add(new ReportEntity(entity.getId(),entity.getPlace().getLocation(),entity.getPlace().getLatitude(),entity.getPlace().getLongitude(),
                    entity.getDate(),entity.getReport_image(),"Abandonado",entity.getDescription(),"2"));
        }
        return reportList;
    }

    @Override
    public void showSwipeLayout() {
        swipeRefreshLayout.setRefreshing(true);

    }

    @Override
    public void hideSWipeLayout() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showEmpty() {
        recyclerView.setVisibility(View.GONE);
        empty.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmpty() {
        recyclerView.setVisibility(View.VISIBLE);
        empty.setVisibility(View.GONE);
    }

    @Override
    public void setMessageError(String error) {
        ((PublicationsActivity)getActivity()).showMessageError(error);
    }


}
