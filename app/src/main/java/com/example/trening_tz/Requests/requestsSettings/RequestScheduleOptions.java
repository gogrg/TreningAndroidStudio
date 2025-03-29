package com.example.trening_tz.Requests.requestsSettings;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trening_tz.dto.DataForRequestSchedule;
import com.example.trening_tz.dto.User;
import com.example.trening_tz.dto.schedule.Schedule;

public class RequestScheduleOptions extends RequestOptions<Schedule> {

    public RequestScheduleOptions(DataForRequestSchedule dataForRequestSchedule, AppCompatActivity activity, User user) {
        super(dataForRequestSchedule, activity);
        url += "rasp/getPersonalForUserSearch";
        tClass = Schedule.class;
        headers.put("token", user.getToken());
        headers.put("Content-Type", "application/json; charset = utf-8");
        headers.put("ContentLength", "181595");
        headers.put("ETag", "W/\"2c55b-+EIv6+N1+X9WqUvu4o028gA92yY\"");
        headers.put("Connection", "keep-alive");
        headers.put("Keep-Alive", "timeout=5");
    }
}