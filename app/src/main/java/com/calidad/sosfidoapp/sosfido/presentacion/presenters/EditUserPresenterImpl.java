package com.calidad.sosfidoapp.sosfido.presentacion.presenters;

import android.content.Context;

import com.calidad.sosfidoapp.sosfido.data.entities.PersonEntity;
import com.calidad.sosfidoapp.sosfido.data.repositories.local.SessionManager;
import com.calidad.sosfidoapp.sosfido.data.repositories.remote.ServiceFactory;
import com.calidad.sosfidoapp.sosfido.presentacion.activies.EditUserActivity;
import com.calidad.sosfidoapp.sosfido.presentacion.contracts.EditUserContract;

/**
 * Created by jairbarzola on 18/11/17.
 */

public class EditUserPresenterImpl  implements EditUserContract.Presenter{

    public Context context;
    public EditUserActivity view;
    public SessionManager sessionManager;
    public ServiceFactory serviceFactory;

    public EditUserPresenterImpl(Context context,EditUserActivity view){
        this.context = context;
        this.view=view;
        serviceFactory = new ServiceFactory();
        sessionManager = new SessionManager(context);
    }


    @Override
    public void sendDataChanged(PersonEntity personEntity) {

    }
}
