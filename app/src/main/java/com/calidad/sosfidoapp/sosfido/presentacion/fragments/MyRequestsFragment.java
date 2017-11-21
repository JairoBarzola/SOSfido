package com.calidad.sosfidoapp.sosfido.presentacion.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.calidad.sosfidoapp.sosfido.R;
import com.calidad.sosfidoapp.sosfido.data.entities.MyRequestEntity;
import com.calidad.sosfidoapp.sosfido.presentacion.adapters.MyRequestRecyclerAdapter;
import com.calidad.sosfidoapp.sosfido.presentacion.contracts.MyRequestContract;
import com.calidad.sosfidoapp.sosfido.presentacion.presenters.MyRequestPresenterImpl;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyRequestsFragment extends Fragment implements MyRequestContract.View {


    private Unbinder unbinder;
    @BindView(R.id.recyclerViewR) RecyclerView recyclerView;
    @BindView(R.id.empty) TextView emptyView;
    @BindView(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefresh;
    public LinearLayoutManager layoutManager;
    public MyRequestRecyclerAdapter adapter;
    public MyRequestContract.Presenter presenter;

    public static MyRequestsFragment newInstance() {
        return new MyRequestsFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my_adoptions, container, false);
        unbinder = ButterKnife.bind(this, root);
        //presenter
        presenter = new MyRequestPresenterImpl(getContext(),this);
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
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        presenter.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    @Override
    public void initRecyclerView(List<MyRequestEntity> requestsEntityList) {

        adapter = new MyRequestRecyclerAdapter(getContext(),requestsEntityList);
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

}
