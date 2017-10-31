package com.calidad.sosfidoapp.sosfido.Presentacion.Fragments;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;
import android.widget.TextView;
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
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class HomeFragment extends Fragment implements OnMapReadyCallback, HomeContract.View,
        GoogleMap.OnMyLocationButtonClickListener,GoogleMap.OnInfoWindowClickListener {

    @BindView(R.id.mv_show_reports)
    MapView mapView;
    GoogleMap googleMap;
    private Marker myPosition;
    private ProgressDialogCustom mProgressDialogCustom;
    HomeContract.Presenter presenter;
    double latitude, longitude;
    private static int PETICION_PERMISO_LOCALIZACION = 101;
    Unbinder unbinder;
    List<ResponseReport.ReportListAdoption> reportListAdoptionsInfo;
    List<ResponseReport.ReportList> reportListsAbandonedInfo;
    List<ResponseReport.ReportListMissing> reportListsMissingInfo;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, root);
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
        googleMap.setOnMyLocationButtonClickListener(this);
        googleMap.setOnInfoWindowClickListener(this);
        /*googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                reportDialog(marker.getTitle());
                return false;
            }
        });*/
       googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View v = getLayoutInflater(getArguments()).inflate(R.layout.info_marker,null);
                ImageView imageView = (ImageView) v.findViewById(R.id.image_animal);
                TextView textView = (TextView) v.findViewById(R.id.text);

                String url_image = findUrl(marker.getTitle());
                //String location = findLocation(marker.getTitle());
               // String pet_name = findPetName(marker.getTitle());

               // Log.i("URL",reportListAdoptionsInfo.get(Integer.parseInt(marker.getTitle())).getAdoption_image());
                Picasso.with(v.getContext()).load(url_image).into(imageView);
                //textView.setText(reportListAdoptionsInfo.get(9).getPet_name());
                //Picasso.with(v.getContext()).load("http://sosfido.tk/media/photos/adoptions/pets/6c608a63-5e8.jpg").into(imageView);

                return v;
            }
        });
        presenter.loadReports();
        myUbication();

    }

    private String findUrl(String id) {

        int i=0;
        String url = "";
        boolean first=false;
        boolean second=false;
        boolean third = false;


        while(i<=reportListsAbandonedInfo.size()&&reportListAdoptionsInfo.size()!=0){
            if(reportListsAbandonedInfo.get(i).getId().equals(id)){
                url=reportListsAbandonedInfo.get(i).getReport_image();
                first=true;
                break;
            }
            i++;
        }

        if(first){
            return url;
        }else{
            i=0;
            while (i<=reportListsMissingInfo.size()&&reportListsMissingInfo.size()!=0){
                if(reportListsMissingInfo.get(i).getId().equals(id)){
                    url=reportListsMissingInfo.get(i).getReport_image();
                    second=true;
                    break;
                }
                i++;
            }
            if(second){
                return url;
            }else {
                i = 0;
                while (i <= reportListAdoptionsInfo.size()&&reportListsAbandonedInfo.size()!=0) {
                    if (reportListAdoptionsInfo.get(i).getId() .equals(id)) {
                        url = reportListAdoptionsInfo.get(i).getAdoption_image();
                        third=true;
                        break;
                    }
                    i++;
                }
                if(third){
                    return url;
                }else {
                    return "http://sosfido.tk/media/photos/users/profile/629df15f-e88.jpg";
                }
            }
        }
    }

    private  void reportDialog(String id) {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.info_marker);
        ImageView imageView = (ImageView) dialog.findViewById(R.id.image_animal);
        TextView textView = (TextView) dialog.findViewById(R.id.text);
        //Picasso.with(getContext()).load(reportListAdoptionsInfo.get(Integer.parseInt(marker.getTitle())).getAdoption_image()).into(imageView);
        Picasso.with(dialog.getContext()).load("http://sosfido.tk/media/photos/adoptions/pets/6c608a63-5e8.jpg").into(imageView);

        dialog.show();
    }

    private void myUbication() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PETICION_PERMISO_LOCALIZACION);
            return;
        } else {

            LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            final Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            ActualizarUbicacion(location);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1200,0,locListener);
            googleMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapView.onDestroy();
        unbinder.unbind();
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
    public void getReportsPoints(List<ResponseReport.ReportList> reportListsAbandoned, List<ResponseReport.ReportListMissing> reportListsMissing,
                                 List<ResponseReport.ReportListAdoption> reportListAdoptions){
        Bitmap abandoned = Bitmap.createScaledBitmap (BitmapFactory.decodeResource(getResources(),
                R.drawable.verde),95,95, false);
        reportListAdoptionsInfo= reportListAdoptions;
        reportListsAbandonedInfo=reportListsAbandoned;
        reportListsMissingInfo=reportListsMissing;
        for (ResponseReport.ReportList entity: reportListsAbandoned){
            LatLng latLng = new LatLng(Double.parseDouble(entity.getPlace().getLatitude()),Double.parseDouble(entity.getPlace().getLongitude()));
            googleMap.addMarker(new MarkerOptions().position(latLng).title(entity.getId())
            .icon(BitmapDescriptorFactory.fromBitmap(abandoned)));
        }
        Bitmap lost = Bitmap.createScaledBitmap (BitmapFactory.decodeResource(getResources(),
                R.drawable.naranja),95,95, false);
        for (ResponseReport.ReportListMissing entity: reportListsMissing){
            LatLng latLngM = new LatLng(Double.parseDouble(entity.getPlace().getLatitude()),Double.parseDouble(entity.getPlace().getLongitude()));
            googleMap.addMarker(new MarkerOptions().position(latLngM).title(entity.getId())
            .icon(BitmapDescriptorFactory.fromBitmap(lost)));
        }
        Bitmap adoption = Bitmap.createScaledBitmap (BitmapFactory.decodeResource(getResources(),
                R.drawable.plomo),95,95, false);
        for (ResponseReport.ReportListAdoption entity: reportListAdoptions){
            LatLng latLngA = new LatLng(Double.parseDouble(entity.getOwner().getAddress().getLatitude()),Double.parseDouble(entity.getOwner().getAddress().getLongitude()));
            googleMap.addMarker(new MarkerOptions().position(latLngA).title(entity.getId())
                    .icon(BitmapDescriptorFactory.fromBitmap(adoption)));
        }
        mapView.onResume();
    }

    public void loadMap(){
        mapView.getMapAsync(this);
     // presenter.loadReports();
    }

    //control del gps
    LocationListener locListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            // mover market real time
           // ActualizarUbicacion(location);
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
        public void onProviderDisabled(String s) {locationStart();message("GPS Desactivado");
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

    @Override
    public boolean onMyLocationButtonClick() {
        mCamera(latitude,longitude);
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
    //    Toast.makeText(getContext(),"Hola",Toast.LENGTH_SHORT).show();
    }
}
