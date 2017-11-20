package com.calidad.sosfidoapp.sosfido.presentacion.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.view.*;
import android.widget.*;

import com.calidad.sosfidoapp.sosfido.data.entities.PersonEntity;
import com.calidad.sosfidoapp.sosfido.data.repositories.local.SessionManager;
import com.calidad.sosfidoapp.sosfido.presentacion.activies.ProfileActivity;
import com.calidad.sosfidoapp.sosfido.presentacion.contracts.ProfileContract;
import com.calidad.sosfidoapp.sosfido.presentacion.presenters.ProfilePresenterImpl;
import com.calidad.sosfidoapp.sosfido.R;
import com.calidad.sosfidoapp.sosfido.utils.CustomBottomSheetDialogFragment;
import com.calidad.sosfidoapp.sosfido.utils.ProgressDialogCustom;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jairbarzola on 28/09/17.
 */

public class ProfileFragment extends Fragment implements ProfileContract.View {

    @BindView(R.id.tv_name)TextView tvName;
    @BindView(R.id.tv_birth_date)TextView tvBirthDate;
    @BindView(R.id.tv_direccion)TextView tvDireccion;
    @BindView(R.id.tv_email) TextView tvEmail;
    @BindView(R.id.tv_phone) TextView tvPhone;
    @BindView(R.id.cardView) CardView cardView;
    @BindView(R.id.fab_camera) ImageButton fabCamera;
    @BindView(R.id.image_profile) CircleImageView imageProfile;
    private ProgressDialogCustom mProgressDialogCustom;
    private SessionManager sessionManager;
    private ProfileContract.Presenter presenter;
    private  final int GALLERY_CODE = 5;;
    private  final int CAMERA_CODE = 1888;
    private  final int CAMERA = 0x5;
    public String imageBase64;


    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, root);
        sessionManager = new SessionManager(getContext());
        mProgressDialogCustom = new ProgressDialogCustom(getActivity(), "Actualizando...");
        askForPermission(android.Manifest.permission.CAMERA, CAMERA);
        presenter = new ProfilePresenterImpl(this, getContext());
        presenter.start();
        return root;
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

    @Override
    public void loadUser(PersonEntity personEntity, boolean t) {
        if (t) {
            Picasso.with(getContext()).load(sessionManager.getPersonEntity().getPersonImage()).into(imageProfile);
        } else {
            imageProfile.setImageDrawable(getResources().getDrawable(R.drawable.user));
        }
        tvName.setText(personEntity.getUser().getFirstName() + " " + personEntity.getUser().getLastName());
        tvBirthDate.setText(personEntity.getBornDate());
        tvDireccion.setText(personEntity.getAddress().getLocation());
        tvEmail.setText(personEntity.getUser().getEmail());
        tvPhone.setText(personEntity.getPhoneNumber());

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
        ((ProfileActivity) getActivity()).showMessageError(error);
    }

    //metodo para actualizar el navegation view
    @Override
    public void updateNav() {
        ((ProfileActivity) getActivity()).returnResult();
    }

    @OnClick(R.id.fab_camera)
    void onClickButton() {
        Bundle bundle = new Bundle();
        bundle.putInt("IdFragment", 2);
        CustomBottomSheetDialogFragment fragment = new CustomBottomSheetDialogFragment();
        fragment.setArguments(bundle);
        fragment.show(getActivity().getSupportFragmentManager(), CustomBottomSheetDialogFragment.FRAGMENT_KEY);
    }

    public void openCamera() {
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
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
                Bitmap resizedImageGallery = Bitmap.createScaledBitmap(bitmapGallery, 600, 600, false);
                imageBase64 = convertBitmapToBASE64(resizedImageGallery);
                sendPhoto(imageBase64);

            } catch (Exception e) {
                setMessageError("error");
            }
        }
        if (requestCode == CAMERA_CODE && resultCode == Activity.RESULT_OK) {
                Bitmap bitmapCamera = (Bitmap) data.getExtras().get("data");
                Bitmap resizedImageGallery = Bitmap.createScaledBitmap(bitmapCamera, 600, 600, false);
                imageBase64 = convertBitmapToBASE64(resizedImageGallery);
                sendPhoto(imageBase64);
        }
    }

    private void sendPhoto(String imageBase64) {
        if (sessionManager.getPersonEntity().getPersonImage().contains("http")) {
            presenter.changePhoto("data:image/jpeg;base64," + imageBase64);
        } else {
            presenter.uploadPhoto("data:image/jpeg;base64," + imageBase64);
        }
    }

    public String convertBitmapToBASE64(Bitmap path) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        path.compress(Bitmap.CompressFormat.JPEG, 100, bytes); //bm is the bitmap object
        byte[] b = bytes.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
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

    public void setImage(String photo) {
        //construir personentity
        sessionManager.saveUser(new PersonEntity(sessionManager.getPersonEntity().getId(),
                sessionManager.getPersonEntity().getUser(), sessionManager.getPersonEntity().getBornDate(),
                sessionManager.getPersonEntity().getPhoneNumber(), sessionManager.getPersonEntity().getAddress(),
                photo));
        Picasso.with(getContext()).load(sessionManager.getPersonEntity().getPersonImage()).into(imageProfile);
    }
}