package com.satria.authenticguards.agv3mvvm.View;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.satria.authenticguards.R;
import com.satria.authenticguards.agv3mvvm.Screens.MasterActivity;
import com.satria.authenticguards.agv3mvvm.utils.FirebaseUtil;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class EditProfileActivity extends AppCompatActivity {
    private static final String TAG = EditProfileActivity.class.getName();
    ImageView picture, back;
    EditText name,age,address,email,phonenumber;
    Button savEdit;
    String isComplete;
    Dialog dialog;

    private static int IMG_CAMERA = 2;

    private Uri filepath;

    private FirebaseStorage storage;
    private StorageReference storageReference;

    private MaterialBetterSpinner spinner;
    private final String [] genderSpinner = {"Male","Female"};
    private FirebaseUtil firebaseUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        filepath = null;
        firebaseUtil=new FirebaseUtil();

        picture = (ImageView)findViewById(R.id.fotoProfile);
        name = (EditText)findViewById(R.id.editTextNamaProfile);
        age = (EditText)findViewById(R.id.editTextUmurProfile);
        address = (EditText)findViewById(R.id.editTextLokasiProfile);
        email = (EditText)findViewById(R.id.editTextEmailProfile);
        phonenumber = (EditText)findViewById(R.id.editTextPhoneNumberProfile);
        savEdit = (Button)findViewById(R.id.btnSaveEdit);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        back = (ImageView) findViewById(R.id.backPress);

        dialog = new Dialog(getApplicationContext(), android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        spinner = (MaterialBetterSpinner) findViewById(R.id.genderProfile);
        ArrayAdapter<String> genderAdapter= new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, genderSpinner);
        spinner.setAdapter(genderAdapter);
        //this method in firebaseutil
        firebaseUtil.getDataEditProfil(getApplicationContext(),name,spinner,age,address,email,phonenumber,picture);
        backMenu();
        clickPicture();
        clickSave();
    }

    private void backMenu(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditProfileActivity.this,MasterActivity.class));
            }
        });
    }
    private void clickPicture(){
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(EditProfileActivity.this);
            }
        });
    }
    private void clickSave(){
        savEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProfile();
            }
        });
    }

    private void saveProfile() {
    }
}
