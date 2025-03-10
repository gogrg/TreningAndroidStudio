package com.example.trening_tz.dto.schedule;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class Schedule {
    private @SerializedName("rasp_name") String groupName;
    private int currentWeekNum;
    private int currentWeekType;
    private int currentWeekDay;

    public class Payload {
        private DayOfWeek mon;
        private DayOfWeek thu;
        private DayOfWeek wen;
        private DayOfWeek thr;
        private DayOfWeek fri;
        private DayOfWeek sat;

        public DayOfWeek getMon() {
            return mon;
        }

        public void setMon(DayOfWeek mon) {
            this.mon = mon;
        }

        public DayOfWeek getThu() {
            return thu;
        }

        public void setThu(DayOfWeek thu) {
            this.thu = thu;
        }

        public DayOfWeek getWen() {
            return wen;
        }

        public void setWen(DayOfWeek wen) {
            this.wen = wen;
        }

        public DayOfWeek getThr() {
            return thr;
        }

        public void setThr(DayOfWeek thr) {
            this.thr = thr;
        }

        public DayOfWeek getFri() {
            return fri;
        }

        public void setFri(DayOfWeek fri) {
            this.fri = fri;
        }

        public DayOfWeek getSat() {
            return sat;
        }

        public void setSat(DayOfWeek sat) {
            this.sat = sat;
        }
    }

    private Payload payload;

    public DayOfWeek getDay(int numDay) {
        switch(numDay) {
            case 0:
                return payload.mon;
            case 1:
                return payload.thu;
            case 2:
                return payload.wen;
            case 3:
                return payload.thr;
            case 4:
                return payload.fri;
            case 5:
                return payload.sat;
            default:
                throw new IllegalArgumentException("Номер дня недели выходит за рамки допустимых значений: " + numDay);

        }
    }

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
