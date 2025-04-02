package com.example.trening_tz.servise;

public enum KeysFileSchedule {
    SCHEDULE("schedule");

    private final String value;

    KeysFileSchedule(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
