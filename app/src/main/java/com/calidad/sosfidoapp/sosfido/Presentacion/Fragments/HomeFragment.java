package com.calidad.sosfidoapp.sosfido.Presentacion.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.calidad.sosfidoapp.sosfido.Data.Entities.ResponseReport;
import com.calidad.sosfidoapp.sosfido.Presentacion.Activies.HomeActivity;
import com.calidad.sosfidoapp.sosfido.Presentacion.Activies.RegisterActivity;
import com.calidad.sosfidoapp.sosfido.Presentacion.Contracts.HomeContract;
import com.calidad.sosfidoapp.sosfido.Presentacion.Presenters.HomePresenterImpl;
import com.calidad.sosfidoapp.sosfido.R;
import com.calidad.sosfidoapp.sosfido.Utils.GPSTracker;
import com.calidad.sosfidoapp.sosfido.Utils.ProgressDialogCustom;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeFragment extends Fragment implements OnMapReadyCallback, HomeContract.View {

    @BindView(R.id.mv_show_reports)
    MapView mapView;
    GoogleMap googleMap;
    private Marker myPosition;
    private ProgressDialogCustom mProgressDialogCustom;
    HomeContract.Presenter presenter;
    double latitude, longitude;
    private static int PETICION_PERMISO_LOCALIZACION = 101;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, root);
        mProgressDialogCustom = new ProgressDialogCustom(getActivity(), "Actualizando...");
        presenter = new HomePresenterImpl(this, getContext());
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
        this.googleMap = googleMap;

        presenter.loadReports();
        myUbication();

    }

    private void myUbication() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PETICION_PERMISO_LOCALIZACION);
            return;
        } else {
            LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            ActualizarUbicacion(location);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1200,0,locListener);
        }
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
        if (mProgressDialogCustom != null) {
            if (active) {
                mProgressDialogCustom.show();
            } else {
                mProgressDialogCustom.dismiss();
            }
        }
    }

    @Override
    public void setMessageError(String error) {
        ((HomeActivity) getActivity()).showMessageError(error);
    }

    @Override
    public void setDialogMessage(String message) {
        ((HomeActivity) getActivity()).showMessageError(message);
    }

    @Override
    public void getReportsPoints(List<ResponseReport.ReportList> reportLists) {
        for (ResponseReport.ReportList entity: reportLists){
            LatLng latLng = new LatLng(Double.parseDouble(entity.getPlace().getLatitude()),Double.parseDouble(entity.getPlace().getLongitude()));
            googleMap.addMarker(new MarkerOptions().position(latLng).title(entity.getDescription()));
        }
        mapView.onResume();
    }

    public void loadMap(){
        mapView.getMapAsync(this);
     //   presenter.loadReports();
    }


    //control del gps
    LocationListener locListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {

            ActualizarUbicacion(location);
           // setLocation(location);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {
            message("GPS Activado");
        }

        @Override
        public void onProviderDisabled(String s) {
            locationStart();
            message("GPS Desactivado");
        }
    };

    private void message(String s) {
        Toast.makeText(getContext(),s,Toast.LENGTH_SHORT).show();
    }

    //actualizar la ubicacion
    private void ActualizarUbicacion(Location location) {
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            mCamera(latitude, longitude);
        }
    }

    private void mCamera(double latitude, double longitude) {
        LatLng coordenadas = new LatLng(latitude, longitude);
        CameraUpdate myUbication = CameraUpdateFactory.newLatLngZoom(coordenadas, 16);
        if (myPosition != null) myPosition.remove();
        myPosition = googleMap.addMarker(new MarkerOptions()
                .position(coordenadas)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin)));
        googleMap.animateCamera(myUbication);
    }
    //activar los servicios del gps cuando esten apagados
    public void locationStart() {
        LocationManager mlocManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }

    }


}
