package com.satria.authenticguards.agv3mvvm.Screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.satria.authenticguards.R;
import com.satria.authenticguards.agv3mvvm.Adapter.SectionAdapter;
import com.satria.authenticguards.agv3mvvm.View.LoginActivity;
import com.satria.authenticguards.agv3mvvm.model.User;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MasterActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private MenuItem prevMenuItem;
    private TextView point;
    private RelativeLayout goPoint;
    private RelativeLayout goLogin;

    private BottomNavigationViewEx bottomNavigationViewEx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);
        point=findViewById(R.id.point);
        goPoint=findViewById(R.id.toPoint);
        goLogin=findViewById(R.id.toLogin);
        loadData();
        goLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MasterActivity.this,LoginActivity.class));
            }
        });
        goPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(MasterActivity.this,PointActivity.class));
            }
        });

        viewPager=findViewById(R.id.viewpager_master);
        bottomNavigationViewEx=findViewById(R.id.bottom_nav_view);
        SectionAdapter adapter=new SectionAdapter(this,getSupportFragmentManager());
        viewPager.setAdapter(adapter);

//        bottomNavigationViewEx.setIconSize(20, 20);
//        bottomNavigationViewEx.setTextSize(12);
//        bottomNavigationViewEx.enableAnimation(false);
//        bottomNavigationViewEx.setTextVisibility(true);
//        bottomNavigationViewEx.enableShiftingMode(false);
//        bottomNavigationViewEx.enableItemShiftingMode(false);
//        bottomNavigationViewEx.setIconTintList(0, getResources().getColorStateList(R.color.colorGold));
//        bottomNavigationViewEx.setTextTintList(0, getResources().getColorStateList(R.color.colorGold));

        bottomNavigationViewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               switch (item.getItemId()){
                   case R.id.ic_home:
                       viewPager.setCurrentItem(0);
//                       bottomNavigationViewEx.setIconTintList(0, getResources().getColorStateList(R.color.colorGold));
//                       bottomNavigationViewEx.setTextTintList(0, getResources().getColorStateList(R.color.colorGold));
//                       bottomNavigationViewEx.setIconTintList(1, getResources().getColorStateList(R.color.colorGREY));
//                       bottomNavigationViewEx.setTextTintList(1, getResources().getColorStateList(R.color.colorGREY));
//                       bottomNavigationViewEx.setIconTintList(2, getResources().getColorStateList(R.color.colorGREY));
//                       bottomNavigationViewEx.setTextTintList(2, getResources().getColorStateList(R.color.colorGREY));
//                       bottomNavigationViewEx.setIconTintList(3, getResources().getColorStateList(R.color.colorGREY));
//                       bottomNavigationViewEx.setTextTintList(3, getResources().getColorStateList(R.color.colorGREY));
//                       bottomNavigationViewEx.setIconTintList(4, getResources().getColorStateList(R.color.colorGREY));
//                       bottomNavigationViewEx.setTextTintList(4, getResources().getColorStateList(R.color.colorGREY));
                       break;
                   case R.id.ic_product:
                       viewPager.setCurrentItem(1);
//                       bottomNavigationViewEx.setIconTintList(0, getResources().getColorStateList(R.color.colorGREY));
//                       bottomNavigationViewEx.setTextTintList(0, getResources().getColorStateList(R.color.colorGREY));
//                       bottomNavigationViewEx.setIconTintList(1, getResources().getColorStateList(R.color.colorGold));
//                       bottomNavigationViewEx.setTextTintList(1, getResources().getColorStateList(R.color.colorGold));
//                       bottomNavigationViewEx.setIconTintList(2, getResources().getColorStateList(R.color.colorGREY));
//                       bottomNavigationViewEx.setTextTintList(2, getResources().getColorStateList(R.color.colorGREY));
//                       bottomNavigationViewEx.setIconTintList(3, getResources().getColorStateList(R.color.colorGREY));
//                       bottomNavigationViewEx.setTextTintList(3, getResources().getColorStateList(R.color.colorGREY));
//                       bottomNavigationViewEx.setIconTintList(4, getResources().getColorStateList(R.color.colorGREY));
//                       bottomNavigationViewEx.setTextTintList(4, getResources().getColorStateList(R.color.colorGREY));
                       break;
                   case R.id.ic_qrcode:
                       viewPager.setCurrentItem(2);
//                       bottomNavigationViewEx.setIconTintList(0, getResources().getColorStateList(R.color.colorGREY));
//                       bottomNavigationViewEx.setTextTintList(0, getResources().getColorStateList(R.color.colorGREY));
//                       bottomNavigationViewEx.setIconTintList(1, getResources().getColorStateList(R.color.colorGREY));
//                       bottomNavigationViewEx.setTextTintList(1, getResources().getColorStateList(R.color.colorGREY));
//                       bottomNavigationViewEx.setIconTintList(2, getResources().getColorStateList(R.color.colorGold));
//                       bottomNavigationViewEx.setTextTintList(2, getResources().getColorStateList(R.color.colorGold));
//                       bottomNavigationViewEx.setIconTintList(3, getResources().getColorStateList(R.color.colorGREY));
//                       bottomNavigationViewEx.setTextTintList(3, getResources().getColorStateList(R.color.colorGREY));
//                       bottomNavigationViewEx.setIconTintList(4, getResources().getColorStateList(R.color.colorGREY));
//                       bottomNavigationViewEx.setTextTintList(4, getResources().getColorStateList(R.color.colorGREY));
                       break;
                   case R.id.ic_notification:
                       viewPager.setCurrentItem(3);
//                       bottomNavigationViewEx.setIconTintList(0, getResources().getColorStateList(R.color.colorGREY));
//                       bottomNavigationViewEx.setTextTintList(0, getResources().getColorStateList(R.color.colorGREY));
//                       bottomNavigationViewEx.setIconTintList(1, getResources().getColorStateList(R.color.colorGREY));
//                       bottomNavigationViewEx.setTextTintList(1, getResources().getColorStateList(R.color.colorGREY));
//                       bottomNavigationViewEx.setIconTintList(2, getResources().getColorStateList(R.color.colorGREY));
//                       bottomNavigationViewEx.setTextTintList(2, getResources().getColorStateList(R.color.colorGREY));
//                       bottomNavigationViewEx.setIconTintList(3, getResources().getColorStateList(R.color.colorGold));
//                       bottomNavigationViewEx.setTextTintList(3, getResources().getColorStateList(R.color.colorGold));
//                       bottomNavigationViewEx.setIconTintList(4, getResources().getColorStateList(R.color.colorGREY));
//                       bottomNavigationViewEx.setTextTintList(4, getResources().getColorStateList(R.color.colorGREY));
                       break;
                   case R.id.ic_profile:
                       viewPager.setCurrentItem(4);
//                       bottomNavigationViewEx.setIconTintList(0, getResources().getColorStateList(R.color.colorGREY));
//                       bottomNavigationViewEx.setTextTintList(0, getResources().getColorStateList(R.color.colorGREY));
//                       bottomNavigationViewEx.setIconTintList(1, getResources().getColorStateList(R.color.colorGREY));
//                       bottomNavigationViewEx.setTextTintList(1, getResources().getColorStateList(R.color.colorGREY));
//                       bottomNavigationViewEx.setIconTintList(2, getResources().getColorStateList(R.color.colorGREY));
//                       bottomNavigationViewEx.setTextTintList(2, getResources().getColorStateList(R.color.colorGREY));
//                       bottomNavigationViewEx.setIconTintList(3, getResources().getColorStateList(R.color.colorGREY));
//                       bottomNavigationViewEx.setTextTintList(3, getResources().getColorStateList(R.color.colorGREY));
//                       bottomNavigationViewEx.setIconTintList(4, getResources().getColorStateList(R.color.colorGold));
//                       bottomNavigationViewEx.setTextTintList(4, getResources().getColorStateList(R.color.colorGold));
                       break;
               }
               return false;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem!=null){
                    prevMenuItem.setChecked(false);
                }else{
                    bottomNavigationViewEx.getMenu().getItem(0).setChecked(false);
                }
                bottomNavigationViewEx.getMenu().getItem(position).setChecked(true);
                prevMenuItem=bottomNavigationViewEx.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void loadData(){
        FirebaseAuth mFirebaseAuth;
        mFirebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser cureentUser=mFirebaseAuth.getCurrentUser();

        if (cureentUser!=null){
            goLogin.setVisibility(View.GONE);
            goPoint.setVisibility(View.VISIBLE);

            cureentUser=FirebaseAuth.getInstance().getCurrentUser();
            final DatabaseReference dbr= FirebaseDatabase.getInstance().getReference("user").child(cureentUser.getUid());
            dbr.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User users=dataSnapshot.getValue(User.class);
                    try{
                        String points=users.getTotalPoint();
                        double parsePoints= Double.parseDouble(points);
                        NumberFormat numberFormat=new DecimalFormat("#,###");
                        String formatedNumber=numberFormat.format(parsePoints);
                        if (users.getTotalPoint()!=null)point.setText(formatedNumber+" pts");
                        else point.setText("0 pts");
                    }catch (NullPointerException e){
                        Log.e("MasterActivity Firebase", "onDataChange: ",e );
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
