package com.app.chat.model;


public class ListasM3uUser{

    public ListasM3uUser(){

    }

    public String getUrl_img() {
        return url_img;
    }

    public void setUrl_img(String url_img) {
        this.url_img = url_img;
    }

    private String url_img;
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public int getType_background() {
        return type_background;
    }

    public void setType_background(int type_background) {
        this.type_background = type_background;
    }

    private int type_background;

    public boolean isIs_ad() {
        return is_ad;
    }

    public void setIs_ad(boolean is_ad) {
        this.is_ad = is_ad;
    }

    private boolean is_ad;

    public int getId_native() {
        return id_native;
    }

    public void setId_native(int id_native) {
        this.id_native = id_native;
    }

    private int id_native;

}
