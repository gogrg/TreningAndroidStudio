package com.example.trening_tz.Requests;

import android.util.Log;

import com.example.trening_tz.dto.User;
import com.example.trening_tz.dialogs.MessageBox;

import java.io.IOException;
import java.util.Map;

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


public class UniversalRequest implements ResponseCallback {
    @Override
    public <T> void onResponse(int codeResponce, T getData) {

    }

    public static <T> void connect(String stringRequest, String url, AppCompatActivity activity, Class<T> tClass, Map<String, String> headers, ResponseCallback callback) {

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        RequestBody requestBody = RequestBody.create(mediaType, stringRequest);

        Request.Builder requestBuilder = new Request.Builder()
                .url("https://app2.spbgasu.ru/" + url)
                .header("x-route-type", "mobile")
                .post(requestBody);

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            requestBuilder.addHeader(entry.getKey(), entry.getValue());
        }

        Request request = requestBuilder.build();


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
                        T getData = gson.fromJson(responseBody.string(), tClass);

                        activity.runOnUiThread(() -> {
                            callback.onResponse(response.code(), getData);
                        });
                    }

                }
            }
        });
    }
}
