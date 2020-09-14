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

import com.app.chat.R;

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

public class Utils {
    private static List<TimoItemViewParameter> getCenterChatNoise(int itemWidth){
        List<TimoItemViewParameter> listTop = new ArrayList<>();
        TimoItemViewParameter yes = getTimoItemViewParameter(itemWidth, R.drawable.confirm_ic,
                R.drawable.confirm_ic, R.string.confirm, R.color.colorPrimaryDark,
                R.color.colorPrimary);

        TimoItemViewParameter nu = getTimoItemViewParameter(itemWidth, R.drawable.cancel_ic,
                R.drawable.cancel_ic, R.string.dontwant, android.R.color.darker_gray,
                R.color.black);


        listTop.add(yes);
        listTop.add(nu);
        return listTop;
    }


    private static TimoItemViewParameter getTimoItemViewParameter(int itemWidth,
                                                                 int normalImageRes,
                                                                 int highlightImageRes,
                                                                 int normalTextRes,
                                                                 int normalTextColorRes,
                                                                 int highlightTextColorRes){
        return new TimoItemViewParameter.Builder()
                .setWidth(itemWidth)
                .setImagePadding(new Rect(10, 10, 10, 10))
                .setTextPadding(new Rect(5, 0, 5, 0))
                .setNormalImageRes(normalImageRes)
                .setHighlightedImageRes(highlightImageRes)
                .setNormalTextRes(normalTextRes)
                .setNormalTextColorRes(normalTextColorRes)
                .setHighlightedTextColorRes(highlightTextColorRes)
                .build();

    }


    public interface selectionListener{
        void onConfirm();
        void onCancel();
    }
    public static void createPopUpContact(Activity c, final selectionListener listener)
    {
        int itemViewWidth = (c.getWindow().getWindowManager().getDefaultDisplay().getWidth() - 30) / 2;



     final TimoMenu   mTimoMenu =  new TimoMenu.Builder(c)
                .setGravity(Gravity.CENTER)
                .setTimoMenuListener(new TimoMenuListener() {
                    @Override
                    public void onShow() {
                     //   Toast.makeText(getApplicationContext(), "Show", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDismiss() {
                       // Toast.makeText(getApplicationContext(), "Dismiss", Toast.LENGTH_SHORT).show();
                    }
                })
                .setTimoItemClickListener(new OnTimoItemClickListener() {
                    @Override
                    public void onItemClick(int row, int index, TimoItemView view) {
                     //   Toast.makeText(getApplicationContext(), String.format("%s click~", getString(MenuHelper.ROW_TEXT[row][index])), Toast.LENGTH_SHORT).show();
                    if(index == 0){
listener.onConfirm();
                    timoMenu.dismiss();
                    }else {
                        listener.onCancel();
                        timoMenu.dismiss();
                    }
                    }
                })

                .setMenuMargin(new Rect(20, 20, 20, 20))
                .setMenuPadding(new Rect(0, 10, 0, 10))
                .addRow(ScaleItemAnimation.create(), getCenterChatNoise(itemViewWidth))
             .setHeaderLayoutRes(R.layout.header_popup)
                //.addRow(ScaleItemAnimation.create(), getCenterChatNoise(itemViewWidth))
                .build();
     timoMenu = mTimoMenu;
    mTimoMenu.show();
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
}
