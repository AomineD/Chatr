package com.app.namko;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.app.chat.NamkoFragment;
import com.app.chat.TvFragment;
import com.app.chat.ViewListActivity;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    private TvFragment fr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FragmentManager fragmentManager = getSupportFragmentManager();

       fr = new TvFragment();


        fragmentManager.beginTransaction().replace(R.id.frg, fr).commitAllowingStateLoss();


        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
          //      fr.openM3uView("http://54.39.147.50:777/ale.m3u", "");
            }
        }, 3000);

    }



}
