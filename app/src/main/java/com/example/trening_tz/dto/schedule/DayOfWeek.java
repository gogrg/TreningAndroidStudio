package com.example.trening_tz.dto.schedule;

import com.example.trening_tz.dto.schedule.structureLesson.Lesson;

import java.util.List;

public class DayOfWeek {
    private List<Lesson> first;
    private List<Lesson> second;
    private List<Lesson> third;
    private List<Lesson> fourth;
    private List<Lesson> fifth;
    private List<Lesson> sixth;
    private List<Lesson> seventh;
    private List<Lesson> eighth;
    private List<Lesson> ninth;
    private List<Lesson> tenth;
    private List<Lesson> eleventh;
    private List<Lesson> twelfth;
    private List<Lesson> thirteenth;
    private List<Lesson> fourteenth;
    private List<Lesson> fifteenth;
    private List<Lesson> sixteenth;


    public List<Lesson> getSomeVariantDay(int numVariantDay) {
        switch (numVariantDay) {
            case 0:
                return first;
            case 1:
                return second;
            case 2:
                return third;
            case 3:
                return fourth;
            case 4:
                return fifth;
            case 5:
                return sixth;
            case 6:
                return seventh;
            case 7:
                return eighth;
            case 8:
                return ninth;
            case 9:
                return tenth;
            case 10:
                return eleventh;
            case 11:
                return twelfth;
            case 12:
                return thirteenth;
            case 13:
                return fourteenth;
            case 14:
                return fifteenth;
            case 15:
                return sixteenth;
            default:
                throw new IllegalArgumentException("Недопустимый номер варианта расписания на день: " + numVariantDay);
        }
    }
    public List<Lesson> getFirst() {
        return first;
    }

    public void setFirst(List<Lesson> first) {
        this.first = first;
    }

    public List<Lesson> getSecond() {
        return second;
    }

    public void setSecond(List<Lesson> second) {
        this.second = second;
    }

    public List<Lesson> getThird() {
        return third;
    }

    public void setThird(List<Lesson> third) {
        this.third = third;
    }

    public List<Lesson> getFourth() {
        return fourth;
    }

    public void setFourth(List<Lesson> fourth) {
        this.fourth = fourth;
    }

    public List<Lesson> getFifth() {
        return fifth;
    }

    public void setFifth(List<Lesson> fifth) {
        this.fifth = fifth;
    }

    public List<Lesson> getSixth() {
        return sixth;
    }

    public void setSixth(List<Lesson> sixth) {
        this.sixth = sixth;
    }

    public List<Lesson> getSeventh() {
        return seventh;
    }

    public void setSeventh(List<Lesson> seventh) {
        this.seventh = seventh;
    }

    public List<Lesson> getEighth() {
        return eighth;
    }

    public void setEighth(List<Lesson> eighth) {
        this.eighth = eighth;
    }

    public List<Lesson> getNinth() {
        return ninth;
    }

    public void setNinth(List<Lesson> ninth) {
        this.ninth = ninth;
    }

    public List<Lesson> getTenth() {
        return tenth;
    }

    public void setTenth(List<Lesson> tenth) {
        this.tenth = tenth;
    }

    public List<Lesson> getEleventh() {
        return eleventh;
    }

    public void setEleventh(List<Lesson> eleventh) {
        this.eleventh = eleventh;
    }

    public List<Lesson> getTwelfth() {
        return twelfth;
    }

    public void setTwelfth(List<Lesson> twelfth) {
        this.twelfth = twelfth;
    }

    public List<Lesson> getThirteenth() {
        return thirteenth;
    }

    public void setThirteenth(List<Lesson> thirteenth) {
        this.thirteenth = thirteenth;
    }

    public List<Lesson> getFourteenth() {
        return fourteenth;
    }

    public void setFourteenth(List<Lesson> fourteenth) {
        this.fourteenth = fourteenth;
    }

    public List<Lesson> getFifteenth() {
        return fifteenth;
    }

    public void setFifteenth(List<Lesson> fifteenth) {
        this.fifteenth = fifteenth;
    }

    public List<Lesson> getSixteenth() {
        return sixteenth;
    }

    public void setSixteenth(List<Lesson> sixteenth) {
        this.sixteenth = sixteenth;
    }
}
