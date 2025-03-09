package com.example.trening_tz.dto.schedule.structureLesson;

import com.google.gson.annotations.SerializedName;

public class Lesson {

    private Group group;
    @SerializedName("week_type") private int weekType;
    @SerializedName("week_day") private int weekDay;
    @SerializedName("week_num") private int weekNum;

    private Teacher firstTeacher;
    private Teacher secondTeacher;

    private String subject;

    private Room room;

    @SerializedName("lesson") private LessonInfo lessonInfo;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
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

    public int getWeekNum() {
        return weekNum;
    }

    public void setWeekNum(int weekNum) {
        this.weekNum = weekNum;
    }

    public Teacher getFirstTeacher() {
        return firstTeacher;
    }

    public void setFirstTeacher(Teacher firstTeacher) {
        this.firstTeacher = firstTeacher;
    }

    public Teacher getSecondTeacher() {
        return secondTeacher;
    }

    public void setSecondTeacher(Teacher secondTeacher) {
        this.secondTeacher = secondTeacher;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LessonInfo getLessonInfo() {
        return lessonInfo;
    }

    public void setLessonInfo(LessonInfo lessonInfo) {
        this.lessonInfo = lessonInfo;
    }
}
