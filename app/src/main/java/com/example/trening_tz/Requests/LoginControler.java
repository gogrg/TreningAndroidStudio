package com.example.trening_tz.Requests;

import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trening_tz.Requests.requestsSettings.RequestEntryOptions;
import com.example.trening_tz.dto.DataForRequestEntry;
import com.example.trening_tz.dto.User;
import com.example.trening_tz.servise.KeysFileEntry;
import com.example.trening_tz.servise.NamesFilesSetting;
import com.example.trening_tz.servise.StaticSharedPreferences;

public class LoginControler {
    public static void login(AppCompatActivity activity) {
        String login = StaticSharedPreferences.getString(NamesFilesSetting.FILE_ENTRY.getValue(), KeysFileEntry.LOGIN.getValue(), "", activity);
        String password = StaticSharedPreferences.getString(NamesFilesSetting.FILE_ENTRY.getValue(), KeysFileEntry.PASSWORD.getValue(), "", activity);

        DataForRequestEntry dataForRequestEntry = new DataForRequestEntry(login, password);

        User user;

        UniversalRequest.connect(new RequestEntryOptions(dataForRequestEntry, activity), new ResponseCallback() {
            @Override
            public <User> void onResponse(int codeResponce, User gettingData) {
                if (codeResponce == 200) {
                    //TODO сделать здесь запихивание к кэш юзера, чтобы потом где надо можно было извлекать
                }
            }
        });
    }

    public static void logout(AppCompatActivity activity) {
        StaticSharedPreferences.clear(NamesFilesSetting.FILE_ENTRY.getValue(), activity);
        StaticSharedPreferences.clear(NamesFilesSetting.FILE_SCHEDULE.getValue(), activity);
        //TODO если добавятся новые файлы с настройками их тоже чистить
    }
}
