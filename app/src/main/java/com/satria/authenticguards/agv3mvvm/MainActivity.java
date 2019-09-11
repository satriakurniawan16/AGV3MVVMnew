package com.satria.authenticguards.agv3mvvm;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.satria.authenticguards.R;
import com.satria.authenticguards.agv3mvvm.Screens.MasterActivity;
import com.satria.authenticguards.agv3mvvm.dataBinding.LoginHandler;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       setContentView(R.layout.activity_main);



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
