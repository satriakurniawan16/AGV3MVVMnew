package com.satria.authenticguards.agv3mvvm.fragment;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.satria.authenticguards.R;
import com.satria.authenticguards.agv3mvvm.View.LoginActivity;
import com.satria.authenticguards.agv3mvvm.View.QrCodeActivity;

import java.security.Permission;

/**
 * A simple {@link Fragment} subclass.
 */
public class QRcodeFragment extends Fragment {
    private Button scanBtn;

    final int REQUEST_CODE_CAMERA=999;
    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;

    public QRcodeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_qrcode, container, false);
        setUpView(view);
        generateView(view);
        return view;
    }

    private void generateView(View view) {
        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED){
                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA},REQUEST_CODE_CAMERA);
                }else {
                    if(mFirebaseUser != null){
                        Intent intent = new Intent(getActivity(), QrCodeActivity.class);
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private void setUpView(View view) {
        scanBtn=view.findViewById(R.id.button_scan_QRCode);
        mFirebaseAuth=FirebaseAuth.getInstance();
        mFirebaseUser=mFirebaseAuth.getCurrentUser();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==REQUEST_CODE_CAMERA){
            if (grantResults.length < 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getContext(), "Please Give permission to acces camera!", Toast.LENGTH_SHORT).show();
            }else{
                mFirebaseAuth=FirebaseAuth.getInstance();
                mFirebaseUser=mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser!=null){
                    startActivity(new Intent(getActivity(),QrCodeActivity.class));
                }else{
                    startActivity(new Intent(getActivity(),LoginActivity.class));
                }
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
