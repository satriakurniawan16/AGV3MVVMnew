package com.satria.authenticguards.agv3mvvm.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.satria.authenticguards.R;

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
    }

    private void setUpView(View view) {
        scanBtn=view.findViewById(R.id.button_scan_QRCode);
    }

}
