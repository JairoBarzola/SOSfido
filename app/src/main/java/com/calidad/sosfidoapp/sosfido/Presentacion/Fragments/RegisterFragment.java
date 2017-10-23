package com.calidad.sosfidoapp.sosfido.Presentacion.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.calidad.sosfidoapp.sosfido.Presentacion.Activies.RegisterActivity;
import com.calidad.sosfidoapp.sosfido.Presentacion.Contracts.RegisterContract;
import com.calidad.sosfidoapp.sosfido.Presentacion.Presenters.RegisterPresenterImpl;
import com.calidad.sosfidoapp.sosfido.R;
import com.calidad.sosfidoapp.sosfido.Utils.CustomBottomSheetDialogFragment;
import com.calidad.sosfidoapp.sosfido.Utils.ProgressDialogCustom;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Jair Barzola on 22-Oct-17.
 */

public class RegisterFragment extends Fragment implements RegisterContract.View,Validator.ValidationListener{

    @BindView(R.id.fab_photo_animal) ImageButton fabPhotoanimal;
    @BindView(R.id.image_animal) CircleImageView photoAnimal;
    @NotEmpty(message = "Este campo no puede ser vacío")
    @BindView(R.id.edt_name_animal) EditText edtName;
    @NotEmpty(message = "Este campo no puede ser vacío")
    @BindView(R.id.edt_address_report) EditText edtAddress;
    @NotEmpty(message = "Este campo no puede ser vacío")
    @BindView(R.id.edt_number_report) EditText edtNumber;
    @NotEmpty(message = "Este campo no puede ser vacío")
    @BindView(R.id.edt_descrip_report) EditText edtDescrip;
    private static final int GALLERY_CODE = 5;
    private static final int STORAGE_PERMISSION_CODE = 123;
    private static final int CAMERA_CODE = 1888;
    static final Integer CAMERA = 0x5;
    public String imageBase64;
    private ProgressDialogCustom mProgressDialogCustom;
    RegisterContract.Presenter presenter;
    Validator validator;

    String location;
    String longitude;
    String latitud;
    public RegisterFragment() {
    }

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, root);
        validator = new Validator(this);
        validator.setValidationListener(this);
        presenter = new RegisterPresenterImpl(this,getContext());
        askForPermission(android.Manifest.permission.CAMERA,CAMERA);
        mProgressDialogCustom = new ProgressDialogCustom(getActivity(),"Registrando...");
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    private void askForPermission(String permission,Integer code) {
        if (ContextCompat.checkSelfPermission( getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale( getActivity(), permission)) {
                ActivityCompat.requestPermissions( getActivity(), new String[]{permission}, code);
            } else {
                ActivityCompat.requestPermissions( getActivity(), new String[]{permission}, code);
            }
        }
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
        if(ContextCompat.checkSelfPermission( getActivity(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(i,CAMERA_CODE);
        }else{
            askForPermission(android.Manifest.permission.CAMERA,CAMERA);
        }


    }
    public void openGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), GALLERY_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_CODE && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                Bitmap bitmapGallery = MediaStore.Images.Media.getBitmap( getActivity().getContentResolver(), filePath);
                Bitmap resizedImageGallery = Bitmap.createScaledBitmap(bitmapGallery, (int) (bitmapGallery.getWidth() * 0.8), (int) (bitmapGallery.getHeight() * 0.8), false);
                imageBase64=convertBitmapToBASE64(resizedImageGallery);
                photoAnimal.setImageBitmap(resizedImageGallery);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == CAMERA_CODE ){
            if (resultCode == Activity.RESULT_OK) {
                Bitmap bitmapCamera = (Bitmap) data.getExtras().get("data");
                Bitmap resizedImageCamera = Bitmap.createScaledBitmap(bitmapCamera, (int) (bitmapCamera.getWidth() * 0.8), (int) (bitmapCamera.getHeight() * 0.8), false);
                imageBase64=convertBitmapToBASE64(resizedImageCamera);
                photoAnimal.setImageBitmap(bitmapCamera);
            } else {
                Toast.makeText( getActivity(), "Vuelva a tomar la foto", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(ActivityCompat.checkSelfPermission( getActivity(), permissions[0]) == PackageManager.PERMISSION_GRANTED){
            Toast.makeText( getActivity(), "Permiso concedido", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText( getActivity(), "Permiso denegado", Toast.LENGTH_SHORT).show();
        }
    }

    public String convertBitmapToBASE64(Bitmap path){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        path.compress(Bitmap.CompressFormat.JPEG, 100, bytes); //bm is the bitmap object
        byte[] b = bytes.toByteArray();
        Log.i("BASE 64", "" + Base64.encodeToString(b, Base64.DEFAULT));

        return Base64.encodeToString(b, Base64.DEFAULT);
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
        ((RegisterActivity)getActivity()).showMessage(error);
    }

    @Override
    public void setDialogMessage(String message) {
        ((RegisterActivity)getActivity()).showMessage(message);
    }

    void sendReport(){
        if(imageBase64!= null){
            presenter.start(location,latitud,longitude,edtDescrip.getText().toString(),
                    imageBase64,edtName.getText().toString()
                            ,edtNumber.getText().toString());
        }else{
            setDialogMessage("Por favor tome una foto");
        }

    }

    @Override
    public void onValidationSucceeded() {
        sendReport();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getActivity());
            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else if (view instanceof TextView) {
                ((TextView) view).setError(message);
            } else {
                setMessageError(message);
            }
        }
    }

    @OnClick(R.id.edt_address_report)
    void OnClickAddress(View view){

        switch (view.getId()){
            case R.id.edt_address_report:
                Toast.makeText(getContext(),"Address",Toast.LENGTH_SHORT).show();
                break;
        }

    }

    public void registerReport(){
        validator.validate();
    }

    public void backToHome(){
        ((RegisterActivity)getActivity()).callToHome();
    }

}