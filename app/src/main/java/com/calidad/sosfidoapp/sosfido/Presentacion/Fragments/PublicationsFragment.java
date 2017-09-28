package com.calidad.sosfidoapp.sosfido.Presentacion.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.calidad.sosfidoapp.sosfido.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by jairbarzola on 28/09/17.
 */

public class PublicationsFragment extends Fragment {

    Unbinder unbinder;
    public PublicationsFragment() {
    }

    public static PublicationsFragment newInstance() {
        return new PublicationsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_publications, container, false);
        unbinder=ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
