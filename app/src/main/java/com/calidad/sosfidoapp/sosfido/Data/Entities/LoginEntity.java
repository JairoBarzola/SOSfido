package com.calidad.sosfidoapp.sosfido.Data.Entities;

/**
 * Created by jairbarzola on 1/10/17.
 */

public class LoginEntity {

    private String email;
    private String password;

    public LoginEntity(String email,String password){
        this.setPassword(password);
        this.setEmail(email);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
