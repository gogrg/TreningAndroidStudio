package com.example.trening_tz.dto.schedule;

import com.example.trening_tz.dto.schedule.daysWeek.Mon;
import com.example.trening_tz.dto.schedule.daysWeek.Thu;
import com.google.gson.annotations.SerializedName;

public class Schedule {
    private @SerializedName("rasp_name") String groupName;
    private int currentWeekNum;
    private int currentWeekType;
    private int currentWeekDay;



    public class Payload {
        private Mon mon;
        private Thu thu;

        public Mon getMon() {
            return mon;
        }

        public void setMon(Mon mon) {
            this.mon = mon;
        }

        public Thu getThu() {
            return thu;
        }

        public void setThu(Thu thu) {
            this.thu = thu;
        }
    }

    private Payload payload;
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getCurrentWeekNum() {
        return currentWeekNum;
    }

    public void setCurrentWeekNum(int currentWeekNum) {
        this.currentWeekNum = currentWeekNum;
    }

    public int getCurrentWeekType() {
        return currentWeekType;
    }

    public void setCurrentWeekType(int currentWeekType) {
        this.currentWeekType = currentWeekType;
    }

    public int getCurrentWeekDay() {
        return currentWeekDay;
    }

    public void setCurrentWeekDay(int currentWeekDay) {
        this.currentWeekDay = currentWeekDay;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }
}
