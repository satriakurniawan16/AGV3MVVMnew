package com.satria.authenticguards.agv3mvvm.dataBinding;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.satria.authenticguards.agv3mvvm.View.LoginActivity;
import com.satria.authenticguards.agv3mvvm.View.RegisterActivity;
import com.satria.authenticguards.agv3mvvm.viewmodels.LoginViewModel;

public class LoginHandler {

    public void onLoginClicked(View view) {
        Toast.makeText(view.getContext(), "Login clicked!", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(view.getContext(),LoginActivity.class);
        view.getContext().startActivity(intent);
    }
    public void onRegisterClicked(View view) {
        Intent intent = new Intent(view.getContext(),RegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        view.getContext().startActivity(intent);
    }
}
