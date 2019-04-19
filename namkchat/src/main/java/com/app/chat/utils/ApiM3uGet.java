package com.app.chat.utils;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.chat.model.MediaItem;

import java.util.ArrayList;

/**
 * @author Diego Garcia
 */
public class ApiM3uGet {
    private Context mContext;
    private M3uResponse responseListener;
    private String url;

    public ApiM3uGet(Context getter,String urlget, M3uResponse reso){
        this.mContext = getter;
        this.responseListener = reso;
        this.url = urlget;
    }

    public void Execute(){
        RequestQueue queue = Volley.newRequestQueue(mContext);

        StringRequest request = new StringRequest(StringRequest.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              //  Log.e("MAIN", "onResponse: AHAHAH -> "+response);
responseListener.OnLoad(M3uParser.GetMediaFromList(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                responseListener.FailedLoad(error.getMessage());
            }
        });

        queue.add(request);



    }


    public interface M3uResponse{
        void OnLoad(ArrayList<MediaItem> items);

        void FailedLoad(String error);
    }
}
