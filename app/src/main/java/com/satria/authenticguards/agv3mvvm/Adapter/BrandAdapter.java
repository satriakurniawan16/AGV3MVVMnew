package com.satria.authenticguards.agv3mvvm.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Transformation;

import com.makeramen.roundedimageview.RoundedImageView;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.satria.authenticguards.R;
import com.satria.authenticguards.agv3mvvm.model.Brand;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.ViewHolder> {
    private BrandAdapter.ClickHandler mClickHandler;
    private Context mContext;
    private ArrayList<Brand> mData;
    private ArrayList<String> mDataId;
    private ArrayList<String> mSelectedId;

    public BrandAdapter(ClickHandler mClickHandler, Context mContext, ArrayList<Brand> mData, ArrayList<String> mDataId) {
        this.mClickHandler = mClickHandler;
        this.mContext = mContext;
        this.mData = mData;
        this.mDataId = mDataId;
    }

    @NonNull
    @Override
    public BrandAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.listitembrand,parent,false);
        return new BrandAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BrandAdapter.ViewHolder holder, int position) {
        Brand brand=mData.get(position);
        Log.d("Brand Adapter", "onBindViewHolder: "+brand.getImage());

        com.squareup.picasso.Transformation transformation=new RoundedTransformationBuilder()
                .borderColor(Color.TRANSPARENT)
                .borderWidthDp(0)
                .cornerRadiusDp(10)
                .oval(false)
                .build();
        Picasso.get().load(brand.getImage()).fit().transform(transformation).into(holder.rImg);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    public void toggleSelection(String dataId) {
        if (mSelectedId.contains(dataId))
            mSelectedId.remove(dataId);
        else
            mSelectedId.add(dataId);
        notifyDataSetChanged();
    }
    public int selectionCount() {
        return mSelectedId.size();
    }

    public void resetSelection() {
        mSelectedId = new ArrayList<>();
        notifyDataSetChanged();
    }

    public ArrayList<String> getSelectedId() {
        return mSelectedId;
    }
    public interface ClickHandler {
        void onItemClick(int position);
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        final RoundedImageView rImg;

        ViewHolder(View view){
            super(view);
            rImg=view.findViewById(R.id.logo_brand);
            view.setFocusable(true);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mClickHandler.onItemClick(getAdapterPosition());
        }
    }
}
