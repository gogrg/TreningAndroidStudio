package com.example.trening_tz.dto.schedule.structureLesson;

import com.google.gson.annotations.SerializedName;

class Building {
    private int id;
    private String name;
    private String type;
    @SerializedName("address") private String adres;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }
}
