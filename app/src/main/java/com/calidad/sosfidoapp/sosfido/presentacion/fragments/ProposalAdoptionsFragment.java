package com.calidad.sosfidoapp.sosfido.presentacion.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.calidad.sosfidoapp.sosfido.R;
import com.calidad.sosfidoapp.sosfido.data.entities.MyProposalAdoptionsEntity;
import com.calidad.sosfidoapp.sosfido.presentacion.adapters.MyAdoptionRecyclerAdapter;
import com.calidad.sosfidoapp.sosfido.presentacion.contracts.ProposalAdoptionsContract;
import com.calidad.sosfidoapp.sosfido.presentacion.presenters.ProposalAdoptionsPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ProposalAdoptionsFragment extends Fragment  implements ProposalAdoptionsContract.View{


    private Unbinder unbinder;
    @BindView(R.id.recyclerViewM) RecyclerView recyclerView;
    @BindView(R.id.empty) TextView emptyView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefresh;
    private ProposalAdoptionsContract.Presenter presenter;
    public LinearLayoutManager layoutManager;
    public MyAdoptionRecyclerAdapter adapter;


    public static ProposalAdoptionsFragment newInstance(String param1, String param2) {
        return new ProposalAdoptionsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_proposal_adoptions, container, false);
        unbinder = ButterKnife.bind(this, root);
        //presenter
        presenter = new ProposalAdoptionsPresenter(getContext(), this);
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
    public void initRecyclerView(List<MyProposalAdoptionsEntity> reportlist) {

        adapter = new MyAdoptionRecyclerAdapter(getContext(),reportlist);
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
    void showDialog(String title,String message){
        message(title,message).show();
    }

    public AlertDialog message (String tille, String message){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle(tille);
        dialog.setMessage(message);
        dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        return dialog.create();
    }
    public void load(){
        presenter.start();
    }

}
