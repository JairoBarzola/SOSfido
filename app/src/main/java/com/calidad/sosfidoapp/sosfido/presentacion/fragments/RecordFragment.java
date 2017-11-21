package com.calidad.sosfidoapp.sosfido.presentacion.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;

import com.calidad.sosfidoapp.sosfido.R;

import butterknife.ButterKnife;

/**
 * Created by jairbarzola on 28/09/17.
 */

public class RecordFragment extends Fragment {


    public static RecordFragment newInstance() {
        return new RecordFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_record, container, false);
        ButterKnife.bind(this, root);
        return root;
    }
}