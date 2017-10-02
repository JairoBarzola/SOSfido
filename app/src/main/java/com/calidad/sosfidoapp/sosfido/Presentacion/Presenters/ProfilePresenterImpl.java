package com.calidad.sosfidoapp.sosfido.Presentacion.Presenters;

import android.content.Context;

import com.calidad.sosfidoapp.sosfido.Data.Entities.PersonEntity;
import com.calidad.sosfidoapp.sosfido.Data.Repositories.Local.SessionManager;
import com.calidad.sosfidoapp.sosfido.Presentacion.Contracts.ProfileContract;
import com.calidad.sosfidoapp.sosfido.Presentacion.Fragments.ProfileFragment;

/**
 * Created by jairbarzola on 2/10/17.
 */

public class ProfilePresenterImpl implements ProfileContract.Presenter{

    private ProfileFragment view;
    private SessionManager sessionManager;
    private Context context;


    public ProfilePresenterImpl(ProfileFragment view, Context context){
        this.view=view;
        this.context=context;
        sessionManager = new SessionManager(context);
    }
    @Override
    public void start() {
        PersonEntity personEntity = sessionManager.getPersonEntity();
        view.loadUser(personEntity);
    }
}
