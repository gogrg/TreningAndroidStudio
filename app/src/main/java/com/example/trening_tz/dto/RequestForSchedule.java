package com.example.trening_tz.dto;

import com.google.gson.annotations.SerializedName;

public class RequestForSchedule {
    private class DateGson {
        private int day;
        private int month;
        private int year;

        DateGson() {
            day = 6;
            month = 3;
            year = 2025;
        }

        DateGson(int year, int month, int day) {
            this.day = day;
            this.month = month;
            this.year = year;
        }
        public void setDay(int day) {
            this.day = day;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getDay() {
            return day;
        }

        public int getMonth() {
            return month;
        }

        public int getYear() {
            return year;
        }
    }

    DateGson date;
    @SerializedName("employee_id") String employeeId;
    @SerializedName("group_id") String groupId;
    @SerializedName("cabinet_id") String cabinetId;

    public RequestForSchedule (int year, int month, int day, String employee_id, String group_id, String cabinet_id) {
        date = new DateGson(year, month, day);
        this.employeeId = employee_id;
        this.groupId = group_id;
        this.cabinetId = cabinet_id;
    }

    public DateGson getDate() {
        return date;
    }

    public void setDate(DateGson date) {
        this.date = date;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getCabinetId() {
        return cabinetId;
    }

    public void setCabinetId(String cabinetId) {
        this.cabinetId = cabinetId;
    }
}
