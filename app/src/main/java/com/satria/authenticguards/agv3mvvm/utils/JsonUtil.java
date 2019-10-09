package com.satria.authenticguards.agv3mvvm.utils;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.satria.authenticguards.agv3mvvm.model.Notif;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class JsonUtil {

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

//    private void getDataSliderHome(List<String > imageUrls){
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://admin.authenticguards.com/api/slider_?&appid=003&loclang=a&loclong=a", null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//
//            }
//        })
//    }
}
