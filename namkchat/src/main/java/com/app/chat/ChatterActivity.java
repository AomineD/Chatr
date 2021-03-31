package com.app.chat;

import androidx.annotation.RestrictTo;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;

import com.app.chat.utils.NamkoFragment;
import com.wineberryhalley.bclassapp.BaseActivity;

import java.util.ArrayList;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public class ChatterActivity extends BaseActivity {

    public static void initChat(Context context, NamkoFragment a){
        namkoFragment = a;
        context.startActivity(new Intent(context, ChatterActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        namkoFragment = null;
    }

    private static NamkoFragment namkoFragment;

    @Override
    public void Main() {
        if(namkoFragment == null){
            finish();
        }

        FragmentTransaction f = getSupportFragmentManager().beginTransaction();
        namkoFragment.ContainerId = R.id.frame;
        namkoFragment.setClickBackListener(new NamkoFragment.onClickBackListener() {
            @Override
            public void onClicked() {
                onBackPressed();
            }
        });
        f.replace(R.id.frame, namkoFragment).commitAllowingStateLoss();
    }

    @Override
    public void statusChanged(int pixelesSizeBar) {

    }

    @Override
    public int resLayout() {
        return R.layout.activity_chatter;
    }

    @Override
    public ArrayList<String> keysNotification() {
        return null;
    }

    @Override
    public void onReceiveValues(ArrayList<String> values) {

    }



}