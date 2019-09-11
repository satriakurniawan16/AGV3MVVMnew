package com.satria.authenticguards.agv3mvvm;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.satria.authenticguards.R;
import com.satria.authenticguards.agv3mvvm.Screens.MasterActivity;
import com.satria.authenticguards.agv3mvvm.View.LoginActivity;
import com.satria.authenticguards.agv3mvvm.View.RegisterActivity;
import com.satria.authenticguards.agv3mvvm.dataBinding.LoginHandler;
import com.satria.authenticguards.agv3mvvm.model.User;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {
    private Button btnRegister,btnLogin;
    LinearLayout btnGmail,btnEmail;
    TextView tos,privacy;
    private SlidingUpPanelLayout slidingUpPanelLayout;

    String gambar,noTlfon,gender,age,address,nama;
    String totalPoint="10000";

    private static final int RC_SIGN=9001;
    private GoogleApiClient mGoogleapi;
    private FirebaseAuth mAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inisisalisasi View
        btnRegister=findViewById(R.id.btnRegister);
        btnLogin=findViewById(R.id.btnLogin);
        btnGmail=findViewById(R.id.login_gmail);
        btnEmail=findViewById(R.id.login_email);
        tos=findViewById(R.id.tos);
        privacy=findViewById(R.id.privacy);
        slidingUpPanelLayout=findViewById(R.id.slidingup_main);
        mAuth=FirebaseAuth.getInstance();

        //Google sign option
        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                requestIdToken(getString(R.string.default_web_client_id)).
                requestEmail().build();
        mGoogleapi=new GoogleApiClient.Builder(MainActivity.this).
                enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(MainActivity.this, "Failed During login with Gmail", Toast.LENGTH_SHORT).show();
                    }
                }).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();

        //Config View
        slidingUpPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {

            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
            }
        });
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        btnGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signWithGoogle();
            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();
//        FirebaseUser currentUser= mAuth.getCurrentUser();
//        if (currentUser!=null){
//            if (currentUser.isEmailVerified())startActivity(new Intent(this,MasterActivity.class));
//        }else{
//
//        }
    }

    private void signWithGoogle(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleapi);
        startActivityForResult(signInIntent, RC_SIGN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RC_SIGN){
            GoogleSignInResult result=Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()){
                GoogleSignInAccount account=result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            }else
                mAuth.signOut();
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account){
        Log.e("MainActivity", "firebaseAuthWithGoogle: "+account.getId());
        pgShow();
        AuthCredential credential= GoogleAuthProvider.getCredential(account.getIdToken(),null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    final FirebaseUser currentUser=mAuth.getCurrentUser();
                    final HashMap<String ,Object> hashUser=new HashMap<>();
                    gambar=currentUser.getPhotoUrl().toString();
                    nama=currentUser.getDisplayName();
                    final DatabaseReference db=FirebaseDatabase.getInstance().getReference("user").child(currentUser.getUid());
                    db.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()){
                                startActivity(new Intent(MainActivity.this,MasterActivity.class));
                            }else {
                                User user=dataSnapshot.getValue(User.class);
                                if (dataSnapshot.child("gambar").exists()){
                                    gambar=user.getImage();
                                }if (dataSnapshot.child("numberPhone").exists()){
                                    noTlfon=user.getNumberPhone();
                                }if (dataSnapshot.child("name").exists()){
                                    nama=user.getName();
                                }if (dataSnapshot.child("gender").exists()){
                                    gender=user.getGender();
                                }if (dataSnapshot.child("age").exists()){
                                    age=user.getAge();
                                }if (dataSnapshot.child("address").exists()){
                                    address=user.getAddress();
                                }if (dataSnapshot.child("totalPoint").exists()){
                                    totalPoint=user.getTotalPoint();
                                }
                                hashUser.put("numberPhone",noTlfon);
                                hashUser.put("idEmail",currentUser.getUid());
                                hashUser.put("idPhone","");
                                hashUser.put("name",nama);
                                hashUser.put("email",currentUser.getEmail());
                                hashUser.put("gender",gender);
                                hashUser.put("age",age);
                                hashUser.put("address",address);
                                hashUser.put("gambar",gambar);
                                hashUser.put("totalPoint",totalPoint);
                                hashUser.put("completeProfile","false");
                                hashUser.put("onverifiednumber", "false");
                                db.setValue(user);
                                Intent pindah = new Intent(MainActivity.this,MasterActivity.class);
                                startActivity(pindah);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }else{
                    Log.e("Google", "onFailure: ", task.getException());
                }
            }
        });
    }

    private void pgShow(){
        ProgressDialog pg=new ProgressDialog(this);
        pg.setMessage("Wait..Account Verifying");
        pg.setIndeterminate(false);
        pg.setCancelable(false);
        pg.show();


    }

}
