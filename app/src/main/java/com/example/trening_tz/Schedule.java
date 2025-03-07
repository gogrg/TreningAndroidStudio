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

import com.example.trening_tz.Requests.ResponseCallback;
import com.example.trening_tz.Requests.UniversalRequest;
import com.example.trening_tz.dto.RequestForSchedule;
import com.example.trening_tz.dto.User;
import com.example.trening_tz.dialogs.MessageBox;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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


        Map<String, String> headers = new HashMap<>();
        headers.put("token", user.getToken());
        headers.put("Content-Type", "application/json; charset = utf-8");
        headers.put("ContentLength", "181595");
        headers.put("ETag", "W/\"2c55b-+EIv6+N1+X9WqUvu4o028gA92yY\"");
        headers.put("Connection", "keep-alive");
        headers.put("Keep-Alive", "timeout=5");

        UniversalRequest.connect(gson.toJson(requestForSchedule), "rasp/getRaspSearch", Schedule.this, User.class, headers, new ResponseCallback() {
            @Override
            //*************************
            //ПОМЕНЯТЬ НА НОРМАЛЬНЫЙ КЛАСС ДЛЯ РАСПИСАНИЯ, ТЕСТОВАЯ ВЕРСИЯ УНИВЕРСАЛЬНОГО ЗАПРОСА
            //*************************
            public <User> void onResponse(int code, User gettingUser) {
                     if (code == 200) {
                         MessageBox messageError = new MessageBox("Чот там", String.valueOf(code));
                         messageError.show(getSupportFragmentManager(), "custom");
                     }
            }
        });
    }
}