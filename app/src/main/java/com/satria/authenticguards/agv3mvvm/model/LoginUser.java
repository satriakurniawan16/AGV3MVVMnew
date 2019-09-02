package com.satria.authenticguards.agv3mvvm.model;

import android.util.Patterns;

import java.util.regex.Pattern;

public class LoginUser {
    public String email,strPassword;

    public LoginUser(String email, String strPassword) {
        this.email = email;
        this.strPassword = strPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStrPassword() {
        return strPassword;
    }

    public void setStrPassword(String strPassword) {
        this.strPassword = strPassword;
    }

    public boolean isValidEmail(){
        return Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches();
    }

}
