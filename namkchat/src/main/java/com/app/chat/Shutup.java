package com.app.chat;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Shutup {
    public static final String urll = "http://besosdeamor.info/chat/rudeness/api.php";

    public Shutup(Context m, final onLoadComplete list){

        RequestQueue queue = Volley.newRequestQueue(m);
        //Log.e("MAIN", "Shutup: "+urll);
        StringRequest request = new StringRequest(Request.Method.GET, urll, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
list.ReadyToGo(getGroser(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
list.Failed(error.toString());
            }
        });

        queue.add(request);

    }


    public interface onLoadComplete{
        void ReadyToGo(ArrayList<String> groseriasBlock);

        void Failed(String erno);
    }

    private ArrayList<String> getGroser(String r){
        ArrayList<String> listing = new ArrayList<>();

        try {
            JSONObject s = new JSONObject(r);

            JSONArray rrarai = s.getJSONArray("rudness");

            for(int i=0; i < rrarai.length(); i++){
                JSONObject def = rrarai.getJSONObject(i);

                listing.add(def.getString("value"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("MAIN", "getGroser: "+e.getMessage());
        }


        return listing;
    }
}
