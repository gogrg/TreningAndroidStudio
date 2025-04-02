package com.example.trening_tz.Requests;

import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trening_tz.Requests.requestsSettings.RequestScheduleOptions;
import com.example.trening_tz.dto.DataForRequestSchedule;
import com.example.trening_tz.dto.User;
import com.example.trening_tz.servise.GsonClass;
import com.example.trening_tz.servise.KeysFileSchedule;
import com.example.trening_tz.servise.NamesFilesSetting;
import com.example.trening_tz.servise.StaticSharedPreferences;

import java.net.HttpURLConnection;
import java.time.LocalDate;

public class RequestSchedule implements ResponseCallback {

    @Override
    public <T> void onResponse(int codeResponse, T gettingData) {

    }

    public static void getSchedule(AppCompatActivity activity, User user, ResponseCallback callback) {

        Log.d("TAG", "Metod getSchedule is started");

        LocalDate currentDate = LocalDate.now();

        DataForRequestSchedule dataForRequestSchedule = new DataForRequestSchedule(currentDate.getYear(), currentDate.getMonthValue(), currentDate.getDayOfMonth(),
                user.getUserId(), user.getIdGroup(), "");

        RequestScheduleOptions requestOptions = new RequestScheduleOptions(dataForRequestSchedule, activity, user);

        UniversalRequest.connect(requestOptions, new ResponseCallback() {
            @Override
            public <Schedule> void onResponse(int code, Schedule gettingSchedule) {

                Log.d("CODE RESPONSE SCHEDULE", String.valueOf(code));


                if (code == HttpURLConnection.HTTP_OK) {
                    //получаем расписание в виде правильного объекта
                    com.example.trening_tz.dto.schedule.Schedule schedule = (com.example.trening_tz.dto.schedule.Schedule) gettingSchedule;
                    //сохраняем в кэш полученное расписание, чтобы брать если нет инета
                    StaticSharedPreferences.putString(NamesFilesSetting.FILE_SCHEDULE.getValue(), KeysFileSchedule.SCHEDULE.getValue(), GsonClass.toJson(schedule), activity);

                    //передаём управление обратно
                    activity.runOnUiThread(() -> {
                        callback.onResponse(code, schedule);
                    });
                } else if (code == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    //если нет доступа, заново логинимся
                    LoginController.login(activity);
                    getSchedule(activity, user, callback);

                } else {
                    com.example.trening_tz.dto.schedule.Schedule schedule = StaticSharedPreferences.getObject(NamesFilesSetting.FILE_SCHEDULE.getValue(),
                            KeysFileSchedule.SCHEDULE.getValue(),
                            null,
                            com.example.trening_tz.dto.schedule.Schedule.class, activity);

                    Toast toast = Toast.makeText(activity, "Не удалось получить расписание", Toast.LENGTH_LONG);
                    toast.show();

                    activity.runOnUiThread(() -> {
                        callback.onResponse(code, schedule);
                    });
                }
            }
        });
    }
}
