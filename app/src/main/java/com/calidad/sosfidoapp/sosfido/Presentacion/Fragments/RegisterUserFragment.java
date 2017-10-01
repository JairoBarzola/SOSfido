package com.calidad.sosfidoapp.sosfido.Presentacion.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.calidad.sosfidoapp.sosfido.R;

import butterknife.ButterKnife;

/**
 * Created by jairbarzola on 29/09/17.
 */

public class RegisterUserFragment extends Fragment {


    public RegisterUserFragment() {}
    public static RegisterUserFragment newInstance() {return new RegisterUserFragment();}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_register_user, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


}