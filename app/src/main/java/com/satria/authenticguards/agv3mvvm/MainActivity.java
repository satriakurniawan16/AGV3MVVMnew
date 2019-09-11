package com.satria.authenticguards.agv3mvvm;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.satria.authenticguards.R;
import com.satria.authenticguards.agv3mvvm.Screens.MasterActivity;
import com.satria.authenticguards.agv3mvvm.dataBinding.LoginHandler;


public class MainActivity extends AppCompatActivity {
    private Button btnRegister,btnLogin,btnGmail,btnEmail;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       setContentView(R.layout.activity_main);
       btnRegister=findViewById(R.id.btnRegister);
       btnLogin=findViewById(R.id.btnLogin);
       //btnGmail=findViewById(R.id.login_gmail);
       //btnEmail=findViewById(R.id.login_email);



    }

    //cek user sudah login atau belum
    @Override
    protected void onStart() {
        super.onStart();
//        FirebaseUser currentUser=mAuth.getCurrentUser();
//        if (currentUser!=null)
//        updateUI(currentUser);
//        else updateUI(null);
    }
    private void updateUI(FirebaseUser user) {

        if (user != null) {
            Intent intent = new Intent(MainActivity.this,MasterActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
