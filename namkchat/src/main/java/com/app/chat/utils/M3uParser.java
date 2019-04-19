package com.app.chat.utils;

import android.util.Log;

import com.app.chat.model.ListasM3u;
import com.app.chat.model.ListasM3uUser;
import com.app.chat.model.MediaItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * @author Diego Garcia
 */
public class M3uParser {
    public static final String header = "#EXTM3U";
    public static final String Main = "#EXTINF:0,";
    public static final String Main2 = "EXTINF:0";
    public static final String tvg = "tvg-logo=";
    public static final String comillas = "\"";
    public static final String salto = "\n";
    public static final String specific = "group-title=\"DRAGON BALL GT\"";







    // ===================================== VARIABLES ESTÁTICAS ======================================== //
    public static final String namekey = "name";
    public static final String urlkey = "url";
    public static final String activekey="active";
    public static final String idkey = "id";

    public static ArrayList<ListasM3u> GetMainLists(String r){

        ArrayList<ListasM3u> list = new ArrayList<>();

        try {
            JSONArray array = new JSONArray(r);

            for(int i=0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);

                String name = object.getString(namekey);
                String url = object.getString(urlkey);
                boolean Active = object.getInt(activekey) == 1;
                int idK = object.getInt(idkey);

                ListasM3u listasM3u = new ListasM3u();

                int nomber = DonotTouch.GetRandom();
                listasM3u.setType_background(nomber);
                listasM3u.setActive(Active);
                listasM3u.setId(idK);
                listasM3u.setUrl(url);
listasM3u.setName(name);
if(Active){
list.add(listasM3u);
}
            }



        } catch (JSONException e) {
            Log.e("MAIN", "GetMainLists: "+e.getMessage());
        }

        return list;
    }


    public static ArrayList<MediaItem> GetMediaFromList(String r){
        ArrayList<MediaItem> lista = new ArrayList<>();


            r = fixEncoding(r);


        r = r.replace(header, "");


       // Log.e("MAIN", "GetMediaFromList: MAIN = "+r);
        String[] splitters = new String[1];
if(!r.contains(Main)){
    splitters = r.split(Main2);
}else {

   splitters = r.split(Main);
}

       // Log.e("MAIN", "GetMediaFromList: " );
for(int i=0; i < splitters.length; i++){
    MediaItem item = new MediaItem();
   // Log.e("MAIN", "GetMediaFromList: spl "+splitters[i]);



    if(i > 1){
    //    Log.e("MAIN", "GetMediaFromList: Contiene tvg = "+splitters[i].contains(tvg));
    }

    if(splitters[i].contains(tvg)) {
        if(splitters[i].contains(specific)) {
            splitters[i] = splitters[i].replace(specific, ",");

        }
        String dataone = splitters[i].split(tvg)[1];


        String[] spl = dataone.split(comillas);

       // Log.e("MAIN", "GetMediaFromList: "+spl[1]);
     //   Log.e("MAIN", "GetMediaFromList: sin comillas "+spl[2]);
       // Log.e("MAIN", "GetMediaFromList: "+i);
        String[] spl2 = spl[2].split(salto);

      //  Log.e("MAIN", "GetMediaFromList: NOMBRE = "+spl2[0]);

      //  Log.e("MAIN", "GetMediaFromList: img = "+spl[1]);

        String nombre = spl2[0].replace(",", "");
        String url = "";
        if(spl2.length > 1) {
             url = spl2[1];
        }
      item.setUrl_photo(spl[1]);
      item.setName(nombre);
      item.setUrl(url);


      lista.add(item);
        // Log.e("MAIN", "GetMediaFromList: "+spl[0]);
    }
}


        return lista;
    }


    // ==================================================== GET M3u FROM M3U ========================================================== //

    public static ArrayList<ListasM3uUser> GetM3uFromListm3u(String r){
        ArrayList<ListasM3uUser> lista = new ArrayList<>();


        r = fixEncoding(r);


        r = r.replace(header, "");


        // Log.e("MAIN", "GetMediaFromList: MAIN = "+r);
        String[] splitters = new String[1];
        if(!r.contains(Main)){
            splitters = r.split(Main2);
        }else {

            splitters = r.split(Main);
        }

        // Log.e("MAIN", "GetMediaFromList: " );
        for(int i=0; i < splitters.length; i++){
            ListasM3uUser item = new ListasM3uUser();
            // Log.e("MAIN", "GetMediaFromList: spl "+splitters[i]);



            if(i > 1){
                //    Log.e("MAIN", "GetMediaFromList: Contiene tvg = "+splitters[i].contains(tvg));
            }

            if(splitters[i].contains(tvg)) {
                if(splitters[i].contains(specific)) {
                    splitters[i] = splitters[i].replace(specific, ",");

                }
                String dataone = splitters[i].split(tvg)[1];


                String[] spl = dataone.split(comillas);

                // Log.e("MAIN", "GetMediaFromList: "+spl[1]);
                //   Log.e("MAIN", "GetMediaFromList: sin comillas "+spl[2]);
                // Log.e("MAIN", "GetMediaFromList: "+i);
                String[] spl2 = spl[2].split(salto);

                //  Log.e("MAIN", "GetMediaFromList: NOMBRE = "+spl2[0]);

                //  Log.e("MAIN", "GetMediaFromList: img = "+spl[1]);

                String nombre = spl2[0].replace(",", "");
                String url = "";
                if(spl2.length > 1) {
                    url = spl2[1];
                }
              //  item.setActive(true);
               // item.setId(3);
                item.setUrl_img(spl[1]);
                item.setName(nombre);
                item.setUrl(url);

                int nomber = DonotTouch.GetRandom();
                item.setType_background(nomber);


                lista.add(item);
                // Log.e("MAIN", "GetMediaFromList: "+spl[0]);
            }
        }


        return lista;
    }




// =============================================================================================================================== //
    public static String fixEncoding(String response) {
        try {
            byte[] u = response.toString().getBytes(
                    "ISO-8859-1");
            response = new String(u, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        return response;
    }
}
