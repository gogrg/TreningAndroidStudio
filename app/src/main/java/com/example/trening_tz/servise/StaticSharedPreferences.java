package com.example.trening_tz.servise;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import java.util.function.Supplier;


public class StaticSharedPreferences {
    private static SharedPreferences preferences;

    public static void putString(String fileSetting, String key, String value, AppCompatActivity activity) {
        preferences = activity.getSharedPreferences(fileSetting, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(String fileSetting, String key, String defaultValue, AppCompatActivity activity) {
        preferences = activity.getSharedPreferences(fileSetting, MODE_PRIVATE);
        return preferences.getString(key, defaultValue);
    }

    public static <T> T getObject(String fileSetting, String key, String defaultValue, Class<T> tClass, Supplier<T> supplier, AppCompatActivity activity) {
        preferences = activity.getSharedPreferences(fileSetting, MODE_PRIVATE);
        String jsonString = preferences.getString(key, defaultValue);
        if (jsonString != defaultValue) {
            return GsonClass.fromJson(jsonString, tClass);
        }
        else {
            return supplier.get();
        }
    }

    public static void remove(String fileSetting, String key, AppCompatActivity activity) {
        preferences = activity.getSharedPreferences(fileSetting, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.apply();
    }

    public static void clear(String fileSetting, AppCompatActivity activity) {
        preferences = activity.getSharedPreferences(fileSetting, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}
