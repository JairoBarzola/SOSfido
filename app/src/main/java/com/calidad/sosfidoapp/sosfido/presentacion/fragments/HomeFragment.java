package com.calidad.sosfidoapp.sosfido.presentacion.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.calidad.sosfidoapp.sosfido.data.entities.ReportEntity;
import com.calidad.sosfidoapp.sosfido.data.entities.ResponseReport;
import com.calidad.sosfidoapp.sosfido.presentacion.activies.HomeActivity;
import com.calidad.sosfidoapp.sosfido.presentacion.contracts.HomeContract;
import com.calidad.sosfidoapp.sosfido.presentacion.presenters.HomePresenterImpl;
import com.calidad.sosfidoapp.sosfido.R;
import com.calidad.sosfidoapp.sosfido.utils.ProgressDialogCustom;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class HomeFragment extends Fragment implements OnMapReadyCallback, HomeContract.View,
        GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnInfoWindowClickListener {

    @BindView(R.id.mv_show_reports)
    MapView mapView;
    private GoogleMap googleMap;
    private ProgressDialogCustom mProgressDialogCustom;
    private HomeContract.Presenter presenter;
    private double latitude;
    private double longitude;
    private Unbinder unbinder;
    private static int CODE_LOCATION = 123;
    private static int ZOOM=16;
    private static  int SIZE_MARKER=95;
    private static  int MIN_TIME = 1200;
    private  static int MIN_DISTANCE =0;
    private List<ResponseReport.ReportListAdoption> reportListAdoptionsInfo;
    private List<ResponseReport.ReportList> reportListsAbandonedInfo;
    private List<ResponseReport.ReportListMissing> reportListsMissingInfo;
    private HashMap<Marker, ReportEntity> hashMapReport = new HashMap<Marker, ReportEntity>();

    private Timer timer;

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
        int delay = 5000; // delay for 0 sec.
        int period = 15000; // repeat every 10 sec.
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                presenter.loadReports();
            }
        }, delay, period);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setOnMyLocationButtonClickListener(this);
        googleMap.setOnInfoWindowClickListener(this);
        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View v = getActivity().getLayoutInflater().inflate(R.layout.info_marker,null);
                ImageView imageView = v.findViewById(R.id.image_animal);
                TextView textView = v.findViewById(R.id.text);
                TextView name = v.findViewById(R.id.pet_name);
                ReportEntity data = hashMapReport.get(marker);
                if(data!=null) {
                    textView.setText(data.getLocation());
                    name.setText(data.getNamePet());
                    if(data.getPhoto().equals("Sin imagen")){
                        Picasso.with(v.getContext()).load("http://sosfido.tk/media/photos/users/profile/3b00f90e-cda.jpg").into(imageView);
                    }else{
                    Picasso.with(v.getContext()).load(data.getPhoto()).into(imageView);}
                }
                return v;
            }
        });
        presenter.loadReports();
        myUbication();
    }

    private void myUbication() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, CODE_LOCATION);
        } else {
            LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            final Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            actualizarUbicacion(location);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME,MIN_DISTANCE,locListener);
            googleMap.setMyLocationEnabled(true);
        }
    }
    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {

        if( requestCode== CODE_LOCATION){
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0) {
                    // Permission granted.
                    myUbication();
                } else {
                    // User refused to grant permission. You can add AlertDialog here
                    askForPermission();
                }
            }
    }

    private void askForPermission() {
        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, CODE_LOCATION);
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
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        timer.purge();
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

        List<ReportEntity> reportEntityList = convertOneList(reportListAdoptions,reportListsAbandoned,reportListsMissing);


        Bitmap abandoned = Bitmap.createScaledBitmap (BitmapFactory.decodeResource(getResources(),
                R.drawable.verde),95,95, false);
        Bitmap lost = Bitmap.createScaledBitmap (BitmapFactory.decodeResource(getResources(),
                R.drawable.naranja),95,95, false);
        Bitmap adoption = Bitmap.createScaledBitmap (BitmapFactory.decodeResource(getResources(),
                R.drawable.plomo),95,95, false);
        for (ReportEntity entity: reportEntityList){
            if(!entity.getLatitude().equals("")||!entity.getLongitude().equals("")){
            LatLng latLng = new LatLng(Double.parseDouble(entity.getLatitude()),Double.parseDouble(entity.getLongitude()));

            if(entity.getTypeReport().equals("1")){
                hashMapReport.put(googleMap.addMarker(new MarkerOptions().position(latLng).title(entity.getIdReport())
                        .icon(BitmapDescriptorFactory.fromBitmap(lost))),entity);
            }else{
                if(entity.getTypeReport().equals("2")) {
                    hashMapReport.put(googleMap.addMarker(new MarkerOptions().position(latLng).title(entity.getIdReport())
                            .icon(BitmapDescriptorFactory.fromBitmap(abandoned))),entity);
                }else{
                    hashMapReport.put(googleMap.addMarker(new MarkerOptions().position(latLng).title(entity.getIdReport())
                            .icon(BitmapDescriptorFactory.fromBitmap(adoption))),entity);
                }
            }

        }
        }

        mapView.onResume();
    }

    private List<ReportEntity> convertOneList(List<ResponseReport.ReportListAdoption> reportListAdoptions, List<ResponseReport.ReportList> reportListsAbandoned, List<ResponseReport.ReportListMissing> reportListsMissing) {
        List<ReportEntity> reportList = new ArrayList<>();
        for (ResponseReport.ReportListAdoption entity: reportListAdoptions){
            reportList.add(new ReportEntity(entity.getId(),entity.getOwner().getAddress().getLocation(),
                    entity.getOwner().getAddress().getLatitude(),entity.getOwner().getAddress().getLongitude(),
                    entity.getDate(),entity.getAdoptionImage(),entity.getPetName(),entity.getDescription(),"3"));
        }
        for (ResponseReport.ReportListMissing entity: reportListsMissing){
            reportList.add(new ReportEntity(entity.getId(),entity.getPlace().getLocation(),entity.getPlace().getLatitude(),
                    entity.getPlace().getLongitude(),entity.getDate(),entity.getReport_image(),entity.getPetName(),entity.getDescription(),"1"));
        }
        for (ResponseReport.ReportList entity: reportListsAbandoned){
            reportList.add(new ReportEntity(entity.getId(),entity.getPlace().getLocation(),entity.getPlace().getLatitude(),entity.getPlace().getLongitude(),
                    entity.getDate(),entity.getReportImage(),"Abandonado",entity.getDescription(),"2"));
        }
        return reportList;
    }

    public void loadMap(){
        mapView.getMapAsync(this);
    }

    //control del gps
    LocationListener locListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            // mover market real time ActualizarUbicacion(location);/ setLocation(location);
        }
        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
            //nothing
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
    private void actualizarUbicacion(Location location) {
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            mCamera(latitude, longitude);
        }
    }

    private void mCamera(double latitude, double longitude) {
        LatLng coordenadas = new LatLng(latitude, longitude);
        CameraUpdate myUbication = CameraUpdateFactory.newLatLngZoom(coordenadas, ZOOM);
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
