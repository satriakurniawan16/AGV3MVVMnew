package com.satria.authenticguards.agv3mvvm.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.satria.authenticguards.R;
import com.satria.authenticguards.agv3mvvm.Screens.MasterActivity;
import com.satria.authenticguards.agv3mvvm.View.EditProfileActivity;
import com.satria.authenticguards.agv3mvvm.View.VerifyPhoneActivity;
import com.satria.authenticguards.agv3mvvm.model.User;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.UUID;

import androidx.annotation.NonNull;

public class FirebaseUtil {

    FirebaseAuth mAuth ;
    FirebaseUser mUser;
    public FirebaseUtil(){};

    public void Register(User user){
        final HashMap<String, Object> userMap= new HashMap<>();
        final FirebaseAuth mFirebase = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = mFirebase.getCurrentUser();
        final DatabaseReference dbuser = FirebaseDatabase.getInstance().getReference(Contants.USER_KEY).child(currentUser.getUid());
        String gambar = "https://firebasestorage.googleapis.com/v0/b/ag-version-3.appspot.com/o/hadiah%2Fuser.png?alt=media&token=178bff40-3a10-4d6f-8544-c93d7fc2dcc1";
        userMap.put("name",user.getEmail());
        userMap.put("email",user.getEmail());
        userMap.put("address",user.getAddress());
        userMap.put("gender",user.getGender());
        userMap.put("phone",user.getNumberPhone());
//        userMap.put("gambar",user.getG);
        userMap.put("totalPoint","");
        userMap.put("completeProfile","");
        userMap.put("onVerifiedNumber","");
    }

