package com.calidad.sosfidoapp.sosfido.Presentacion.Fragments;

import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.calidad.sosfidoapp.sosfido.Data.Entities.PersonEntity;
import com.calidad.sosfidoapp.sosfido.Presentacion.Contracts.ProfileContract;
import com.calidad.sosfidoapp.sosfido.Presentacion.Presenters.ProfilePresenterImpl;
import com.calidad.sosfidoapp.sosfido.R;
import com.calidad.sosfidoapp.sosfido.Utils.CustomBottomSheetDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by jairbarzola on 28/09/17.
 */

public class ProfileFragment extends Fragment implements ProfileContract.View {

    @BindView(R.id.tv_name) TextView tvName;
    @BindView(R.id.tv_birth_date) TextView tvBirthDate;
    @BindView(R.id.tv_direccion) TextView tvDireccion;
    @BindView(R.id.tv_email) TextView tvEmail;
    @BindView(R.id.tv_phone) TextView tvPhone;
    @BindView(R.id.cardView) CardView cardView;
    @BindView(R.id.fab_camera) ImageButton fabCamera;

    ProfileContract.Presenter presenter;
    Unbinder unbinder;
    public ProfileFragment() {
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder=ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new ProfilePresenterImpl(this,getContext());
        presenter.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void loadUser(PersonEntity personEntity) {
        tvName.setText(personEntity.getUser().getFirst_name()+" "+personEntity.getUser().getLast_name());
        tvBirthDate.setText(personEntity.getBorn_date());
        tvDireccion.setText(personEntity.getAddress());
        tvEmail.setText(personEntity.getUser().getEmail());
        tvPhone.setText(personEntity.getPhone_number());
    }

    @Override
    public void onResume() {
        super.onResume();
        this.init();
    }

    private void init() {

    }
    @OnClick(R.id.fab_camera)
    void onClickButton(){
        Bundle bundle = new Bundle();
        bundle.putInt("IdFragment",2);
        CustomBottomSheetDialogFragment fragment = new CustomBottomSheetDialogFragment();
        fragment.setArguments( bundle );
        fragment.show( getActivity().getSupportFragmentManager(), CustomBottomSheetDialogFragment.FRAGMENT_KEY );
    }
    public void openCamera(){
        Toast.makeText(getContext(),"CameraP",Toast.LENGTH_SHORT).show();
    }
    public void openGallery(){
        Toast.makeText(getContext(),"GalleryP",Toast.LENGTH_SHORT).show();
    }
}