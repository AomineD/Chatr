package com.app.chat.model;

import android.net.Uri;

public class MediaItem {
    public MediaItem(){

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri getUrl() {
        return Uri.parse(url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Uri getUrl_photo() {
        return Uri.parse(url_photo);
    }

    public void setUrl_photo(String url_photo) {
        this.url_photo = url_photo;
    }

    private String name;
    private String url;
    private String url_photo;

    public boolean isAd() {
        return IsAd;
    }

    public void setAd(boolean ad) {
        IsAd = ad;
    }

    private boolean IsAd;


    public boolean isFavr() {
        return favr;
    }

    public void setFavr(boolean favr) {
        this.favr = favr;
    }

    private boolean favr;
}
