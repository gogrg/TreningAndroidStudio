package com.example.trening_tz.dto;

public class DataForRequestEntry {
    String login;
    String pass;

    public DataForRequestEntry(String login, String password) {
        this.login = login;
        this.pass = password;
    }

    public String getPassword() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
