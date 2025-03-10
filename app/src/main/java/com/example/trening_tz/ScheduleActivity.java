package com.example.trening_tz;

import android.content.Intent;
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
import com.example.trening_tz.dialogs.MessageBox;
import com.example.trening_tz.dto.RequestForSchedule;
import com.example.trening_tz.dto.User;
import com.example.trening_tz.servise.GsonClass;
import com.example.trening_tz.servise.KeysFileEntry;
import com.example.trening_tz.servise.NamesFilesSetting;
import com.example.trening_tz.servise.StaticSharedPreferences;
import com.example.trening_tz.dto.schedule.Schedule;

import java.util.HashMap;
import java.util.Map;


public class ScheduleActivity extends AppCompatActivity {


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
                        StaticSharedPreferences.remove(NamesFilesSetting.FILE_ENTRY.getValue(), KeysFileEntry.USER_JSON.getValue(), ScheduleActivity.this);
                        StaticSharedPreferences.remove(NamesFilesSetting.FILE_ENTRY.getValue(), KeysFileEntry.LOGIN.getValue(), ScheduleActivity.this);
                        StaticSharedPreferences.remove(NamesFilesSetting.FILE_ENTRY.getValue(), KeysFileEntry.PASSWORD.getValue(), ScheduleActivity.this);

                        Intent intent = new Intent(ScheduleActivity.this, MainActivity.class);
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


        TextView testView = new TextView(ScheduleActivity.this);
        testView.setText(user.getUserId());
        testView.setTextSize(12);
        blockSchedule.addView(testView);


        RequestForSchedule requestForSchedule = new RequestForSchedule(6, 3, 2025, user.getUserId(), user.getIdGroup(), "");


        Map<String, String> headers = new HashMap<>();
        headers.put("token", user.getToken());
        headers.put("Content-Type", "application/json; charset = utf-8");
        headers.put("ContentLength", "181595");
        headers.put("ETag", "W/\"2c55b-+EIv6+N1+X9WqUvu4o028gA92yY\"");
        headers.put("Connection", "keep-alive");
        headers.put("Keep-Alive", "timeout=5");

        //было, через передачу айди группы и т.д. и даты
        UniversalRequest.connect(GsonClass.toJson(requestForSchedule), "rasp/getRaspSearch", ScheduleActivity.this, Schedule.class, headers, new ResponseCallback() {

        //UniversalRequest.connect("", "rasp/getPersonalForUserSearch", ScheduleActivity.this, User.class, headers, new ResponseCallback() {
            @Override
            //*************************
            //ПОМЕНЯТЬ НА НОРМАЛЬНЫЙ КЛАСС ДЛЯ РАСПИСАНИЯ, ТЕСТОВАЯ ВЕРСИЯ УНИВЕРСАЛЬНОГО ЗАПРОСА
            //*************************
            public <Schedule> void onResponse(int code, Schedule gettingSchedule) {
                     if (code == 200) {
                         com.example.trening_tz.dto.schedule.Schedule schedule = (com.example.trening_tz.dto.schedule.Schedule) gettingSchedule;
                         MessageBox messageError = new MessageBox("Чот там", schedule.getDay(1).getSomeVariantDay(5).get(0).getSubject().toString());
                         messageError.show(getSupportFragmentManager(), "custom");
                     }
            }
        });
    }
}