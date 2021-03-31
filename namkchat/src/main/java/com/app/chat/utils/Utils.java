package com.app.chat.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RestrictTo;
import androidx.appcompat.app.AppCompatActivity;

import com.app.chat.R;
import com.app.chat.chat.ClickBottom;
import com.app.chat.model.MessageReceive;
import com.wineberryhalley.bclassapp.TinyDB;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import me.samlss.timomenu.TimoItemViewParameter;
import me.samlss.timomenu.TimoMenu;
import me.samlss.timomenu.animation.ScaleItemAnimation;
import me.samlss.timomenu.interfaces.OnTimoItemClickListener;
import me.samlss.timomenu.interfaces.TimoMenuListener;
import me.samlss.timomenu.view.TimoItemView;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

@RestrictTo(LIBRARY)
public class Utils {

    static TinyDB tinyDB;



    public static class SelectionListener{
       public void onConfirm(){}
        public void onCancel(){}
        public void OnBanUser(){}
    }
    public static void createPopUpContact(Activity c, MessageReceive ms, final SelectionListener listener)
    {
        AppCompatActivity a = (AppCompatActivity) c;
        ClickBottom clickBottom = new ClickBottom(listener, ms);
        clickBottom.show(a.getSupportFragmentManager(), "clkas");
    }

    private static TimoMenu timoMenu;


    private static final String key_show = "NASDASD";
    public static void showCase(Activity m, View emojibtn, View changeBtn){
       SharedPreferences preferences = m.getSharedPreferences("case", Context.MODE_PRIVATE);

        if(preferences.getInt(key_show, 0) == 0){

         //   preferences.edit().



            ShowcaseConfig config = new ShowcaseConfig();
            config.setDelay(500); // half second between each showcase view

            MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(m, "idasdes");

            sequence.setConfig(config);

            sequence.addSequenceItem(changeBtn,
          m.getString(R.string.change_info), m.getString(R.string.got_it));

            sequence.addSequenceItem(emojibtn,
                    m.getString(R.string.emoji_info), m.getString(R.string.got_it));

            sequence.start();

                    }

    }

    public static String createUserIdentifier(String nm){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            int randomNum = ThreadLocalRandom.current().nextInt(0, 8000 + 1);
            nm = nm+""+randomNum;
        }else{
            Random rand = new Random();

            // nextInt is normally exclusive of the top value,
            // so add 1 to make it inclusive
            int randomNum = rand.nextInt((8000 - 0) + 1) + 0;
            nm = nm+""+randomNum;
        }

    return nm;
    }

    public static void setPreferences(SharedPreferences preferences) {
        Utils.preferences = preferences;
    }

    private static SharedPreferences preferences;
private static String key_usern = "KALALSLAKAKA";
private static String key_identifier = "LLWKQKASAS";
private static String key_saved = "KASKASKSAK";
private static String key_banneds = "MNWQFFAFASF";

public static void banThis(String identifier){
if(tinyDB != null){
    ArrayList<String> ident = tinyDB.getListString(key_banneds);
boolean has = false;
    for (String id:
         ident) {
        if(id.equalsIgnoreCase(identifier)){
            has = true;
        }
    }

    if(!has){
        ident.add(identifier);
    }

    tinyDB.putListString(key_banneds, ident);

}
}

public static boolean isBanned(String ident){
  if(tinyDB != null){
      ArrayList<String> identa = tinyDB.getListString(key_banneds);

      for (String id:
              identa) {
          if(id.equalsIgnoreCase(ident)){
              return true;
          }
      }

  }
    return false;
}

    public static void saveData(String identifier, String user_name){

preferences.edit().putString(key_usern, user_name).apply();
        preferences.edit().putString(key_identifier, identifier).apply();
preferences.edit().putBoolean(key_saved, true).apply();
    }

    public static String[] getDataUser(){
        String[] s = new String[2];

        s[0] = preferences.getString(key_usern, "");
        s[1] = preferences.getString(key_identifier, "");

        return s;
    }
    
    public static boolean isUserSaved(){
        return preferences.getBoolean(key_saved, false);
    }

    public static boolean isAdmobAd(String ad){
        return ad.length() == 38;
    }

    public static boolean isAudienceAd(String ad){
        return ad.length() == 31;
    }
}
