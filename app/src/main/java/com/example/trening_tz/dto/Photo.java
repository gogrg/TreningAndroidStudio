package com.example.trening_tz.dto;


import android.graphics.drawable.Drawable;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.trening_tz.R;

import java.io.Serializable;


public class Photo implements Serializable {
    private String origin;
    private String thumbnail;
    private String small;

    Photo(String origin, String thumbnail, String small) {
        this.origin = origin;
        this.thumbnail = thumbnail;
        this.small = small;
    }

    Photo() {
        this.origin = "";
        this.thumbnail = "";
        this.small = "";
    }

    public void setSmallImage(ImageView imageView, int drawable, AppCompatActivity activity) {
        if (small != null) {
            Glide.with(activity)
                    .load("https://app2.spbgasu.ru" + small)
                    .error(drawable)
                    .into(imageView);
        } else {
            return;
        }
    }

    public void setSmallImage(ImageButton imageButton, int drawable, AppCompatActivity activity) {
        if (small != null) {
            Glide.with(activity)
                    .load("https://app2.spbgasu.ru" + small)
                    .error(drawable)
                    .into(imageButton);
        } else {
            return;
        }
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }
}
