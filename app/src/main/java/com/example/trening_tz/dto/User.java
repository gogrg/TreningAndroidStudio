package com.example.trening_tz.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {
    private String token;
    private String type;
    @SerializedName("user_id") private String userId;
    private String extra;
    @SerializedName("id_group" )private String idGroup;
    private Photo photo;

    public User() {
        token = "";
        type = "";
        userId = "";
        extra = "";
        idGroup = "";
    }

    public User(String token, String type, String userId, String extra, String idGroup, Photo photo) {
        this.token = token;
        this.type = type;
        this.userId = userId;
        this.extra = extra;
        this.idGroup = idGroup;
        this.photo = photo;
    }



    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getExtra() {
        return extra;
    }

    public void setIdGroup(String idGroup) {
        this.idGroup = idGroup;
    }

    public String getIdGroup() {
        return idGroup;
    }
}
