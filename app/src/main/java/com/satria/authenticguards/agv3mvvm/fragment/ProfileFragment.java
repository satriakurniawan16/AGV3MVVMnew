package com.satria.authenticguards.agv3mvvm.fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.satria.authenticguards.R;
import com.satria.authenticguards.agv3mvvm.View.LoginActivity;
import com.satria.authenticguards.agv3mvvm.model.User;
import com.satria.authenticguards.agv3mvvm.utils.PrivaciEndService;
import com.satria.authenticguards.agv3mvvm.utils.TermEndService;
import com.squareup.picasso.Picasso;

import java.net.URLEncoder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private View v;
    private Button logoutBtn;

    TextView editProfile,name,email,phone;
    ImageView profilePicture;

    RelativeLayout pmenu1,pmenu2,pmenu3,pmenu4,pmenu5,box1;

    private GoogleApiClient mGoogleSigninClient;
    private FirebaseAuth mAuth;
    private CallbackManager mCallbackManager;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FacebookSdk.sdkInitialize(getContext());
        v= inflater.inflate(R.layout.fragment_profile, container, false);
        mAuth=FirebaseAuth.getInstance();
        name=v.findViewById(R.id.textFname);
        email = v.findViewById(R.id.textFEmail);
        phone = v.findViewById(R.id.textFPhone);
        pmenu1= v.findViewById(R.id.pagemenu1);
        pmenu2 = v.findViewById(R.id.pmenu2);
        pmenu3 = v.findViewById(R.id.pmenu3);
        pmenu4 = v.findViewById(R.id.pmenu4);
        pmenu5 = v.findViewById(R.id.pmenu5);
        box1= v.findViewById(R.id.box_account1);

        profilePicture = v.findViewById(R.id.profile_imageprofile);
        logoutBtn=v.findViewById(R.id.logoutBtn);

        firebaseCheck();
        clickpmenu1();
        clickpmenu2();
        clickpmenu3();
        clickpmenu4();
        clickpmenu5();




        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        mCallbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void clickpmenu1(){
        pmenu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Tahap Pengembangan", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void clickpmenu2(){
        pmenu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),TermEndService.class);
                intent.putExtra("EXTRA_SESSION_ID","https://www.authenticguards.com/term/");
                startActivity(intent);
            }
        });
    }
    private void clickpmenu3(){
        pmenu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PrivaciEndService.class);
                intent.putExtra("EXTRA_SESSION_ID", "https://www.authenticguards.com/privacy-policy/");
                startActivity(intent);
            }
        });

    }
    private void clickpmenu4(){
        pmenu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String appPackageName = "com.agreader";
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });
    }
    private void clickpmenu5(){
        pmenu5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(inflater.inflate(R.layout.dialog_confirmprofile,null));
                TextView customer = (TextView)dialog.findViewById(R.id.customer);
                TextView merchants = (TextView)dialog.findViewById(R.id.merchants);

                customer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openWhatsappContact(getActivity(),"+6281717174112","");
                    }
                });

                merchants.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openWhatsappContact(getActivity(),"+6281717174113","");
                    }
                });
                dialog.show();
            }
        });
    }

    private void openWhatsappContact(Context context, String number, String message) {
        PackageManager packageManager=context.getPackageManager();
        Intent i =new Intent(Intent.ACTION_VIEW);
        try{
            String url = "https://api.whatsapp.com/send?phone="+ number +"&text=" + URLEncoder.encode(message, "UTF-8");
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager)!= null){
                context.startActivity(i);
            }else {
                Toast.makeText(context, "Anda belum menginstall aplikasi whatsapp", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void firebaseCheck(){
        final FirebaseUser currentUser=FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser!=null){
            DatabaseReference dbf=FirebaseDatabase.getInstance().getReference("user").child(currentUser.getUid());
            dbf.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User us = dataSnapshot.getValue(User.class);
                    if (us.getName().isEmpty()){
                        name.setText("NAME");
                    }else {
                        name.setText(us.getName());
                    }
                    if (us.getEmail().isEmpty()){
                        email.setText("EMAIL");
                    }else {
                        email.setText(us.getEmail());
                    }

                    if (us.getNumberPhone().isEmpty()){
                        phone.setText("PHONE");
                    }else {
                        phone.setText(us.getNumberPhone());
                    }
                    Picasso.get().load(us.getImage()).into(profilePicture);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            logoutBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseAuth.getInstance().signOut();
                    getActivity().finish();

                    if (mGoogleSigninClient!=null){
                        Auth.GoogleSignInApi.signOut(mGoogleSigninClient).setResultCallback(new ResultCallback<Status>() {
                            @Override
                            public void onResult(@NonNull Status status) {

                            }
                        });
                    }

                }
            });
        }
        else {
            box1.setVisibility(View.GONE);
            logoutBtn.setText("Login");
            logoutBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getContext(),LoginActivity.class));
                }
            });
        }
    }

}
