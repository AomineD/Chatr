package com.app.namko;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import com.app.chat.utils.NamkoFragment;
import com.app.chat.model.ChanInfo;
import com.app.chat.utils.NamkoChat;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {


    ArrayList<String> validUrls = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/*
        validUrls.add("https://multiupload.live/embed/Hk6rht47exhIJ4c8/*");
        validUrls.add("https://multiupload.live/embed/*");
        WebView webView = findViewById(R.id.webview);
webView.setWebViewClient(new WebViewClient(){
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        if (isValidUrl(request.getUrl().toString())) {
            return false;
        }
        Log.e("MAIN", "shouldOverrideUrlLoading: no es valida" );
        return true;
    }
});

webView.getSettings().setJavaScriptEnabled(true);

       // webView.setProvider(AdblockHelper.get().getProvider());
        webView.loadUrl("https://multiupload.live/embed/Hk6rht47exhIJ4c8/");*/

      //  startActivity(new Intent(this, ChatterActivity.class));
        ArrayList<ChanInfo> chanInfos = new ArrayList<>();
        ChanInfo a = new ChanInfo();
        a.channelName = "GENERAL";
        a.identifierChannel = "GENERAL";

        ChanInfo a2 = new ChanInfo();
        a2.channelName = "GENERAL 2";
        a2.identifierChannel = "GENERAL 2";
        chanInfos.add(a);
        chanInfos.add(a2);

NamkoFragment namkoFragment = new NamkoFragment.Builder(chanInfos, a.channelName)
        .setUserAutomatic()
        .withBannerAds("ca-app-pub-3940256099942544/6300978111")
        .setTextInRegister("Hola, registrate en nuestra app ahora")
        .build();

        NamkoChat.init(this, namkoFragment);
    }

    private boolean isValidUrl(String url) {
        for (String validUrl : validUrls) {
            Pattern pattern = Pattern.compile(validUrl, Pattern.MULTILINE);
            Matcher matcher = pattern.matcher(url);
            if (matcher.find()) {
                return true;
            }
        }
        return false;
    }



}
