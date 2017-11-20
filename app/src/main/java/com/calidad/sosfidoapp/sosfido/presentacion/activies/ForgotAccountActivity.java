package com.calidad.sosfidoapp.sosfido.presentacion.activies;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.calidad.sosfidoapp.sosfido.R;
import com.calidad.sosfidoapp.sosfido.presentacion.contracts.ForgotAccountContract;
import com.calidad.sosfidoapp.sosfido.presentacion.presenters.ForgotAccountPresenter;
import com.calidad.sosfidoapp.sosfido.utils.ProgressDialogCustom;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForgotAccountActivity extends AppCompatActivity implements Validator.ValidationListener,ForgotAccountContract.View{
    @Email(message = "Este campo no tiene el formato email")
    @BindView(R.id.et_fa_email) EditText faEmail;
    @Password(message = "La contraseña debe tener mínimo 6 campos")
    @BindView(R.id.et_fa_password) EditText faPassword;
    @ConfirmPassword(message = "No coincide con la contraseña")
    @BindView(R.id.et_fa_repeat_password) EditText faRepeatPassword;
    @BindView(R.id.btn_send_email) Button checkEmail;
    @BindView(R.id.btn_update_pass) Button updatePassword;
    @BindView(R.id.coordinatorLayout) CoordinatorLayout container;
    @BindView(R.id.lnfa_email) LinearLayout lnEmail;
    @BindView(R.id.lnfa_pass) LinearLayout lnPassword;
    @BindView(R.id.name_person) TextView namePersona;
    @BindView(R.id.enter_name) TextView enterName;
    private Validator validator;
    public ForgotAccountContract.Presenter presenter;
    private ProgressDialogCustom mProgressDialogCustom;
    public String idUser;
    @BindView(R.id.toolbar) Toolbar tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_account_entity);
        ButterKnife.bind(this);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        presenter = new ForgotAccountPresenter(this);
        validator = new Validator(this);
        validator.setValidationListener(this);
        mProgressDialogCustom = new ProgressDialogCustom(this, "Verificando ...");


        checkEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKeyboard();
                validator.validate();
            }
        });

        updatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKeyboard();
                validator.validate();
            }
        });
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(getApplication(),LoginActivity.class);
        startActivity(a);
        finish();
    }
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onValidationSucceeded() {
       presenter.start(faEmail.getText().toString().trim(),faPassword.getText().toString(),idUser);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else if (view instanceof TextView) {
                ((TextView) view).setError(message);
            } else {
                showMessage(message);
            }
        }
    }

    @Override
    public void showUpdatePassword(String message,String userId) {
        lnPassword.setVisibility(View.VISIBLE);
        faEmail.setText("");
        lnEmail.setVisibility(View.GONE);
        namePersona.setText(message+" existe");
        enterName.setVisibility(View.GONE);
        this.idUser=userId;
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
    public void showMessage(String message) {
        this.showMessageSnack(container, message, R.color.error_red);
    }

    @Override
    public void closeActivity() {
        messageInfo().show();
    }

    public AlertDialog messageInfo(){
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Mensaje");
        dialog.setMessage("Su contraseña ha sido actualizada correctamente");
        dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent a = new Intent(getApplication(),LoginActivity.class);
                startActivity(a);
                finish();
                dialogInterface.dismiss();
            }
        });
        return dialog.create();
    }

    public void showMessageSnack(View container, String message, int colorResource) {
        if (container != null) {
            Snackbar snackbar = Snackbar
                    .make(container, message, Snackbar.LENGTH_LONG);
            snackbar.setActionTextColor(Color.WHITE);
            View sbView = snackbar.getView();
            sbView.setBackgroundColor(ContextCompat.getColor(this, colorResource));
            TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        } else {
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }
    }

    private void closeKeyboard() {
        View view = ForgotAccountActivity.this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
