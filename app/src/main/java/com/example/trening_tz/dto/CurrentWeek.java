package com.example.trening_tz.dto;

import com.google.gson.annotations.SerializedName;

public class CurrentWeek {
    @SerializedName("week_num") private int weekNum;
    @SerializedName("week_type") private int weekType;
    @SerializedName("week_day") private int weekDay;

    public int getWeekNum() {
        return weekNum;
    }

    public void setWeekNum(int weekNum) {
        this.weekNum = weekNum;
    }

    public int getWeekType() {
        return weekType;
    }

    public void setWeekType(int weekType) {
        this.weekType = weekType;
    }

    public int getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(int weekDay) {
        this.weekDay = weekDay;
    }
}
