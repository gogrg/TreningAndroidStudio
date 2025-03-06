package com.example.trening_tz.GsonClass;

import java.io.Serializable;

public class User implements Serializable {
    private String token;
    private String type;
    private String user_id;
    private String extra;
    private String id_group;

    public User() {
        token = "";
        type = "";
        user_id = "";
        extra = "";
        id_group = "";
    }

    public User(String token, String type, String user_id, String extra, String id_group) {
        this.token = token;
        this.type = type;
        this.user_id = user_id;
        this.extra = extra;
        this.id_group = id_group;
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

    public void setUserId(String user_id) {
        this.user_id = user_id;
    }

    public String getUserId() {
        return user_id;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getExtra() {
        return extra;
    }

    public void setIdGroup(String id_group) {
        this.id_group = id_group;
    }

    public String getIdGroup() {
        return id_group;
    }
}
