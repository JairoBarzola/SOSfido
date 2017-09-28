package com.calidad.sosfidoapp.sosfido.Presentacion.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.calidad.sosfidoapp.sosfido.R;

import butterknife.ButterKnife;

/**
 * Created by jairbarzola on 27/09/17.
 */

public class RegisterFragment extends Fragment {


    public RegisterFragment() {}
    public static RegisterFragment newInstance() {return new RegisterFragment();}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


}
