package com.satria.authenticguards.agv3mvvm.Adapter;

import android.content.Context;

import com.satria.authenticguards.agv3mvvm.fragment.HomeFragment;
import com.satria.authenticguards.agv3mvvm.fragment.NotifFragment;
import com.satria.authenticguards.agv3mvvm.fragment.ProductFragment;
import com.satria.authenticguards.agv3mvvm.fragment.ProfileFragment;
import com.satria.authenticguards.agv3mvvm.fragment.QRcodeFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SectionAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public SectionAdapter(Context context, FragmentManager fm){
        super(fm);
        mContext=context;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position==0)return new HomeFragment();
        else if (position==1)return new ProductFragment();
        else if (position==2)return new QRcodeFragment();
        else if (position==3)return new NotifFragment();
        else return new ProfileFragment();
    }

    @Override
    public int getCount() {
        return 5;
    }
}
