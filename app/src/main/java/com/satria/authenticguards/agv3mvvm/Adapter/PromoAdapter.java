package com.satria.authenticguards.agv3mvvm.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.makeramen.roundedimageview.RoundedImageView;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.satria.authenticguards.R;
import com.satria.authenticguards.agv3mvvm.View.DetailNotifActivity;
import com.satria.authenticguards.agv3mvvm.View.DetailPointActivity;
import com.satria.authenticguards.agv3mvvm.model.Promo;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PromoAdapter extends RecyclerView.Adapter<PromoAdapter.ViewHolder> {
    private PromoAdapter.ClickHandler mClickHandler;
    private Context mContext;
    private ArrayList<Promo> mData;
    private ArrayList<String> mDataId;
    private ArrayList<String> mSelectedId;

    public PromoAdapter(Context mContext, ArrayList<Promo> mData, ArrayList<String> mDataId) {
        this.mContext = mContext;
        this.mData = mData;
        this.mDataId = mDataId;
        mSelectedId = new ArrayList<>();
    }

    @NonNull
    @Override
    public PromoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.listitempromo,parent,false);
        return new PromoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PromoAdapter.ViewHolder holder, int position) {
        final Promo promo=mData.get(position%mData.size());
        Log.d("PromoAdapter", "onBindViewHolder: "+promo.getGambar());
        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.TRANSPARENT)
                .borderWidthDp(0)
                .cornerRadiusDp(10)
                .oval(false)
                .build();
        Picasso.get().load(promo.getGambar()).fit()
                .transform(transformation).into(holder.mImg);

        holder.mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentDetailPoint = new Intent(mContext,DetailPointActivity.class);
                intentDetailPoint.putExtra("postId", promo.getIdHadiah());
                intentDetailPoint.putExtra("title", promo.getJudul());
                intentDetailPoint.putExtra("gambar", promo.getGambar());
                intentDetailPoint.putExtra("price", promo.getTotalPoint());
                intentDetailPoint.putExtra("availablePoint", promo.getExpired());
                intentDetailPoint.putExtra("descriptionPoint", promo.getDesc());
                intentDetailPoint.putExtra("termC", promo.getTermC());
                mContext.startActivity(intentDetailPoint);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size() * 2;
    }
    public interface ClickHandler {
        void onItemClick(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        final RoundedImageView mImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImg = itemView.findViewById(R.id.gambarPromo);
            itemView.setFocusable(true);

        }
    }
}
