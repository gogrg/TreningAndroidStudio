package com.example.trening_tz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.trening_tz.Requests.ResponseCallback;
import com.example.trening_tz.Requests.UniversalRequest;
import com.example.trening_tz.Requests.requestsSettings.RequestCurrentWeekOptions;
import com.example.trening_tz.Requests.requestsSettings.RequestScheduleOptions;
import com.example.trening_tz.buildUI.BuildSchedule;
import com.example.trening_tz.dialogs.MessageBox;
import com.example.trening_tz.dto.DataForRequestSchedule;
import com.example.trening_tz.dto.User;
import com.example.trening_tz.servise.GsonClass;
import com.example.trening_tz.servise.KeysFileEntry;
import com.example.trening_tz.servise.NamesFilesSetting;
import com.example.trening_tz.servise.StaticSharedPreferences;
import com.example.trening_tz.dto.schedule.Schedule;

import java.time.LocalDate;


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
        ConstraintLayout blockSchedule = findViewById(R.id.containerInScrollView);
        ImageButton imageButtonUser = findViewById(R.id.imageButtonUser);
        imageButtonUser.setBackgroundResource(R.drawable.icon_person);

        imageButtonUser.setBackgroundResource(R.drawable.background_person);

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


        User user = (User) getIntent().getSerializableExtra("userData");
        if (user != null) {
            if (user.getPhoto() != null){
                    Log.d("TAG", "Попытка установить изображение для кнопки");
                    user.getPhoto().setSmallImage(imageButtonUser, R.drawable.icon_person ,ScheduleActivity.this);
            } else {
                Log.d("TAG", "Фото пользователя отсутствует");
            }
        }


        LocalDate currentDate = LocalDate.now();

        DataForRequestSchedule dataForRequestSchedule = new DataForRequestSchedule(currentDate.getYear(), currentDate.getMonthValue(), currentDate.getDayOfMonth(), user.getUserId(), user.getIdGroup(), "");

        UniversalRequest.connect(new RequestScheduleOptions(dataForRequestSchedule, ScheduleActivity.this, user), new ResponseCallback() {
            @Override
            public <Schedule> void onResponse(int code, Schedule gettingSchedule) {
                     if (code == 200) {
                         com.example.trening_tz.dto.schedule.Schedule schedule = (com.example.trening_tz.dto.schedule.Schedule) gettingSchedule;
//TODO Подумать, откуда вызывать создание расписание. Возможно подумать над переименованием класса BuildSchedule в просто Build и создание методов для разных случаев


                         //получение номера текущей недели, её типа и дня
                         UniversalRequest.connect(new RequestCurrentWeekOptions(currentDate, ScheduleActivity.this, user), new ResponseCallback() {
                            @Override
                            public <CurrentWeek> void onResponse(int code, CurrentWeek gettingCurrentWeek) {
                                if (code == 200) {
                                    com.example.trening_tz.dto.CurrentWeek currentWeek = (com.example.trening_tz.dto.CurrentWeek) gettingCurrentWeek;
                                    BuildSchedule.build(schedule, currentWeek.getWeekNum()-1, blockSchedule, ScheduleActivity.this);
                                }
                            }
                         });

                     }
            }
        });
    }
}