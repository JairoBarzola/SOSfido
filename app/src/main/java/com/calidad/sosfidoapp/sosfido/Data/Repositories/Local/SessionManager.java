package com.calidad.sosfidoapp.sosfido.Data.Repositories.Local;

import android.content.Context;
import android.content.SharedPreferences;

import com.calidad.sosfidoapp.sosfido.Data.Entities.PersonEntity;
import com.google.gson.Gson;

/**
 * Created by jairbarzola on 24/09/17.
 */

public class SessionManager {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;

    private static final String NAME_PREFERENCE = "sosfidoapp";

    /**
     USUARIO DATA SESSION
     */
    public static final String USER_TOKEN = "user_token";
    public static final String USER_JSON = "user_json";
    public static final String IS_LOGIN = "user_login";

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
        editor.putString(USER_TOKEN, token);
        if(personEntity!=null){
            Gson gson = new Gson();
            String user= gson.toJson(personEntity);
            editor.putString(USER_JSON, user);
        }
        editor.commit();
    }

    //obtener el token
    public String getUserToken() {
        if (isLogin()) {
            return preferences.getString(USER_TOKEN, "");
        } else {
            return "";
        }
    }
    //save user
    public void saveUser(PersonEntity personEntity){
        editor.putString(USER_JSON, null);
        editor.commit();
        if(personEntity!=null){
            Gson gson = new Gson();
            String user= gson.toJson(personEntity);
            editor.putString(USER_JSON, user);
        }
        editor.commit();
    }
    //reset session
    public void closeSession() {
        editor.putBoolean(IS_LOGIN, false);
        editor.putString(USER_TOKEN, null);
        editor.putString(USER_JSON, null);
        editor.commit();
    }
    //metodo para obtener datos del usuario guardados en memoria interna
    public PersonEntity getPersonEntity(){
        String userData = preferences.getString(USER_JSON, null);
        return new Gson().fromJson(userData, PersonEntity.class);
    }

}
