package com.satria.authenticguards.agv3mvvm.Adapter;

import android.content.Context;

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

        return null;
    }

    @Override
    public int getCount() {
        return 5;
    }
}
