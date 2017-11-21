package com.calidad.sosfidoapp.sosfido.presentacion.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import com.calidad.sosfidoapp.sosfido.presentacion.activies.HomeActivity;
import com.calidad.sosfidoapp.sosfido.presentacion.contracts.RegisterUserContract;
import com.calidad.sosfidoapp.sosfido.presentacion.presenters.RegisterUserPresenterImpl;
import com.calidad.sosfidoapp.sosfido.R;
import com.calidad.sosfidoapp.sosfido.utils.ProgressDialogCustom;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jairbarzola on 29/09/17.
 */

public class RegisterUserFragment extends Fragment implements RegisterUserContract.View, Validator.ValidationListener,DatePickerDialog.OnDateSetListener {

    @NotEmpty(message = "Este campo no puede ser vacío")
    @BindView(R.id.et_first_name) EditText etFirstName;
    @NotEmpty(message = "Este campo no puede ser vacío")
    @BindView(R.id.et_last_name) EditText etLastName;
    @NotEmpty(message = "Este campo no puede ser vacío")
    @BindView(R.id.et_birth_date) EditText etBirthDate;
    @NotEmpty(message = "Este campo no puede ser vacío")
    @BindView(R.id.et_phone) EditText etPhone;
    @Email(message = "Este campo no tiene el formato email")
    @BindView(R.id.et_email) EditText etEmail;
    @NotEmpty(message = "Este campo no puede ser vacío")
    @BindView(R.id.et_district) EditText etDistrict;

    @Password(message = "La contraseña debe tener mínimo 6 campos")
    @BindView(R.id.et_password) EditText etPassword;
    @ConfirmPassword(message = "No coincide con la contraseña")
    @BindView(R.id.et_repeat_password) EditText etRepeatPass;

    private final int REQUEST_CODE_PLACEPICKER = 1;
    private RegisterUserContract.Presenter presenter;
    private ProgressDialogCustom mProgressDialogCustom;
    private Validator validator;
    private String location;
    private String longitude;
    private String latitude;
    public DatePickerDialog dpd;


    public static RegisterUserFragment newInstance() {
        return new RegisterUserFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_register_user, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        validator = new Validator(this);
        validator.setValidationListener(this);
        mProgressDialogCustom = new ProgressDialogCustom(getContext(), "Registrando...");
        Calendar now = Calendar.getInstance();
        dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        presenter = new RegisterUserPresenterImpl(this, getContext());

    }

    // funcion llamada desde la activity para validar
    public void register() {
        validator.validate();
    }

    @Override
    public void registerSuccessfully() {
        Intent i = new Intent(getActivity(), HomeActivity.class);
        startActivity(i);
        getActivity().finish();
        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onValidationSucceeded() {
        presenter.register(etFirstName.getText().toString(), etLastName.getText().toString(), location, longitude, latitude, etBirthDate.getText().toString(),
                etEmail.getText().toString(), etPassword.getText().toString(), etPhone.getText().toString());
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getContext());
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

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        etBirthDate.setText(year + "-" + String.format("%02d", monthOfYear + 1) + "-" + String.format("%02d", dayOfMonth));
    }

    @OnClick(R.id.et_birth_date)
    public void onViewClicked(View view) {

        if (view.getId() == R.id.et_birth_date) {
            dpd.show(getActivity().getFragmentManager(), "DatePickerDialog");
        }

    }

    public void closeKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @OnClick(R.id.et_district)
    void onclickAddress() {
        startPlacePickerActivity();
    }

    private void startPlacePickerActivity() {
        PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
        try {
            Intent intent = intentBuilder.build(getActivity());
            startActivityForResult(intent, REQUEST_CODE_PLACEPICKER);
        } catch (Exception e) {
            setMessageError("No se pudo cargar la foto, intente nuevamente");
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PLACEPICKER && resultCode == Activity.RESULT_OK) {
            displaySelectedPlaceFromPlacePicker(data);
        }
    }

    private void displaySelectedPlaceFromPlacePicker(Intent data) {
        Place placeSelected = PlacePicker.getPlace(data, getActivity());
        location = placeSelected.getAddress().toString();
        etDistrict.setText(location);
        latitude = String.valueOf(placeSelected.getLatLng().latitude);
        longitude = String.valueOf(placeSelected.getLatLng().longitude);
    }
}