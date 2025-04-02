package com.example.trening_tz.Requests;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trening_tz.Requests.requestsSettings.RequestEntryOptions;
import com.example.trening_tz.dto.DataForRequestEntry;
import com.example.trening_tz.servise.GsonClass;
import com.example.trening_tz.servise.KeysFileEntry;
import com.example.trening_tz.servise.NamesFilesSetting;
import com.example.trening_tz.servise.StaticSharedPreferences;

import java.net.HttpURLConnection;

public class LoginController implements ResponseCallback {

    @Override
    public <T> void onResponse(int code, T gettingData) {

    }
    public static void login(AppCompatActivity activity) {
        String login = StaticSharedPreferences.getString(NamesFilesSetting.FILE_ENTRY.getValue(), KeysFileEntry.LOGIN.getValue(), "", activity);
        String password = StaticSharedPreferences.getString(NamesFilesSetting.FILE_ENTRY.getValue(), KeysFileEntry.PASSWORD.getValue(), "", activity);

        DataForRequestEntry dataForRequestEntry = new DataForRequestEntry(login, password);

        UniversalRequest.connect(new RequestEntryOptions(dataForRequestEntry, activity), new ResponseCallback() {
            @Override
            public <User> void onResponse(int codeResponce, User gettingData) {
                if (codeResponce == HttpURLConnection.HTTP_OK) {
                    com.example.trening_tz.dto.User user = (com.example.trening_tz.dto.User) gettingData;

                    StaticSharedPreferences.putString(NamesFilesSetting.FILE_ENTRY.getValue(), KeysFileEntry.USER_JSON.getValue(), GsonClass.toJson(gettingData), activity);
                    //TODO сделать здесь запихивание к кэш юзера, чтобы потом где надо можно было извлекать
                } else if (codeResponce == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    LoginController.logout(activity);
                }
            }
        });
    }

    public static void login(String login, String password, AppCompatActivity activity, ResponseCallback callback) {
        DataForRequestEntry dataForRequestEntry = new DataForRequestEntry(login, password);

        UniversalRequest.connect(new RequestEntryOptions(dataForRequestEntry, activity), new ResponseCallback() {
            @Override
            public <User> void onResponse(int codeResponce, User gettingData) {
                if (codeResponce == HttpURLConnection.HTTP_OK) {
                    com.example.trening_tz.dto.User user = (com.example.trening_tz.dto.User) gettingData;

                    StaticSharedPreferences.putString(NamesFilesSetting.FILE_ENTRY.getValue(), KeysFileEntry.USER_JSON.getValue(), GsonClass.toJson(user), activity);
                    StaticSharedPreferences.putString(NamesFilesSetting.FILE_ENTRY.getValue(), KeysFileEntry.LOGIN.getValue(), login, activity);
                    StaticSharedPreferences.putString(NamesFilesSetting.FILE_ENTRY.getValue(), KeysFileEntry.PASSWORD.getValue(), password, activity);

                    activity.runOnUiThread(() -> {
                        callback.onResponse(codeResponce, user);
                    });

                } else if (codeResponce == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    LoginController.logout(activity);
                    activity.runOnUiThread(() -> {
                        callback.onResponse(codeResponce, gettingData);
                    });
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
