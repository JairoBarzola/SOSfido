package com.calidad.sosfidoapp.sosfido.Presentacion.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.calidad.sosfidoapp.sosfido.Presentacion.Activies.HomeActivity;
import com.calidad.sosfidoapp.sosfido.Presentacion.Contracts.RegisterUserContract;
import com.calidad.sosfidoapp.sosfido.Presentacion.Presenters.RegisterUserPresenterImpl;
import com.calidad.sosfidoapp.sosfido.R;
import com.calidad.sosfidoapp.sosfido.Utils.ProgressDialogCustom;
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

public class RegisterUserFragment extends Fragment implements RegisterUserContract.View,Validator.ValidationListener, com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener{

    @NotEmpty(message = "Este campo no puede ser vacío")
    @BindView(R.id.et_first_name) EditText etFirstName;
    @NotEmpty(message = "Este campo no puede ser vacío")
    @BindView(R.id.et_last_name) EditText etLastName;
    @NotEmpty(message = "Este campo no puede ser vacío")
    @BindView(R.id.et_dni) EditText etDni;
    @NotEmpty(message = "Este campo no puede ser vacío")
    @BindView(R.id.et_gender) EditText etGender;
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
    @ConfirmPassword(message = "No concuerda con la contraseña")
    @BindView(R.id.et_repeat_password) EditText etRepeatPass;

    RegisterUserContract.Presenter presenter;
    private ProgressDialogCustom mProgressDialogCustom;
    Validator validator;
    com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd;
    public RegisterUserFragment() {}
    public static RegisterUserFragment newInstance() {return new RegisterUserFragment();}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_register_user, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        validator = new Validator(this);
        validator.setValidationListener(this);
        mProgressDialogCustom = new ProgressDialogCustom(getContext(),"Registrando...");
        Calendar now = Calendar.getInstance();
        dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        presenter = new RegisterUserPresenterImpl(this,getContext());

    }
    @OnClick(R.id.btn_register)
    public void onViewClicked() {

        validator.validate();
    }


    @Override
    public void registerSuccessfully() {
        Intent i = new Intent(getActivity(), HomeActivity.class);
        startActivity(i);
        getActivity().finish();
        getActivity().overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
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
        Toast.makeText(getContext(),error,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setDialogMessage(String message) {

    }

    @Override
    public void onValidationSucceeded() {
        presenter.register(etFirstName.getText().toString(),etLastName.getText().toString(),etDni.getText().toString(),
                etGender.getText().toString(),etDistrict.getText().toString(),etBirthDate.getText().toString(),
                etEmail.getText().toString(),etPassword.getText().toString(),etPhone.getText().toString());
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
    public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        etBirthDate.setText(year + "-" + String.format("%02d", monthOfYear + 1) + "-" + String.format("%02d", dayOfMonth));
    }
    @OnClick({R.id.et_birth_date,R.id.et_gender})
    public void onViewClicked(View view) {

        switch (view.getId()){
            case R.id.et_birth_date:
                dpd.show(getActivity().getFragmentManager(), "DatePickerDialog");
                break;
            case  R.id.et_gender:
                getGender().show();
                break;
        }

    }

    public AlertDialog getGender() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        final CharSequence[] items = new CharSequence[2];

        items[0] = "Masculino";
        items[1] = "Femenino";

        builder.setTitle("Género")
                .setSingleChoiceItems(items,2, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==0){
                            dialog.dismiss();
                            etGender.setText("M");
                        }
                        else{
                            dialog.dismiss();
                            etGender.setText("F");
                        }
                    }
                });
        return builder.create();
    }
    void closeKeyboard(){
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}