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
import com.satria.authenticguards.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        LoginHandler handler = new LoginHandler();
        binding.setHandlers(handler);

        mAuth=FirebaseAuth.getInstance();



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
