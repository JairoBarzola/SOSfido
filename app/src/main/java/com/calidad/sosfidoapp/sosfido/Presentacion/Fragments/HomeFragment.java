package com.calidad.sosfidoapp.sosfido.Presentacion.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.calidad.sosfidoapp.sosfido.Presentacion.Activies.HomeActivity;
import com.calidad.sosfidoapp.sosfido.Presentacion.Activies.RegisterActivity;
import com.calidad.sosfidoapp.sosfido.Presentacion.Contracts.HomeContract;
import com.calidad.sosfidoapp.sosfido.R;
import com.calidad.sosfidoapp.sosfido.Utils.ProgressDialogCustom;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeFragment extends Fragment implements OnMapReadyCallback,HomeContract.View{

    @BindView(R.id.mv_show_reports)
    MapView mapView;
    GoogleMap googleMap;
    private ProgressDialogCustom mProgressDialogCustom;
    public HomeFragment() {}
    public static HomeFragment newInstance() {return new HomeFragment();}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, root);
        mProgressDialogCustom = new ProgressDialogCustom(getActivity(),"Actualizando...");
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(-12.0560204,-77.0866113))
                .zoom(15)
                .build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mapView.onResume();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if(mProgressDialogCustom!=null){
            if(active){
                mProgressDialogCustom.show();
            }
            else{
                mProgressDialogCustom.dismiss();
            }
        }
    }

    @Override
    public void setMessageError(String error) {
        ((HomeActivity)getActivity()).showMessageError(error);
    }

    @Override
    public void setDialogMessage(String message) {
        ((HomeActivity)getActivity()).showMessageError(message);
    }
}
