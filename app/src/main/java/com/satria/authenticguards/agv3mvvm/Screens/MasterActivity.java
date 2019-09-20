package com.satria.authenticguards.agv3mvvm.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.satria.authenticguards.R;
import com.satria.authenticguards.agv3mvvm.View.LoginActivity;

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

    }
}
