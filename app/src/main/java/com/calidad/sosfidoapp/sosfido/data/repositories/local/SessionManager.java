package com.calidad.sosfidoapp.sosfido.data.repositories.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.calidad.sosfidoapp.sosfido.data.entities.PersonEntity;
import com.google.gson.Gson;

/**
 * Created by jairbarzola on 24/09/17.
 */

public class SessionManager {

    private SharedPreferences preferences;
    private  SharedPreferences.Editor editor;
    //NAME SHARED PREFERENCES
    private static final String NAME_PREFERENCE = "sosfidoapp";

    //USER TOKEN
    private static final String U_TOKEN = "uToken";
    //USER JSON
    private static final String U_JSON = "uJson";
    // USER ISLOGIN
    private static final String IS_LOGIN = "uLogin";
    // USER DEVICE
    private static final String DEVICE = "uDevice";
    public Context context;
    public SessionManager(Context context){
        this.context = context;
        preferences = context.getSharedPreferences(NAME_PREFERENCE,Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public boolean isLogin()  {
        return preferences.getBoolean(IS_LOGIN, false);
    }

    public void openSession(String token, PersonEntity personEntity) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(U_TOKEN, token);
        if(personEntity!=null){
            Gson gson = new Gson();
            String user= gson.toJson(personEntity);
            editor.putString(U_JSON, user);
        }
        editor.commit();
    }

    //obtener el token
    public String getUserToken() {
        if (isLogin()) {
            return preferences.getString(U_TOKEN, "");
        } else {
            return "";
        }
    }
    //save user
    public void saveUser(PersonEntity personEntity){
        editor.putString(U_JSON, null);
        editor.commit();
        if(personEntity!=null){
            Gson gson = new Gson();
            String user= gson.toJson(personEntity);
            editor.putString(U_JSON, user);
        }
        editor.commit();
    }
    //reset session
    public void closeSession() {
        editor.putBoolean(IS_LOGIN, false);
        editor.putString(U_TOKEN, null);
        editor.putString(U_JSON, null);
        editor.putString(DEVICE,null);
        editor.commit();
    }
    //metodo para obtener datos del usuario guardados en memoria interna
    public PersonEntity getPersonEntity(){
        String userData = preferences.getString(U_JSON, null);
        return new Gson().fromJson(userData, PersonEntity.class);
    }

    public void saveDevice(String iddevice){
        editor.putString(DEVICE,iddevice);
        editor.commit();
    }

    public String getIdDevice(){
        return  preferences.getString(DEVICE,"");
    }
}
