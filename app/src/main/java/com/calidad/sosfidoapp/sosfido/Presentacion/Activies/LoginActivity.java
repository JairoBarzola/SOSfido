package com.calidad.sosfidoapp.sosfido.Presentacion.Activies;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.calidad.sosfidoapp.sosfido.Presentacion.Contracts.LoginContract;
import com.calidad.sosfidoapp.sosfido.Presentacion.Presenters.LoginPresenterImpl;
import com.calidad.sosfidoapp.sosfido.R;
import com.calidad.sosfidoapp.sosfido.Utils.ProgressDialogCustom;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity implements LoginContract.View,Validator.ValidationListener{

    @Email(message = "Email no válido")
    @BindView(R.id.et_usuario_l)
    EditText etUsuario;

    @NotEmpty(message = "Este campo no puede ser vacío")
    @BindView(R.id.et_password_l)
    EditText etPassword;

    @BindView(R.id.coordinatorLayout) CoordinatorLayout container;

    private Validator validator;
    private LoginContract.Presenter presenter;
    private ProgressDialogCustom mProgressDialogCustom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        validator = new Validator(this);
        validator.setValidationListener(this);
        presenter = new LoginPresenterImpl(this,getApplicationContext());
        mProgressDialogCustom = new ProgressDialogCustom(this,"Iniciando Sesión...");
    }

    @OnClick({R.id.btn_login,R.id.btn_register})
    void onClickView(View view)
    {
        switch (view.getId()) {
            case R.id.btn_login:
                closeKeyboard();
            validator.validate();
                break;
            case R.id.btn_register:
                closeKeyboard();
                openActivity(RegisterUserActivity.class);
                break;
        }
    }
    public void openActivity(Class<?> activity) {
        Intent i= new Intent(LoginActivity.this,activity);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }

    @Override
    public void loginSuccessfully() {
        openActivity(HomeActivity.class);
        finish();
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
        showMessage(error);
    }

    @Override
    public void setDialogMessage(String message) {
        showMessage(message);
    }

    @Override
    public boolean isActive() {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void onValidationSucceeded() {
        presenter.login(etUsuario.getText().toString(),etPassword.getText().toString());
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
                setMessageError(message);
            }
        }
    }

    public void showMessage(String message) {
        this.showMessageSnack(container, message, R.color.error_red);
    }

    public void showMessageSnack(View container, String message, int colorResource) {
        if (container != null) {
            Snackbar snackbar = Snackbar
                    .make(container, message, Snackbar.LENGTH_LONG);
            snackbar.setActionTextColor(Color.WHITE);
            View sbView = snackbar.getView();
            sbView.setBackgroundColor(ContextCompat.getColor(this, colorResource));
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        } else {
            Toast toast =
                    Toast.makeText(getApplicationContext(),
                            message, Toast.LENGTH_LONG);

            toast.show();
        }

    }
    public void showMessageError(String message) {
        CoordinatorLayout container = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        this.showMessageSnack(container, message, R.color.error_red);

    }
    void closeKeyboard(){
        View view = LoginActivity.this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
