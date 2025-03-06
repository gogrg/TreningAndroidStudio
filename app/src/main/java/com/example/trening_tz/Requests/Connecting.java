package com.example.trening_tz.Requests;

import android.util.Log;

import com.example.trening_tz.GsonClass.User;
import com.example.trening_tz.service.MessageBox;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;


public class Connecting {
    public interface ResponseCallback {
        void onResponse(int codeResponce, User user);
    }
    public static void connect(String login, String password, AppCompatActivity activity, ResponseCallback callback) {

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        String stringRecurest = "{\"login\":\"" + login + "\", \"pass\":\"" + password + "\"}";

        RequestBody requestBody = RequestBody.create(mediaType, stringRecurest);

        Request request = new Request.Builder()
                .url("https://app2.spbgasu.ru/auth/login")
                .addHeader("x-route-type", "mobile")
                .post(requestBody)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("LOG", "onResponse is called");
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) {
                        Log.d("TAG", "Response isn't successful");

                        if (response.code() != 401) {
                            MessageBox messageError = new MessageBox("Ошибка подключения", "Ошибка подключения: " + response.message() + ". Код ошибки: " + String.valueOf(response.code()));
                            messageError.show(activity.getSupportFragmentManager(), "custom");
                        } else {
                            MessageBox messageError = new MessageBox("Ошибка подключения", "Неверный логин или пароль");
                            messageError.show(activity.getSupportFragmentManager(), "custom");
                        }
                        throw new IOException("Запрос к серверу не был успешен: " +
                                response.code() + " " + response.message());
                    } else {
                        Gson gson = new Gson();
                        User user = gson.fromJson(responseBody.string(), User.class);

                        activity.runOnUiThread(() -> {
                            callback.onResponse(response.code(), user);
                        });
                    }

                }
            }
        });
    }
}