    public void getDataEditProfil(Context context, EditText name, EditText spinner, EditText age, EditText address, EditText email, EditText phoneNumber, ImageView picture){
        FirebaseUser currentUser=FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference dbs=FirebaseDatabase.getInstance().getReference("user").child(currentUser.getUid());
        dbs.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User us=dataSnapshot.getValue(User.class);
                name.setText(us.getName());
                spinner.setText(us.getGender());
                age.setText(us.getAge());
                address.setText(us.getAddress());
                email.setText(us.getEmail());
                if (!us.getEmail().isEmpty()){
                    email.setFocusable(false);
                }
                Picasso.get().load(us.getImage()).into(picture);
                if (us.getNumberPhone().equals("")) {
                    phoneNumber.setText("+62");
                } else {
                    phoneNumber.setText(us.getNumberPhone());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void saveSettingEditProfile(Context context, EditText name, EditText spinner, EditText age, EditText address, EditText email, EditText phonenumber, FirebaseStorage fbStorage, StorageReference sRefrence, Uri filepath,Dialog dialog){
        String nama=name.getText().toString();
        String spin=spinner.getText().toString();
        String ages=age.getText().toString();
        String addres=address.getText().toString();
        String emails=email.getText().toString();
        String phone=phonenumber.getText().toString();
        String isComplete="";
        FirebaseUser curentUser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference dbr = FirebaseDatabase.getInstance().getReference("user").child(curentUser.getUid());
        dbr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User usr = dataSnapshot.getValue(User.class);

                if (phonenumber.equals(usr.getNumberPhone())){
                    StorageReference storageReference=sRefrence.child("users/"+ UUID.randomUUID().toString());
                    if (filepath==null){
                        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        final DatabaseReference dbf = FirebaseDatabase.getInstance().getReference("user").child(currentUser.getUid());
                        dbf.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                User usr = dataSnapshot.getValue(User.class);
                                String isComplete = usr.getCompleteProfile();
                                final HashMap<String, Object> user = new HashMap<>();
                                if (isComplete.equals("false")){
                                    if (!TextUtils.isEmpty(name.getText()) && !TextUtils.isEmpty(age.getText()) && !TextUtils.isEmpty(address.getText())) {
                                        Log.d("Firebase Utils", "iscomplete : kondisi profil belum lengkap");
                                        String condition = "";
                                        condition = "true";
                                        user.put("name", nama);
                                        user.put("gender", spin);
                                        user.put("age", ages);
                                        user.put("address", addres);
                                        user.put("email", emails);
                                        user.put("id", currentUser.getUid());
                                        user.put("numberPhone", phone);
                                        user.put("gambar", usr.getImage());
                                        int total = Integer.parseInt(usr.getTotalPoint()) + 5000;
                                        user.put("totalPoint", String.valueOf(total));
                                        user.put("completeProfile", condition);
                                        dbf.setValue(user);
                                        Toast.makeText(context, "Profile tersimpan ,Lengkapi Profile dengan mendapatkan 5.000 point", Toast.LENGTH_SHORT).show();
                                        LayoutInflater layoutInflater= LayoutInflater.from(context);
                                        View viewthen = layoutInflater.inflate(R.layout.fullscreenpopup_editprfl, null);
                                        dialog.setContentView(viewthen);
                                        Button buttonhome;
                                        buttonhome = viewthen.findViewById(R.id.butthome);
                                        buttonhome.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent intent = new Intent(context, MasterActivity.class);
                                                context.startActivity(intent);
                                            }
                                        });
                                        dialog.show();

                                    }else{
                                        String condition = "";
                                        condition = "false";
                                        user.put("name", nama);
                                        user.put("gender", spin);
                                        user.put("age", ages);
                                        user.put("address", addres);
                                        user.put("email", emails);
                                        user.put("id", currentUser.getUid());
                                        user.put("numberPhone", phone);
                                        user.put("gambar", usr.getImage());
                                        user.put("totalPoint", usr.getTotalPoint());
                                        user.put("completeProfile", condition);
                                        dbf.setValue(user);
                                        Toast.makeText(context, "Profile tersimpan ,Lengkapi Profile dengan mendapatkan 5.000 point", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(context, MasterActivity.class);
                                        context.startActivity(intent);
                                    }
                                }else if (isComplete.equals("true")){
                                    String condition = "";
                                    condition = "true";
                                    user.put("name", nama);
                                    user.put("gender", spin);
                                    user.put("age", ages);
                                    user.put("address", addres);
                                    user.put("email", emails);
                                    user.put("id", currentUser.getUid());
                                    user.put("numberPhone", phone);
                                    user.put("gambar", usr.getImage());
                                    user.put("totalPoint", usr.getTotalPoint());
                                    user.put("completeProfile", condition);
                                    dbf.setValue(user);
                                    Toast.makeText(context, "Profile tersimpan ", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(context, MasterActivity.class);
                                    context.startActivity(intent);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }else{
                        final ProgressDialog progressDialog = new ProgressDialog(context);
                        progressDialog.setTitle("Uploading...");
                        progressDialog.show();
                        UploadTask uploadTask = storageReference.putFile(filepath);
                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                               storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                   @Override
                                   public void onSuccess(Uri uri) {
                                       final String urlGambar = uri.toString();
                                       final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                                       final DatabaseReference dbz = FirebaseDatabase.getInstance().getReference("user").child(currentUser.getUid());
                                       dbz.addListenerForSingleValueEvent(new ValueEventListener() {
                                           @Override
                                           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                               User usr = dataSnapshot.getValue(User.class);
                                               String isComplete = usr.getCompleteProfile();
                                               final HashMap<String, Object> user = new HashMap<>();
                                               if (isComplete.equals("false")) {
                                                   if (!TextUtils.isEmpty(name.getText()) && !TextUtils.isEmpty(age.getText()) && !TextUtils.isEmpty(address.getText())) {
                                                       Log.d("LOL", "iscomplete: ini kondisi dimana profil belum lengkap dan belum dapat reward ");
                                                       user.put("name", nama);
                                                       user.put("gender", spin);
                                                       user.put("age", ages);
                                                       user.put("address", addres);
                                                       user.put("email", emails);
                                                       user.put("numberPhone", phone);
                                                       user.put("gambar", urlGambar);
                                                       user.put("totalPoint", usr.getTotalPoint());
                                                       user.put("completeProfile", "true");
                                                       dbz.setValue(user);
                                                       LayoutInflater layoutInflater= LayoutInflater.from(context);
                                                       View viewthen = layoutInflater.inflate(R.layout.fullscreenpopup_editprfl, null);
                                                       dialog.setContentView(viewthen);
                                                       Button buttonhome;
                                                       buttonhome = viewthen.findViewById(R.id.butthome);
                                                       buttonhome.setOnClickListener(new View.OnClickListener() {
                                                           @Override
                                                           public void onClick(View view) {
                                                               Intent intent = new Intent(context, MasterActivity.class);
                                                               context.startActivity(intent);
                                                           }
                                                       });
                                                       dialog.show();

                                                   }else {
                                                       user.put("name", nama);
                                                       user.put("gender", spin);
                                                       user.put("age", ages);
                                                       user.put("address", addres);
                                                       user.put("email", emails);
                                                       user.put("numberPhone", phone);
                                                       user.put("gambar", urlGambar);
                                                       user.put("totalPoint", usr.getTotalPoint());
                                                       user.put("completeProfile", "false");
                                                       dbz.setValue(user);
                                                       // intent reward
                                                       Toast.makeText(context, "Profile tersimpan ,Lengkapi Profile dengan mendapatkan 5.000 point", Toast.LENGTH_SHORT).show();
                                                       Intent intent = new Intent(context, MasterActivity.class);
                                                       context.startActivity(intent);
                                                   }
                                               }else if (isComplete.equals("true")){
                                                   user.put("name", nama);
                                                   user.put("gender", spin);
                                                   user.put("age", ages);
                                                   user.put("address", addres);
                                                   user.put("email", emails);
                                                   user.put("number", phone);
                                                   user.put("gambar", urlGambar);
                                                   user.put("totalPoint", usr.getTotalPoint());
                                                   user.put("completeProfile", "false");
                                                   dbz.setValue(user);
                                                   // intent reward
                                                   Toast.makeText(context, "Profile tersimpan", Toast.LENGTH_SHORT).show();
                                                   Intent intent = new Intent(context, MasterActivity.class);
                                                   context.startActivity(intent);
                                               }
                                           }

                                           @Override
                                           public void onCancelled(@NonNull DatabaseError databaseError) {

                                           }
                                       });
                                   }
                               });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(context, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                        .getTotalByteCount());
                                progressDialog.setMessage("Uploaded " + (int) progress + "%");
                            }
                        });

                    }
                }else{
                    Intent toverifyphone = new Intent(context, VerifyPhoneActivity.class);
                    toverifyphone.putExtra("number", phone);
                    toverifyphone.putExtra("nameUser", nama);
                    toverifyphone.putExtra("emailnya", emails);
                    toverifyphone.putExtra("gender", spin);
                    toverifyphone.putExtra("age", ages);
                    toverifyphone.putExtra("address", addres);
                    toverifyphone.putExtra("gambar", filepath);
                    toverifyphone.putExtra("totalPoint", usr.getTotalPoint());
                    if (filepath == null) {
                        toverifyphone.putExtra("filepath", "");
                    } else {
                        toverifyphone.putExtra("filepath", filepath.toString());
                    }
                    toverifyphone.putExtra("completeProfile", usr.getCompleteProfile());
                    context.startActivity(toverifyphone);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }



}
