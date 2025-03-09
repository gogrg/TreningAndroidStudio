package com.example.trening_tz.servise;

public enum NamesFilesSetting {
    FILE_ENTRY("FILE_SETTING_ENTRY"),
    FILE_SCHEDULE("FILE_SETTING_SCHEDULE");

    private final String value;

    NamesFilesSetting(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}