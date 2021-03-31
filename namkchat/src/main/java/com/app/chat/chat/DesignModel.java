package com.app.chat.chat;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.app.chat.R;

public class DesignModel {

    private Context context;
    public DesignModel(Context amc){

    }

    protected DesignModel(){

    }
    private int colorBack;

    public int getColorBackgroundChat() {
        if(colorBackgroundChat == 0){
            return ContextCompat.getColor(context, R.color.background_color_def_chat);
        }
        return colorBackgroundChat;
    }

    private int colorBackgroundChat;

    private int action_back;
    private int txtColorAnother;

    private int txt;

    public static DesignModel getDefault(Context context) {
    DesignModel designModel = new DesignModel();

designModel.setBackgroundChatColor(ContextCompat.getColor(context, R.color.background_color_def_chat));

        designModel.setTextColor(ContextCompat.getColor(context, R.color.text_color_def_chat));

        designModel.setActionBackground(ContextCompat.getColor(context, R.color.action_color_def_chat));

        designModel.setTextActionColor(ContextCompat.getColor(context, R.color.txt_action_color_def_chat));

        designModel.setBackgroundOthers(ContextCompat.getColor(context, R.color.background_another_color_def_chat));

        designModel.setAnotherTxt(ContextCompat.getColor(context, R.color.text_another_color_def_chat));

        designModel.setColorBackground(ContextCompat.getColor(context, R.color.background_cardmain_color_def_chat));

    return designModel;
    }

    public int getTxtColorAnother() {
        if(txtColorAnother == 0){
           return ContextCompat.getColor(context, R.color.text_another_color_def_chat);
        }
        return txtColorAnother;
    }

    public int getColorBackMainUser() {

        if(colorBackMainUser == 0){
            return ContextCompat.getColor(context, R.color.background_cardmain_color_def_chat);
        }

        return colorBackMainUser;
    }

    private int colorBackMainUser;

    public int getColorBack() {

        if(colorBack == 0){
            return ContextCompat.getColor(context, R.color.background_another_color_def_chat);
        }

        return colorBack;
    }

    public int getTxt() {
        if(txt == 0){
            return ContextCompat.getColor(context, R.color.text_color_def_chat);
        }
        return txt;
    }

    public int getAction_back() {
        if(action_back == 0){
            return ContextCompat.getColor(context, R.color.action_color_def_chat);
        }
        return action_back;
    }

    public int getAction_txt_back() {
        if(action_txt_back == 0){
            return ContextCompat.getColor(context, R.color.txt_action_color_def_chat);
        }
        return action_txt_back;
    }

    private int action_txt_back;

    /**
     * Color de fondo de cada mensaje
     * Background color for every message except main user
     */
    public void setColorBackground(int clr){
        colorBackMainUser = clr;
    }

    /**
     * Color de texto de cada mensaje
     * Text color for every message except main user
     */
    public void setTextColor(int clr){
        txt = clr;
    }

    /**
     * Color de fondo de cada boton de accion
     * Background color for every button
     */
    public void setActionBackground(int clr){
        action_back = clr;
    }

    /**
     * Color de fondo de cada texto en botones de accion
     * Background color for every text button
     */
    public void setTextActionColor(int clr){
        action_txt_back = clr;
    }

    /**
     * Color de fondo de cada text en mensajes de otros
     * Background color for every text button
     */
    public void setAnotherTxt(int clr){
        txtColorAnother = clr;
    }

    public void setBackgroundOthers(int clr){
        colorBack = clr;
    }


    public void setBackgroundChatColor(int clr){
        colorBackgroundChat = clr;
    }

}
