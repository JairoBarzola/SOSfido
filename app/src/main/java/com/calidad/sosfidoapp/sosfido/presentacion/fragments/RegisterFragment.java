package com.calidad.sosfidoapp.sosfido.presentacion.fragments;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.calidad.sosfidoapp.sosfido.data.repositories.Local.SessionManager;
import com.calidad.sosfidoapp.sosfido.presentacion.activies.RegisterActivity;
import com.calidad.sosfidoapp.sosfido.presentacion.contracts.RegisterContract;
import com.calidad.sosfidoapp.sosfido.presentacion.presenters.RegisterPresenterImpl;
import com.calidad.sosfidoapp.sosfido.R;
import com.calidad.sosfidoapp.sosfido.utils.CustomBottomSheetDialogFragment;
import com.calidad.sosfidoapp.sosfido.utils.ProgressDialogCustom;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.io.ByteArrayOutputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Jair Barzola on 22-Oct-17.
 */

public class RegisterFragment extends Fragment implements RegisterContract.View, Validator.ValidationListener {

    @BindView(R.id.fab_photo_animal)
    ImageButton fabPhotoanimal;
    @BindView(R.id.image_animal)
    CircleImageView photoAnimal;
    @NotEmpty(message = "Este campo no puede ser vacío")
    @BindView(R.id.edt_name_animal)
    EditText edtName;
    @NotEmpty(message = "Este campo no puede ser vacío")
    @BindView(R.id.edt_address_report)
    EditText edtAddress;
    @NotEmpty(message = "Este campo no puede ser vacío")
    @BindView(R.id.edt_number_report)
    EditText edtNumber;
    @NotEmpty(message = "Este campo no puede ser vacío")
    @BindView(R.id.edt_descrip_report)
    EditText edtDescrip;
    @BindView(R.id.ln_name)
    LinearLayout lnName;
    @BindView(R.id.ln_phone)
    LinearLayout lnPhone;
    @BindView(R.id.ln_description)
    LinearLayout lnDescrip;
    @BindView(R.id.ln_address)
    LinearLayout lnAddress;
    private static final int GALLERY_CODE = 5;
    private static final int CAMERA_CODE = 1888;
    private static final Integer CAMERA = 0x5;
    private String imageBase64;
    private ProgressDialogCustom mProgressDialogCustom;
    private RegisterContract.Presenter presenter;
    private Validator validator;
    private final int REQUEST_CODE_PLACEPICKER = 1;
    private String location;
    private String longitude;
    private String latitud;
    private SessionManager sessionManager;
    private int idReport;

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
        sessionManager = new SessionManager(getContext());
        Bundle bundle = getArguments();
        idReport = bundle.getInt("idReport");
        openReport(idReport);

