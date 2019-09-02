package com.satria.authenticguards.agv3mvvm.utils;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.satria.authenticguards.agv3mvvm.Model.User;

import java.util.HashMap;

public class FirebaseUtil {

    FirebaseAuth mAuth ;
    FirebaseUser mUser;

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

}
