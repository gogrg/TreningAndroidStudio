package com.example.trening_tz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.trening_tz.GsonClass.RequestForSchedule;
import com.example.trening_tz.GsonClass.User;
import com.example.trening_tz.service.MessageBox;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Schedule extends AppCompatActivity {
    private static final String PREF_FILE_SETTINR_ENTRY = "FILE_SETTING_ENTRY";
    private static final String PREF_LOGIN = "login";
    private static final String PREF_PASSWORD = "password";
    private static final String PREF_USER_JSON = "user_json";

    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_schedule);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button buttonExit = findViewById(R.id.buttonExit);

        if (buttonExit != null) {
            buttonExit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Log.d("TAG", "Переход между активностями произошёл. (?)");
                        //обнуление полей и user в MainActivity
                        SharedPreferences settingEntry = getSharedPreferences(PREF_FILE_SETTINR_ENTRY, MODE_PRIVATE);
                        SharedPreferences.Editor sharedSetting = settingEntry.edit();
                        sharedSetting.putString(PREF_USER_JSON, "");
                        sharedSetting.putString(PREF_LOGIN, "");
                        sharedSetting.putString(PREF_PASSWORD, "");
                        sharedSetting.apply();

                        Intent intent = new Intent(Schedule.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } catch (Exception e) {
                        Log.d("TAG", "Ошибка при возврате в MainActivity: " + e.getMessage());
                    }
                }
            });
        } else {
            Log.d("TAG", "Кнопка не найдена");
        }

        User user = (User) getIntent().getSerializableExtra("userData");
        ConstraintLayout blockSchedule = findViewById(R.id.containerInScrollView);


        TextView testView = new TextView(Schedule.this);
        testView.setText(user.getUserId());
        testView.setTextSize(12);
        blockSchedule.addView(testView);

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        RequestForSchedule requestForSchedule = new RequestForSchedule(6, 3, 2025, user.getUserId(), user.getIdGroup(), "");

        String stringRequest = gson.toJson(requestForSchedule);



        RequestBody requestBody = RequestBody.create(mediaType, stringRequest);

        Request request = new Request.Builder()
                .url("https://app2.spbgasu.ru/rasp/getPersonalForUserSearch")
                .addHeader("token", user.getToken())
                .addHeader("X-Powered-By", "Express")
                .addHeader("Content-Type", "application/json; charset = utf-8")
                .addHeader("ContentLength", "181595")
                .addHeader("ETag", "W/\"2c55b-+EIv6+N1+X9WqUvu4o028gA92yY\"")
                .addHeader("Connection", "keep-alive")
                .addHeader("Keep-Alive", "timeout=5")
                //.addHeader("group_id", user.getIdGroup())
                .post(requestBody)
                .build();

        Log.d("TAG", "Запрос сформирован");

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("TAG", "Не удалось отрправить запрос");
                MessageBox messageError = new MessageBox("Ошибка подключения", "Не удалось отправить запрос");
                messageError.show(getSupportFragmentManager(), "custom");
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("TAG", "onResponse is called");
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) {
                        Log.d("TAG", "Response abount schedule isn't successful");

                        if (response.code() != 401) {
                            MessageBox messageError = new MessageBox("Ошибка подключения", "Ошибка подключения: " + response.message() + ". Код ошибки: " + String.valueOf(response.code()));
                            messageError.show(getSupportFragmentManager(), "custom");
                        } else {
                            MessageBox messageError = new MessageBox("Ошибка подключения", "Неверный логин или пароль");
                            messageError.show(getSupportFragmentManager(), "custom");
                        }
                        throw new IOException("Запрос к серверу не был успешен: " +
                                response.code() + " " + response.message());
                    }

                    Log.d("TAG", "Запрос по поводу расписания успешен");

                    MessageBox messageError = new MessageBox("Подключение успешно?", "Получилось? " + response.message() + " Код " + String.valueOf(response.code()));
                    messageError.show(getSupportFragmentManager(), "custom");


                    String jsonString = responseBody.string();

                    Log.d("TAG", jsonString);

                }
            }
        });
    }
}