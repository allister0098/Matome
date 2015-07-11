package com.example.syo.listfragment.Model;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by syo on 2015/07/05.
 */
public class Item implements Serializable {
    // 記事のタイトル
    private CharSequence mTitle;
    // 記事の本文
    private CharSequence mDescription;
    // 記事のURL
    private CharSequence mUrl;
    // 画像のURL
//    private CharSequence imgUrl;
    private Bitmap image;

    public CharSequence getTitle() {
        return mTitle;
    }

    public void setTitle(CharSequence title) {
        mTitle = title;
    }

    public CharSequence getDescription() {
        return mDescription;
    }

    public void setDescription(CharSequence description) {
        mDescription = description;
    }

    public CharSequence getUrl() {return mUrl;}

    public void setUrl(CharSequence Url) {mUrl = Url;}

    public void setImage(Bitmap resource) {
        image = resource;
    }

    public Bitmap getImage() {
        return image;
    }

//    public void setImgUrl(CharSequence imgUrl) {
//        this.imgUrl = imgUrl;
//    }
//
//    public CharSequence getImgUrl() {
//        return imgUrl;
//    }
}
