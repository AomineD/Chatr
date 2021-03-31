package com.app.chat.utils;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.chat.R;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class UserActivity extends AppCompatActivity {

    public static NamkoFragment namkoFragment;
    public static String str = "";

    @Override
    protected void onDestroy() {
        super.onDestroy();
        str = "";
    }

    private boolean isReady = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        final AutoCompleteTextView auj = findViewById(R.id.usern);

        TextView ca = findViewById(R.id.below_text);

        if(str.isEmpty()){
            str = getString(R.string.chat_info);
        }

ca.setText(str);

        findViewById(R.id.btn_go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(auj.getText().toString().isEmpty()){
                    Toast.makeText(UserActivity.this, "Cual es tu nombre de usuario?", Toast.LENGTH_SHORT).show();
                }else{
                    String usn = auj.getText().toString();
                    String idf = Utils.createUserIdentifier(usn);
                    Utils.saveData(idf, usn);
                    namkoFragment.identifier = idf;
                    namkoFragment.setNameP(usn);
                    isReady = true;
               onBackPressed();
               namkoFragment.shownow();
                namkoFragment = null;
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(!isReady){
            namkoFragment.clickBackListener.onClicked();
        }
        Animatoo.animateSlideUp(this);
    }
}