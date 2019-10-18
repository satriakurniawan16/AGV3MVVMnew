package com.satria.authenticguards.agv3mvvm.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.satria.authenticguards.R;
import com.satria.authenticguards.agv3mvvm.model.ProductModel;
import com.satria.authenticguards.agv3mvvm.utils.CustomClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyProductAdapter extends RecyclerView.Adapter<MyProductAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ProductModel> mData;
    private CustomClickListener listener;

    public MyProductAdapter(Context context, ArrayList<ProductModel> mData, CustomClickListener listener) {
        this.context = context;
        this.mData = mData;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.list_item_product,parent,false);
        final MyProductAdapter.ViewHolder mholder=new MyProductAdapter.ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(view,mholder.getPosition());
            }
        });
        return mholder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyProductAdapter.ViewHolder holder, int position) {
        holder.product.setText(mData.get(position).getNameProduct());
        holder.brand.setText(mData.get(position).getBrand());
        holder.date.setText(mData.get(position).getDateProduct());
        if (mData.get(position).getStatus().equals("on_review")){
            holder.img_status.setImageResource(R.drawable.ic_time);
            holder.status.setText("Pending");
        }else {
            holder.img_status.setImageResource(R.drawable.crop__ic_done);
            holder.status.setText("Disetujui");
        }
        Picasso.get().load(mData.get(position).getImageProduct()).fit().into(holder.img_product);

    }

    @Override
    public int getItemCount() {
        return (mData!=null) ? mData.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView product,brand,date,status;
        private ImageView img_status,img_product;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            product=itemView.findViewById(R.id.namaProduct);
            brand=itemView.findViewById(R.id.namaBrandProduct);
            date=itemView.findViewById(R.id.tanggalClaimProduct);
            status=itemView.findViewById(R.id.status_text);
            img_status=itemView.findViewById(R.id.status);
            img_product=itemView.findViewById(R.id.gambarProduct);

        }
    }
}
