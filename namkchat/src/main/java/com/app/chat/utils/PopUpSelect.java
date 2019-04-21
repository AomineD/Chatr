package com.app.chat.utils;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.chat.R;
import com.app.chat.adapter.playerAdapter;
import com.yarolegovich.discretescrollview.DiscreteScrollView;

/**
 * @author Diego Garcia
 */
public class PopUpSelect extends AlertDialog {


    private Context mContext;
    private Activity actividad;
    private Uri urr;
private String namee;
private View Linss;
private View backwh;

private boolean Already;
private RelativeLayout rr;


    public static boolean edit;
    public static long item_id;
    private boolean ok_bol;
    private DiscreteScrollView scrollView;

    protected PopUpSelect(@NonNull Context context) {
        super(context);

    }

    public PopUpSelect(Context context, String url, String nam){
        super(context);
        this.mContext = context;
        this.urr = Uri.parse(url);

    this.namee = nam;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_layout);

        //animationView.setScale(1f);
        rr = findViewById(R.id.kk);
        backwh = findViewById(R.id.white_backg);
        Linss = findViewById(R.id.lin_ss);
        scrollView = findViewById(R.id.reprod);

        playerAdapter adapter = new playerAdapter(mContext);

        scrollView.setAdapter(adapter);

getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
getWindow().setBackgroundDrawable(ActivityCompat.getDrawable(getContext(), R.color.tran));

        TextView button_cancel = findViewById(R.id.cancel_button);

        TextView button_save = findViewById(R.id.go_player);


        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
onBackPressed();
            }
        });
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Play();
            }
        });


    }

    private void Play() {
        if(!Already) {



            backwh.setVisibility(View.VISIBLE);
            Linss.setVisibility(View.GONE);
            rr.setVisibility(View.GONE);

                        switch (scrollView.getCurrentItem()) {
                            case 0:
                                OpenWithMX();
                                break;
                            case 1:
                                OpenWithVlc();
                                break;

                        }
                        dismiss();


            Already = true;


        }
    }

    private void OpenWithMX() {
        String packageName = "com.flix.mediaplayer";//Configs.nombre_paquete_player;
try {
    Intent mx = new Intent(Intent.ACTION_VIEW);
mx.setPackage(packageName);
mx.setDataAndType(urr, "video/*");
    mx.putExtra("title", namee);
   // mx.putExtra("from_start", false);

    mContext.startActivity(mx);

}catch (Exception e){
    Log.e("MAIN", "OpenWithMX: "+e.getMessage());

    try {
        mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+packageName)));
    } catch (android.content.ActivityNotFoundException ee) {
        mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+packageName)));
    }

}
    }

    private void OpenWithVlc() {
        String packageName = "org.videolan.vlc";
        try {
            Intent vlc = new Intent(Intent.ACTION_VIEW);
vlc.setPackage(packageName);
            vlc.setDataAndType(urr, "video/*");
            vlc.putExtra("title", namee);
            vlc.putExtra("from_start", false);
            vlc.putExtra("position", 90000L);
            // mx.putExtra("from_start", false);

            mContext.startActivity(vlc);

        }catch (Exception e){
            Log.e("MAIN", "OpenVLC: "+e.getMessage());

            try {
                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+packageName)));
            } catch (android.content.ActivityNotFoundException ee) {
                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+packageName)));
            }

        }
    }


// ==================================================================== Editar lista user =============================================================== //



            }


