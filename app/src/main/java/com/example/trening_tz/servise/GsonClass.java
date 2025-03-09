package com.example.trening_tz.servise;

import com.google.gson.Gson;

public  class GsonClass {
    private static Gson gson = new Gson();

    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    public static <T> T fromJson(String json, Class<T> tClass){
        return gson.fromJson(json, tClass);
    }
}
