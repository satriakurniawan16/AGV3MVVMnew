package com.satria.authenticguards.agv3mvvm.viewmodels;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.satria.authenticguards.agv3mvvm.model.User;

public class RegisterViewModel extends ViewModel {

    public MutableLiveData<String> Fullname = new MutableLiveData<>();
    public MutableLiveData<String> EmailAddress = new MutableLiveData<>();
    public MutableLiveData<String> PhoneNumber = new MutableLiveData<>();
    public MutableLiveData<String> Password = new MutableLiveData<>();
    public MutableLiveData<String> ConfirmationPassword = new MutableLiveData<>();

    private MutableLiveData<User> userMutableLiveData;


    public MutableLiveData<User> getUser() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;
    }

    public void onClick(View view) {
        User loginUser = new User(Fullname.getValue(),"",PhoneNumber.getValue(),"",EmailAddress.getValue(),"","","","","",Password.getValue(),ConfirmationPassword.getValue());
        userMutableLiveData.setValue(loginUser);
    }

}
