package com.satria.authenticguards.agv3mvvm.viewmodels;

import com.satria.authenticguards.agv3mvvm.model.LoginUser;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {

    public MutableLiveData<String> EmailAddress=new MutableLiveData<>();
    public MutableLiveData<String> Password=new MutableLiveData<>();

    private MutableLiveData<LoginUser> loginUserMutableLiveData;

    public MutableLiveData<LoginUser> getUser(){
        if (loginUserMutableLiveData==null){
            loginUserMutableLiveData=new MutableLiveData<>();
        }
        return loginUserMutableLiveData;
    }

    
}
