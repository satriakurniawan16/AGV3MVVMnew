package com.satria.authenticguards.agv3mvvm.utils;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.JsonArray;
import com.satria.authenticguards.R;
import com.satria.authenticguards.agv3mvvm.model.Notif;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ViewListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class JsonUtil {
    Context context;
    public List<String> imgUrls= new ArrayList<>();

    public JsonUtil(){}

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
                    carouselView.setPageCount(imgUrls.size());
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
}
