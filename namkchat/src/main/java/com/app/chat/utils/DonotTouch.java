package com.app.chat.utils;


import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.app.chat.R;

import java.util.Random;

//import cn.pedant.SweetAlert.SweetAlertDialog;

/* API
=========================================
Diego García - 2018 (ALL RIGHTS RESERVED)
=========================================
 */
public class DonotTouch {
    // ===================== NO TOCAR, URL DE API ======================================= //
    public static final String urlMain = "http://iptv.besosdeamor.info/v1/users/";
    public static final String urlAdd = "/links?page=";
    public static final String url_m = "http://iptv.besosdeamor.info/v1";
    public static final String url_query_add = "&query=";

    public static final String url_get_adv ="advertising/show";

    public static final String providerTest = "l100000168957817851818";


   // public static final String url_user_main

    public static final String key_login ="kekek";
    public static final String email_developer = "soporte@colvengames.com";
    public static final String id_developer = "ColVen+Games";
    public static final String key_item_new = "new_media";
    // ================================================================================== //

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        view.clearFocus();
    }

    public static void showKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.showSoftInput(view, 0);
        view.clearFocus();
    }

    public static int GetRandom(){
        Random rand = new Random();

        int nomber = 0 + rand.nextInt(10 - 1);

        return nomber;

    }

    /*public static void reportChapter(String name, Context mc){
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"+DonotTouch.email_developer));

        intent.putExtra(Intent.EXTRA_SUBJECT, mc.getString(R.string.report_part_1)+" "+name+" "+mc.getString(R.string.report_part_2));

        mc.startActivity(intent);
    }*/

    public static void ShowErrorLoad(Activity mContext, String error){
        String tittle = mContext.getString(R.string.errorLoad);
        if(!mContext.isDestroyed()) {
         /*   new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText(tittle)
                    .setContentText(error)
                    .show();*/
        }
    }

    public static boolean CheckInternet(Context mContext){
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                //we are connected to a network
                connected = true;
            } else {
                connected = false;
            }
        }catch (Exception e){
//DonotTouch.ShowErrorLoad(mContext, "");
            Log.e("MAIN", "CheckInternet: "+e.getMessage());
        }

        return connected;
    }

    public static void ShowSuccess(Activity mContext, String mess){
        if(!mContext.isDestroyed()) {
           /* if (!mess.isEmpty()) {
          /*      new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText(mContext.getString(R.string.success_connect))
                        .setContentText(mess)
                        .show();
            } else {
                new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText(mContext.getString(R.string.success_connect))
                        .show();
            }

*/
        }
    }
}
