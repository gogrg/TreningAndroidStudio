package com.example.trening_tz.GsonClass;

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
    String employee_id;
    String group_id;
    String cabinet_id;

    public RequestForSchedule (int year, int month, int day, String employee_id, String group_id, String cabinet_id) {
        date = new DateGson(year, month, day);
        this.employee_id = employee_id;
        this.group_id = group_id;
        this.cabinet_id = cabinet_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public void setCabinet_id(String cabinet_id) {
        this.cabinet_id = RequestForSchedule.this.cabinet_id;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public String getGroup_id() {
        return group_id;
    }

    public String getCabinet_id() {
        return cabinet_id;
    }

    public DateGson getDate() {
        return date;
    }
}
