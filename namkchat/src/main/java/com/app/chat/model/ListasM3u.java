package com.app.chat.model;



public class ListasM3u {

    public ListasM3u(){

    }

    public boolean nuevo;

    public int getIdList() {
        return idmovi;
    }

    public void setId(int id) {
        this.idmovi = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    private int idmovi;
    private String url;
    private boolean active;

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
