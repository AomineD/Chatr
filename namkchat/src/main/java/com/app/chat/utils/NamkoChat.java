package com.app.chat.utils;

import android.content.Context;

import com.app.chat.ChatterActivity;

public class NamkoChat {
    public static void init(Context context, NamkoFragment namkoFragment){
        ChatterActivity.initChat(context, namkoFragment);
    }
}
