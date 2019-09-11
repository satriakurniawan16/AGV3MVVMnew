package com.satria.authenticguards.agv3mvvm.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.satria.authenticguards.R;
import com.satria.authenticguards.agv3mvvm.MainActivity;
import com.satria.authenticguards.agv3mvvm.Screens.MasterActivity;
import com.satria.authenticguards.agv3mvvm.model.LoginUser;
import com.satria.authenticguards.agv3mvvm.viewmodels.LoginViewModel;
import com.satria.authenticguards.databinding.ActivityLoginBinding;
import com.satria.authenticguards.databinding.ActivityRegisterBinding;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    ActivityLoginBinding loginBinding;
    private ProgressDialog progressDialog;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
        generateView();
        mFirebaseAuth=FirebaseAuth.getInstance();
    }
    private void setupView(){
        loginViewModel=ViewModelProviders.of(this).get(LoginViewModel.class);
        loginBinding=DataBindingUtil.setContentView(this,R.layout.activity_login);
        loginBinding.setLifecycleOwner(this);
        loginBinding.setLoginViewModel(loginViewModel);
    }

    private void generateView(){
        loginViewModel.getUser().observe(this, new Observer<LoginUser>() {
            @Override
            public void onChanged(LoginUser loginUser) {
                if(TextUtils.isEmpty(Objects.requireNonNull(loginUser.getEmail()))){
                    loginBinding.edttxtEmailaddressLogin.setError("You must enter email !");
                    loginBinding.edttxtEmailaddressLogin.requestFocus();
                }else if (TextUtils.isEmpty(Objects.requireNonNull(loginUser.getStrPassword()))){
                    loginBinding.edttxtPasswordLogin.setError("You must enter password !");
                    loginBinding.edttxtPasswordLogin.requestFocus();
                }else {
                    progressDialog();
                   mFirebaseAuth.signInWithEmailAndPassword(loginUser.getEmail(),loginUser.getStrPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                           if (task.isSuccessful()){
                               Intent intent =new Intent(LoginActivity.this,MasterActivity.class);
                               startActivity(intent);
                               finish();
                               Toast.makeText(LoginActivity.this, "Login successfuly!", Toast.LENGTH_SHORT).show();
                           }else{
                               Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                           }
                       }
                   });
                }
            }
        });
    }

    private void progressDialog(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Login Account");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
    }
}
