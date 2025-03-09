package com.example.trening_tz.servise;

public enum KeysFileEntry {
    LOGIN("login"),
    PASSWORD("password"),
    USER_JSON("user json");

    private final String value;

    KeysFileEntry(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}