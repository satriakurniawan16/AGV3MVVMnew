package com.satria.authenticguards.agv3mvvm.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.satria.authenticguards.R;
import com.satria.authenticguards.agv3mvvm.View.DetailNotifActivity;
import com.satria.authenticguards.agv3mvvm.model.Notif;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotifAdapter extends RecyclerView.Adapter<NotifAdapter.ViewHolder> {
    private Context mContext;
    private List<Notif> mData;
    private String userImage = null;
    private int clickCount = 0;
    private View mEmptyView;

    public NotifAdapter(Context mContext, List<Notif> mData, View mEmptyView) {
        this.mContext = mContext;
        this.mData = mData;
        this.mEmptyView = mEmptyView;
    }
    public void updateEmptyView() {
        if (mData.size() == 0)
            mEmptyView.setVisibility(View.VISIBLE);
        else
            mEmptyView.setVisibility(View.GONE);
    }

    @NonNull
    @Override
    public NotifAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.listnotif,parent,false);
        return new NotifAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotifAdapter.ViewHolder holder, int position) {
        updateEmptyView();
        Picasso.get().load(mData.get(position).getImage()).placeholder(R.drawable.logo_ag).into(holder.image);
        holder.title.setText(mData.get(position).getTitle());
        holder.message.setText(mData.get(position).getMessage());
        holder.date.setText(mData.get(position).getDate());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickCount==0){
                    Context context = view.getContext();
                    Intent intent = new Intent(context, DetailNotifActivity.class);
                    intent.putExtra("id", mData.get(position).getId());
                    context.startActivity(intent);
                    clickCount ++;
                }else if (clickCount>=0){
                    Context context = view.getContext();
                    Intent intent = new Intent(context, DetailNotifActivity.class);
                    intent.putExtra("id", mData.get(position).getId());
                    context.startActivity(intent);
                    holder.badge.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (mData!=null) ? mData.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView image,badge;
        private RelativeLayout relativeLayout;
        private TextView title,message,date;
        public ViewHolder(View view){
            super(view);
            relativeLayout=view.findViewById(R.id.relative_notif_card);
            image = (ImageView) itemView.findViewById(R.id.image_notif);
            badge = (ImageView) itemView.findViewById(R.id.badge_notif);
            title = (TextView) itemView.findViewById(R.id.title_notif);
            message = (TextView) itemView.findViewById(R.id.message_notif);
            date = (TextView) itemView.findViewById(R.id.date_notif);
        }

    }

}
