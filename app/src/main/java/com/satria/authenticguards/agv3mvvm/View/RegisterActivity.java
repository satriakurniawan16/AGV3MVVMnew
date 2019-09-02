package com.satria.authenticguards.agv3mvvm.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

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
import com.satria.authenticguards.agv3mvvm.model.User;
import com.satria.authenticguards.agv3mvvm.viewmodels.RegisterViewModel;
import com.satria.authenticguards.databinding.ActivityRegisterBinding;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private RegisterViewModel registerViewModel;
    ActivityRegisterBinding registerBinding;
    ProgressDialog progressDialog;
    FirebaseAuth mFirebaseAuth;
    String gambar = "https://firebasestorage.googleapis.com/v0/b/ag-version-3.appspot.com/o/hadiah%2Fuser.png?alt=media&token=178bff40-3a10-4d6f-8544-c93d7fc2dcc1";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
        generateView();

    }

    private void generateView() {
        registerViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                if(TextUtils.isEmpty(Objects.requireNonNull(user).getName())) {
                    registerBinding.edttxtFullname.setError("Enter Fullname !");
                    registerBinding.edttxtFullname.requestFocus();
                }else if (TextUtils.isEmpty(Objects.requireNonNull(user).getEmail())){
                    registerBinding.edttxtEmailaddress.setError("Enter Email ! ");
                    registerBinding.edttxtEmailaddress.requestFocus();
                }else if (TextUtils.isEmpty(Objects.requireNonNull(user).getNumberPhone())){
                    registerBinding.edttxtPhonenumber.setError("Enter Phone Number ! ");
                    registerBinding.edttxtPhonenumber.requestFocus();
                }else if (TextUtils.isEmpty(Objects.requireNonNull(user).getStrPassword())){
                    registerBinding.edttxtPassword.setError("Enter Password ! ");
                    registerBinding.edttxtPassword.requestFocus();
                }else if (TextUtils.isEmpty(Objects.requireNonNull(user).getStrConfirmPassword())){
                    registerBinding.edttxtRetypePassword.setError("Enter Confirmation Password ! ");
                    registerBinding.edttxtRetypePassword.requestFocus();
                }else if (!user.isEmailValid()){
                    registerBinding.edttxtEmailaddress.setError("Email Invalid !");
                    registerBinding.edttxtEmailaddress.requestFocus();
                }else if (!user.isPasswordLengthGreaterThan5()){
                    registerBinding.edttxtPassword.setError("Password lenth min 5 !");
                    registerBinding.edttxtPassword.requestFocus();
                }else  if (!user.isPasswordConfirmation()){
                    registerBinding.edttxtRetypePassword.setError("Password doesnt match ! ");
                    registerBinding.edttxtRetypePassword.requestFocus();
                }else{
                    progressDialog();
                    mFirebaseAuth.createUserWithEmailAndPassword(user.getEmail(),user.getStrPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, "Registration Failed : " + task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }else {
                                saveUser(new User(user.getName(),gambar,user.getNumberPhone(),"10000",user.getEmail(),"0","","","false","false","",""));
                            }
                        }
                    });
                }
            }
        });
    }

    private void saveUser(User user){
        final FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
        final DatabaseReference dbUser = FirebaseDatabase.getInstance().getReference("user");
        final HashMap<String,Object> putUser = new HashMap<>();
        putUser.put("numberPhone",user.getNumberPhone());
        putUser.put("idEmail",currentUser.getUid());
        putUser.put("name", user.getNumberPhone());
        putUser.put("email",user.getEmail());
        putUser.put("gender",user.getGender());
        putUser.put("age",user.getAge());
        putUser.put("address",user.getAddress());
        putUser.put("gambar",gambar);
        putUser.put("totalPoint",user.getTotalPoint());
        putUser.put("completeProfile",user.getCompleteProfile());
        putUser.put("onverifiednumber",user.getOnVerifiedNumber());

        assert currentUser != null;
        if(!currentUser.isEmailVerified()){
            currentUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Register Complete", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    intent.putExtra("titleSlider",user.getName());
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RegisterActivity.this, "Error : " + e.toString(), Toast.LENGTH_SHORT).show();
                }

            });
        }
    }

    private void setupView() {
        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
        registerBinding = DataBindingUtil.setContentView(this,R.layout.activity_register);
        registerBinding.setLifecycleOwner(this);
        registerBinding.setRegisterViewModel(registerViewModel);
    }

    private void progressDialog(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Create Account");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
    }
}
