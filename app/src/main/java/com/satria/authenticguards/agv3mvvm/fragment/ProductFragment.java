package com.satria.authenticguards.agv3mvvm.fragment;


import android.content.Intent;
import android.drm.DrmStore;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.satria.authenticguards.R;
import com.satria.authenticguards.agv3mvvm.Adapter.MyProductAdapter;
import com.satria.authenticguards.agv3mvvm.View.MyProductDetail;
import com.satria.authenticguards.agv3mvvm.model.ProductModel;
import com.satria.authenticguards.agv3mvvm.utils.CustomClickListener;
import com.satria.authenticguards.agv3mvvm.utils.JsonUtil;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment {
    FirebaseUser firebaseUser;
    private LinearLayout emptyView,emptyViewLogin;
    View rootView;
    private ArrayList<ProductModel> prodcut;
    private MyProductAdapter productAdapter;
    private String token = "", finalImage = "", name_product = "", name_brand = "", date_claim_product = "", image_product = "", status = "";
    private String size, color, material, price, distributor, expiredDate, alamat, imageBrand, finalImage2;
    private RecyclerView recyclerView;
    JsonUtil jsonUtil=new JsonUtil();

    public ProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_product, container, false);
        setUpView(rootView);
        generateView(rootView);
        return rootView;
    }

    private void generateView(View rootView) {
        if (firebaseUser!=null){
            emptyViewLogin.setVisibility(View.GONE);
            try{
                firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
                assert firebaseUser!=null;
                firebaseUser.getIdToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    @Override
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        try{
                            token=Objects.requireNonNull(task.getResult().getToken());
                            jsonUtil.getDataProduct(getContext(),token,prodcut,emptyView,productAdapter);
                        }catch (NullPointerException ignored){
                            Toast.makeText(getContext(), "Check your connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }catch (Exception e){
                Toast.makeText(getContext(), "Check your connection", Toast.LENGTH_SHORT).show();
            }
        }else{
            emptyViewLogin.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
        toAdapter();

    }

    private void setUpView(View rootView) {
        prodcut=new ArrayList<>();
        emptyView=rootView.findViewById(R.id.emptyView);
        emptyViewLogin=rootView.findViewById(R.id.empty_view_login);
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        recyclerView=rootView.findViewById(R.id.listProduct);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

    }

    private void toAdapter(){
        productAdapter=new MyProductAdapter(getContext(), prodcut, new CustomClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                final ProductModel productModel = prodcut.get(position);
                Intent detail_intent = new Intent(getContext(), MyProductDetail.class);
                detail_intent.putExtra("namaProduk", prodcut.get(position).getNameProduct());
                detail_intent.putExtra("size", productModel.getSize());
                detail_intent.putExtra("color", productModel.getColor());
                detail_intent.putExtra("material", productModel.getMaterial());
                detail_intent.putExtra("price", productModel.getPrice());
                detail_intent.putExtra("distributor", productModel.getDistributor());
                detail_intent.putExtra("expiredDate", productModel.getExpiredDate());
                detail_intent.putExtra("image", prodcut.get(position).getImageProduct());
                detail_intent.putExtra("nama_brand", prodcut.get(position).getBrand());
                detail_intent.putExtra("alamat_brand", productModel.getAlamatBrand());
                detail_intent.putExtra("logo_brand", productModel.getLogoBrand());
                startActivity(detail_intent);
            }
        });
        recyclerView.setAdapter(productAdapter);
    }

}
