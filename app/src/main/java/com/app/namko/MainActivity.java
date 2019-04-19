package com.app.namko;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.app.chat.NamkoFragment;
import com.app.chat.ViewListActivity;

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
        fr.setBackgdrawable(R.drawable.backgr);

        fr.setNameP("Diego Garcia");
        fr.setLang_chat("Latino");
        fr.setIdentifier("shaiyafusison30@gmail.com");
        fr.setUrrPhoto("http://chittagongit.com//images/avatar-icon/avatar-icon-4.jpg");
fr.setDebg(true);
        fragmentManager.beginTransaction().replace(R.id.frg, fr).commitAllowingStateLoss();


        fr.setListenerMedia(new NamkoFragment.OnClickmedia() {
            @Override
            public void onClickMedia(String data) {
                Toast.makeText(MainActivity.this, "CLICK "+data, Toast.LENGTH_SHORT).show();
            }
        });


        ViewListActivity.openM3uView(this, "http://54.39.147.50:777/ale.m3u", "TV");

        fr.setColors(getResources().getColor(R.color.colorAccent), getResources().getColor(R.color.colorPrimaryDark), getResources().getColor(R.color.colorAccent), getResources().getColor(R.color.white), getResources().getColor(R.color.black), getResources().getColor(R.color.white));
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
runOnUiThread(new Runnable() {
    @Override
    public void run() {
       // Log.e("MAIN", "run: "+getResources().getColor(R.color.white));

     ///   sendPersonalized();
    }
});


            }
        }, 4000);

    }

    public void sendPersonalized(){

fr.SendPersonalizedMessage("¿Quieres compartir esta pelicula con los demas en el chat?", "Game Of Thrones 5", "La temporada 5 de una de las mejores series de la historia",
        "https://cinepremiere.com.mx/assets/images/noticias/2015/03-marzo/poster-season5-gameOfThrones.jpg", "VER AHORA", "LA ESTAS VIENDO"
        );

    }


}
