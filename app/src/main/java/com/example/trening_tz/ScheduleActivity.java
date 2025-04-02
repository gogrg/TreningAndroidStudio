package com.example.trening_tz;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.trening_tz.dto.User;
import com.example.trening_tz.fragments.FragmentSchedule;
import com.example.trening_tz.servise.KeysFileEntry;
import com.example.trening_tz.servise.NamesFilesSetting;
import com.example.trening_tz.servise.StaticSharedPreferences;

import java.util.jar.Attributes;


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

        if (savedInstanceState == null) {
            FragmentSchedule fragmentSchedule = new FragmentSchedule(user);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.containerFragments, fragmentSchedule)
                    .commit();
        }
    }
}