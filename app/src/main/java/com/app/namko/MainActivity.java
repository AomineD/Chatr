package com.app.namko;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.app.chat.ChangeFragment;
import com.app.chat.NamkoFragment;
import com.app.chat.adapter.ChannelAdapter;
import com.app.chat.model.ChanInfo;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private NamkoFragment fr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FragmentManager fragmentManager = getSupportFragmentManager();

        fr = new NamkoFragment();


        fr.setLang_chat("Español \uD83C\uDDEA\uD83C\uDDF8");
        fr.setNameP("kai", new NamkoFragment.onBanUser() {
            @Override
            public void whenBanUser(String username) {

            }
        });
       /* fr.setWithAds(true, true, "");
        ArrayList<String> idsnativ = new ArrayList<>();
        fr.setId_native_banner(idsnativ);*/
        // fr.setDebg(true);
        //  kai  kee206
        fr.setIdentifier("kee206", R.id.frg);

        ArrayList<String> idsnative = new ArrayList<>();

        idsnative.add("410359413142447_626423458202707");

        fr.setId_native_banner(idsnative);

        fr.setWithAds(true, true, "");



        ArrayList<ChanInfo> sds = new ArrayList<>();

ChanInfo chanInfo = new ChanInfo();
chanInfo.identifierChannel = "Español \uD83C\uDDEA\uD83C\uDDF8";
chanInfo.channelName = "Español \uD83C\uDDEA\uD83C\uDDF8";

        ChanInfo chanInfo2 = new ChanInfo();
        chanInfo2.identifierChannel = "Esp \uD83C\uDDEA\uD83C\uDDF8";
        chanInfo2.channelName = "Esp \uD83C\uDDEA\uD83C\uDDF8";


        ChanInfo chanInfo3 = new ChanInfo();
        chanInfo3.identifierChannel = "Hispano \uD83C\uDDEA\uD83C\uDDF8";
        chanInfo3.channelName = "Hispano \uD83C\uDDEA\uD83C\uDDF8";
       // ChangeFragment.setListChannels(sds);
sds.add(chanInfo);
        sds.add(chanInfo2);
        sds.add(chanInfo3);

     ChangeFragment.setListChannels(sds);
        //
        fr.isAdminSender = false;

        fragmentManager.beginTransaction().replace(R.id.frg, fr).commitAllowingStateLoss();

    }



}
