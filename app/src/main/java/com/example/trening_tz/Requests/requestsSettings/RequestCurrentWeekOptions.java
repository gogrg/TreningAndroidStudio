package com.example.trening_tz.Requests.requestsSettings;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trening_tz.dto.CurrentWeek;
import com.example.trening_tz.dto.User;

import java.time.LocalDate;

public class RequestCurrentWeekOptions extends RequestOptions<CurrentWeek> {

    public RequestCurrentWeekOptions(LocalDate localDate, AppCompatActivity activity, User user) {
        super(localDate, activity);
        url += "rasp/getWeekInfo";
        tClass = CurrentWeek.class;
        headers.put("Content-Type", "application/json; charset=utf-8");
        headers.put("token", user.getToken());
        headers.put("Content-Length", "42");
        headers.put("ETag", "W/\"2a-M5plDnmsRRxMOOGgLJrEaaE0scc\"");
        headers.put("Connection", "keep-alive");
        headers.put("Keep-Alive", "timeout=5");
    }
}
