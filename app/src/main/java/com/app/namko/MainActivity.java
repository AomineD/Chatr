package com.app.namko;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.app.chat.NamkoFragment;
import com.app.chat.TvFragment;
import com.app.chat.ViewListActivity;
import com.app.chat.utils.PopUpSelect;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    private NamkoFragment fr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FragmentManager fragmentManager = getSupportFragmentManager();

       fr = new NamkoFragment();


       fr.setLang_chat("Colombia");
       fr.setNameP("Diego");
       fr.setDebg(true);
      fr.setIdentifier("kee2012406");
       // fr.setUrrPhoto("");

        fragmentManager.beginTransaction().replace(R.id.frg, fr).commitAllowingStateLoss();

       /* fr.setAd(new PopUpSelect.ClickAd() {
            @Override
            public void Ad() {
                Toast.makeText(MainActivity.this, "Sale anuncio", Toast.LENGTH_SHORT).show();
fr.playMedia();
            }
        });*/



        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                TvFragment.beginStart = true;
           //    fr.openM3uView("http://54.39.147.50:777/ale.m3u", "");
            }
        }, 0);

    }



}
