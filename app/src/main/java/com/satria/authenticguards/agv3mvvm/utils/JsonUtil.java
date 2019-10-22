package com.satria.authenticguards.agv3mvvm.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.satria.authenticguards.R;
import com.satria.authenticguards.agv3mvvm.Adapter.BrandAdapter;
import com.satria.authenticguards.agv3mvvm.Adapter.MyProductAdapter;
import com.satria.authenticguards.agv3mvvm.Adapter.PromoAdapter;
import com.satria.authenticguards.agv3mvvm.View.UnverifiedProductActivity;
import com.satria.authenticguards.agv3mvvm.View.VerifiedProductActivity;
import com.satria.authenticguards.agv3mvvm.model.Brand;
import com.satria.authenticguards.agv3mvvm.model.Notif;
import com.satria.authenticguards.agv3mvvm.model.ProductModel;
import com.satria.authenticguards.agv3mvvm.model.Promo;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ViewListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class JsonUtil {
    Context context;
    public List<String> imgUrls= new ArrayList<>();
    public ArrayList<Brand> brands=new ArrayList<>();
    public ArrayList<Promo> promos=new ArrayList<>();
    public JsonUtil(){}

    //for json in notification fragments
    public void getDataNotif(Context context, RecyclerView.Adapter adapter, List<Notif> notifList){
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, "https://admin.authenticguards.com/api/notification/?appid=003", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response.length() > 0) {
                    try {
                        JSONObject jsonObject = response.getJSONObject("result");
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject data = jsonArray.getJSONObject(i);
                            Notif notif=new Notif();
                            notif.setId(data.getString("id"));
                            notif.setTitle(data.getString("title"));
                            notif.setMessage(data.getString("message"));
                            notif.setImage("https://admin.authenticguards.com/storage/app/public/notif/"+data.getString("image")+".jpg");
                            notif.setDate(data.getString("created_at"));
                            notifList.add(notif);

                            Log.e("JsonUtils", "onResponse: gagal atau berhasil");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }
    //for json in homefragment until
    public void getDataSliderHome(Context context, ViewListener view, CarouselView carouselView, ShimmerFrameLayout shimmerFrameLayout){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://admin.authenticguards.com/api/slider_?&appid=003&loclang=a&loclong=a", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject = response.getJSONObject("result");
                    JSONArray results = (JSONArray) jsonObject.get("data");
                    Log.d("getdataslider", "onResponse: "+results);
                    Log.d("getdatalistslider", "onResponse: "+imgUrls);
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject data = results.getJSONObject(i);
                        String image = data.getString("image");
                        String finalImage = "https://admin.authenticguards.com/storage/" + image + ".jpg";
                        imgUrls.add(finalImage);
                    }
                    carouselView.setViewListener(view);
                    carouselView.setPageCount(3);
                    shimmerFrameLayout.stopShimmerAnimation();

                    Log.d("getdataimageurls", "onResponse: "+imgUrls);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }

    public void getBrandHome(Context context, BrandAdapter adapter, ProgressBar progressBar){
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, "https://admin.authenticguards.com/api/feature?appid=003", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response.length()>0){
                    try{
                        JSONObject jsonObject=response.getJSONObject("result");
                        JSONArray jsonArray=jsonObject.getJSONArray("data");
                        for (int i = 0; i <5 ; i++) {
                            JSONObject data=jsonArray.getJSONObject(i);
                            int id=data.getInt("id");
                            String idString = String.valueOf(id);
                            String image = data.getString("image");
                            String name = data.getString("Name");
                            JSONObject brand = data.getJSONObject("client");
                            brands.add(new Brand(idString,name,"https://admin.authenticguards.com/storage/" + image + ".jpg"));

                        }
                        adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);

                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }

    public void getPromoHome(Context context, String token, PromoAdapter promoAdapter,ProgressBar progressBar){
        String url = "https://admin.authenticguards.com/api/promo_?token=" + token + "&appid=003";
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONObject json = response.getJSONObject("result");
                    JSONArray jsonArray = json.getJSONArray("data");
                    for (int i = 0; i <jsonArray.length() ; i++) {
                        JSONObject data=jsonArray.getJSONObject(i);
                        int id=data.getInt("id");
                        String idx = String.valueOf(id);
                        String image = data.getString("image");
                        final String title = data.getString("title");
                        final String price = data.getString("price");
                        final String time = data.getString("time");
                        final String desc = data.getString("description");
                        final String termC = data.getString("termCondition");
                        final String tanggal = time.substring(0, 10);
                        final String harga = price.substring(6, 9);
                        String finalImage="https://admin.authenticguards.com/storage/" + image + ".jpg";
                        promos.add(new Promo(idx,finalImage,title,harga,"5",tanggal,desc,termC));

                    }
                    promoAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.INVISIBLE);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }
    //here bro

    //for json in myproduct fragments
    public void getDataProduct(Context context, String token, ArrayList<ProductModel> productModels, LinearLayout emptyView, MyProductAdapter adapter){
        String url = "https://admin.authenticguards.com/api/myproduct_?token=" + token + "&appid=003";
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response.length()>0){
                    try{
                        JSONObject jsonObject=response.getJSONObject("result");
                        JSONArray results=(JSONArray)jsonObject.get("data");
                        for (int i = 0; i <results.length() ; i++) {
                            JSONObject data=results.getJSONObject(i);
                            String tanggal=data.getString("created_at");
                            String date_claim_product=tanggal.substring(0,10);
                            String status=data.getString("statusClaim");
                            JSONObject produk=(JSONObject)data.get("product");
                            String name_product=produk.getString("nama");
                            String size=produk.getString("size");
                            String color=produk.getString("color");
                            String material=produk.getString("material");
                            String price=produk.getString("price");
                            String distributor=produk.getString("distributedOn");
                            String expiredDate = produk.getString("expireDate");
                            String image_product = produk.getString("image");
                            String finalImageProduct="https://admin.authenticguards.com/product/" + image_product + ".jpg";

                            JSONObject brand = (JSONObject) produk.get("brand");
                            String name_brand = brand.getString("Name");
                            String alamat = brand.getString("addressOfficeOrStore");
                            String imageBrand = brand.getString("image");
                            String finalImageBrand="https://admin.authenticguards.com/storage/app/public/" + imageBrand + ".jpg";
                            productModels.add(new ProductModel(finalImageProduct, name_product, name_brand, date_claim_product, status, size, color, material, price, distributor, expiredDate, alamat, finalImageBrand));
                        }
                        if (productModels.size()==0) emptyView.setVisibility(View.VISIBLE);
                        else emptyView.setVisibility(View.GONE);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        });
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }

    //for json in QRcodeActivity
    public void validation_codeQR(Context context, String scanCode, String token, double latitude, double  longitude, ProgressDialog progressDialog){

        String url="https://admin.authenticguards.com/api/check_/"+scanCode+"?token="+token+"&appid=003&loclang="+latitude+"&loclong="+longitude;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String rvalid="";
                String size="",color="",price="",material="",distributor="",expiredDate="",img="",GCODE="";
                String brand = "",company="",address="",phone="",email="",web="";
                if (response.length()>0){
                    for (int i = 0; i <response.length() ; i++) {
                        try{
                            rvalid = response.getString("status");
                            JSONObject jsonObject=response.getJSONObject("result");
                            if (!jsonObject.isNull("product")){
                                JSONObject resultProduct= jsonObject.getJSONObject("product");
                                size = resultProduct.getString("size");
                                color = resultProduct.getString("color");
                                price = resultProduct.getString("price");
                                material = resultProduct.getString("material");
                                distributor = resultProduct.getString("distributedOn");
                                expiredDate = resultProduct.getString("expireDate");
                                img = resultProduct.getString("image");
                                GCODE = jsonObject.getString("code");

                                JSONObject dataclient = jsonObject.getJSONObject("client");
                                JSONObject data = jsonObject.getJSONObject("brand");
                                brand = data.getString("Name");
                                company = dataclient.getString("name");
                                address = data.getString("addressOfficeOrStore");
                                phone = data.getString("csPhone");
                                email = data.getString("csEmail");
                                web = data.getString("web");
                            }else{
                                JSONObject newresultObject = jsonObject.getJSONObject("package_code");
                                JSONObject brandnew = jsonObject.getJSONObject("brand");
                                JSONObject clientnew = jsonObject.getJSONObject("client");
                                size = "unregistered";
                                color = "unregistered";
                                price = "unregistered";
                                material = "unregistered";
                                distributor = "unregistered";
                                expiredDate = "unregistered";
                                img = "unregistered";
                                GCODE = jsonObject.getString("code");
                                brand = brandnew.getString("Name");
                                company = clientnew.getString("name");
                                address = clientnew.getString("address");
                                phone = clientnew.getString("phone");
                                email = clientnew.getString("email");
                                web = clientnew.getString("web");
                            }
                        }catch (JSONException e){

                        }
                    }
                    if (rvalid.equals("success")){
                        progressDialog.dismiss();
                        Intent intent_geniune=new Intent(context,VerifiedProductActivity.class);
                        intent_geniune.putExtra("key", scanCode);
                        intent_geniune.putExtra("brand", brand);
                        intent_geniune.putExtra("company", company);
                        intent_geniune.putExtra("address", address);
                        intent_geniune.putExtra("phone", phone);
                        intent_geniune.putExtra("email", email);
                        intent_geniune.putExtra("web", web);

                        intent_geniune.putExtra("code", GCODE);

                        intent_geniune.putExtra("size",size);
                        intent_geniune.putExtra("color",color);
                        intent_geniune.putExtra("material",material);
                        intent_geniune.putExtra("price",price);
                        intent_geniune.putExtra("distributor",distributor);
                        intent_geniune.putExtra("expiredDate",expiredDate);
                        intent_geniune.putExtra("image",img);
                        context.startActivity(intent_geniune);
                    }else {
                        progressDialog.dismiss();
                        context.startActivity(new Intent(context,UnverifiedProductActivity.class));
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Wrong Code, Check your connection", Toast.LENGTH_SHORT).show();
                Intent intent_fake = new Intent(context, UnverifiedProductActivity.class);
                context.startActivity(intent_fake);
            }
        });
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }
}