        presenter = new RegisterPresenterImpl(this, getContext());
        askForPermission(android.Manifest.permission.CAMERA, CAMERA);
        mProgressDialogCustom = new ProgressDialogCustom(getActivity(), "Registrando...");
        return root;
    }

    private void openReport(int id) {
        //perdido
        if (id == 1) {
            lnName.setVisibility(View.VISIBLE);
            lnPhone.setVisibility(View.GONE);
            lnAddress.setVisibility(View.VISIBLE);
            lnDescrip.setVisibility(View.VISIBLE);
        }
        //abandonado
        else if (id == 2) {
            lnName.setVisibility(View.GONE);
            lnPhone.setVisibility(View.GONE);
            lnAddress.setVisibility(View.VISIBLE);
            lnDescrip.setVisibility(View.VISIBLE);
        } else {
            //adopcion
            lnName.setVisibility(View.VISIBLE);
            lnPhone.setVisibility(View.GONE);
            lnAddress.setVisibility(View.GONE);
            lnDescrip.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void askForPermission(String permission, Integer code) {
        if (ContextCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, code);
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, code);
            }
        }
    }


    @OnClick(R.id.fab_photo_animal)
    void buttonOnClicked() {
        Bundle bundle = new Bundle();
        bundle.putInt("IdFragment", 1);
        CustomBottomSheetDialogFragment fragment = new CustomBottomSheetDialogFragment();
        fragment.setArguments(bundle);
        fragment.show(getActivity().getSupportFragmentManager(), CustomBottomSheetDialogFragment.FRAGMENT_KEY);
    }

    public void openCamera() {
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(i, CAMERA_CODE);
        } else {
            askForPermission(android.Manifest.permission.CAMERA, CAMERA);
        }


    }

    public void openGallery() {
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
                Bitmap bitmapGallery = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                //Bitmap resizedImageGallery = Bitmap.createScaledBitmap(bitmapGallery, (int) (bitmapGallery.getWidth() * 0.2), (int) (bitmapGallery.getHeight() * 0.2), false);
                Bitmap resizedImageGallery = Bitmap.createScaledBitmap(bitmapGallery, 600, 600, false);
                imageBase64 = convertBitmapToBASE64(resizedImageGallery);
                photoAnimal.setImageBitmap(resizedImageGallery);
            } catch (Exception e) {
                setMessageError("error");
            }
        }
        if (requestCode == CAMERA_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Bitmap bitmapCamera = (Bitmap) data.getExtras().get("data");
                Bitmap resizedImageGallery = Bitmap.createScaledBitmap(bitmapCamera, 600, 600, false);
                imageBase64 = convertBitmapToBASE64(resizedImageGallery);
                photoAnimal.setImageBitmap(resizedImageGallery);
            }
        }
        if (requestCode == REQUEST_CODE_PLACEPICKER && resultCode == Activity.RESULT_OK) {
            displaySelectedPlaceFromPlacePicker(data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(getActivity(), permissions[0]) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getActivity(), "Permiso concedido", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getActivity(), "Permiso denegado", Toast.LENGTH_SHORT).show();
        }
    }

    public String convertBitmapToBASE64(Bitmap path) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        path.compress(Bitmap.CompressFormat.JPEG, 100, bytes); //bm is the bitmap object
        byte[] b = bytes.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
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
        ((RegisterActivity) getActivity()).showMessage(error);
    }

    @Override
    public void setDialogMessage(String message) {
        ((RegisterActivity) getActivity()).showMessage(message);
    }

    void sendReport() {
        if (imageBase64 != null) {
            if (idReport == 1) {
                presenter.start(location, latitud, longitude, edtDescrip.getText().toString(),
                        imageBase64, edtName.getText().toString()
                        , edtNumber.getText().toString(), 1);
            } else if (idReport == 2) {
                presenter.start(location, latitud, longitude, edtDescrip.getText().toString(),
                        imageBase64, edtName.getText().toString()
                        , edtNumber.getText().toString(), 2);
            } else {
                presenter.start(sessionManager.getPersonEntity().getAddress().getLocation(), sessionManager.getPersonEntity().getAddress().getLatitude()
                        , sessionManager.getPersonEntity().getAddress().getLongitude(), edtDescrip.getText().toString(),
                        imageBase64, edtName.getText().toString()
                        , edtNumber.getText().toString(), 3);
            }

        } else {
            setDialogMessage("Por favor adjunta una foto");
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
    void onClickAddress(View view) {
        if (view.getId() == R.id.edt_address_report) {
            startPlacePickerActivity();
        }

    }

    private void startPlacePickerActivity() {
        PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
        // this would only work if you have your Google Places API working
        try {
            Intent intent = intentBuilder.build(getActivity());
            startActivityForResult(intent, REQUEST_CODE_PLACEPICKER);
        } catch (Exception e) {
            setMessageError("error");
        }

    }

    public void registerReport() {
        validator.validate();
    }

    public void backToHome() {
        ((RegisterActivity) getActivity()).callToHome();
    }

    private void displaySelectedPlaceFromPlacePicker(Intent data) {
        Place placeSelected = PlacePicker.getPlace(data, getActivity());
        location = placeSelected.getAddress().toString();
        edtAddress.setText(location);
        latitud = String.valueOf(placeSelected.getLatLng().latitude);
        longitude = String.valueOf(placeSelected.getLatLng().longitude);
    }
}