package com.satria.authenticguards.agv3mvvm.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.satria.authenticguards.R;
import com.satria.authenticguards.agv3mvvm.model.LoginUser;
import com.satria.authenticguards.agv3mvvm.viewmodels.LoginViewModel;
import com.satria.authenticguards.databinding.ActivityMainBinding;
import com.satria.authenticguards.databinding.ActivityRegisterBinding;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityRegisterBinding loginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
        generateView();
    }
    private void setupView(){
        loginViewModel=ViewModelProviders.of(this).get(LoginViewModel.class);
        loginBinding=DataBindingUtil.setContentView(this,R.layout.activity_login);
        loginBinding.setLifecycleOwner(this);
        loginBinding.setRegisterViewModel(loginViewModel);
    }

    private void generateView(){
        loginViewModel.getUser().observe(this, new Observer<LoginUser>() {
            @Override
            public void onChanged(LoginUser loginUser) {
                if(TextUtils.isEmpty(Objects.requireNonNull(loginUser.getEmail()))){
                    
                }
            }
        });
    }
}
