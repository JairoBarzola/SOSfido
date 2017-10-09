package com.calidad.sosfidoapp.sosfido.Presentacion.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.calidad.sosfidoapp.sosfido.R;
import com.calidad.sosfidoapp.sosfido.Utils.CustomBottomSheetDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jairbarzola on 27/09/17.
 */

public class RegisterFragment extends Fragment {

    @BindView(R.id.fab_photo_animal) ImageButton fabPhotoanimal;

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

    @OnClick(R.id.fab_photo_animal)
    void buttonOnClicked(){
        Bundle bundle = new Bundle();
        bundle.putInt("IdFragment",1);
        CustomBottomSheetDialogFragment fragment = new CustomBottomSheetDialogFragment();
        fragment.setArguments( bundle );
        fragment.show( getActivity().getSupportFragmentManager(), CustomBottomSheetDialogFragment.FRAGMENT_KEY );
    }
    public void openCamera(){
        Toast.makeText(getContext(),"CameraR",Toast.LENGTH_SHORT).show();
    }
    public void openGallery(){
        Toast.makeText(getContext(),"GalleryR",Toast.LENGTH_SHORT).show();
    }


}
