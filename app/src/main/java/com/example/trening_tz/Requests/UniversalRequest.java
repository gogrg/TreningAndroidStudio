package com.example.trening_tz.Requests;

import android.util.Log;

import com.example.trening_tz.Requests.requestsSettings.RequestOptions;
import com.example.trening_tz.dialogs.MessageBox;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


import com.example.trening_tz.servise.GsonClass;



public class UniversalRequest implements ResponseCallback {
    private static final OkHttpClient client = new OkHttpClient();
    @Override
    public <T> void onResponse(int codeResponse, T gettingData) {

    }

    public static <T> void connect(RequestOptions <T>requestOptions, ResponseCallback callback) {



        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        RequestBody requestBody = RequestBody.create(mediaType, requestOptions.getRequest());

        Request.Builder requestBuilder = new Request.Builder()
                .url(requestOptions.getUrl())
                .header("x-route-type", "mobile")  //оставлено, чтобы предыдущие заголовки перезаписывались этим, а дальше добавлялись актуальные
                .post(requestBody);

        requestOptions.getHeaders().forEach(requestBuilder::addHeader);

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
                            messageError.show(requestOptions.getActivity().getSupportFragmentManager(), "custom");
                        } else {
                            MessageBox messageError = new MessageBox("Ошибка подключения", "Неверный логин или пароль");
                            messageError.show(requestOptions.getActivity().getSupportFragmentManager(), "custom");
                        }
                        throw new IOException("Запрос к серверу не был успешен: " +
                                response.code() + " " + response.message());
                    } else {
                        String jsonString = responseBody.string();

                        T getData = GsonClass.fromJson(jsonString, requestOptions.gettClass());

                        Log.d("TAG", "JSON response: " + jsonString);

                        requestOptions.getActivity().runOnUiThread(() -> {
                            callback.onResponse(response.code(), getData);
                        });
                    }

                }
            }
        });
    }
}
