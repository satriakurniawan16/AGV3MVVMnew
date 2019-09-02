package com.satria.authenticguards.agv3mvvm;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.satria.authenticguards.R;
import com.satria.authenticguards.agv3mvvm.dataBinding.LoginHandler;
import com.satria.authenticguards.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        LoginHandler handler = new LoginHandler();
        binding.setHandlers(handler);


    }
}
