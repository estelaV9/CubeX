package com.example.cubex.model;

import java.time.LocalDate;

public class TimeCompetition {
    private int idTimeCompe;
    private int idUserCompe;
    private String descriptionScramble;
    private int minutes1;
    private double seconds1;
    private String comments1;
    private int minutes2;
    private double seconds2;
    private String comments2;
    private int idCompe;
    private int idType;

    public TimeCompetition(String descriptionScramble, int idUserCompe, int minutes1, double seconds1, String comments1, int minutes2, double seconds2, String comments2, int idCompe, int idType) {
        this.descriptionScramble = descriptionScramble;
        this.idUserCompe = idUserCompe;
        this.minutes1 = minutes1;
        this.seconds1 = seconds1;
        this.comments1 = comments1;
        this.minutes2 = minutes2;
        this.seconds2 = seconds2;
        this.comments2 = comments2;
        this.idCompe = idCompe;
        this.idType = idType;
    }

    public int getIdUserCompe() {
        return idUserCompe;
    }

    public void setIdUserCompe(int idUserCompe) {
        this.idUserCompe = idUserCompe;
    }

    public int getIdTimeCompe() {
        return idTimeCompe;
    }

    public void setIdTimeCompe(int idTimeCompe) {
        this.idTimeCompe = idTimeCompe;
    }

    public String getDescriptionScramble() {
        return descriptionScramble;
    }

    public void setDescriptionScramble(String descriptionScramble) {
        this.descriptionScramble = descriptionScramble;
    }

    public int getMinutes1() {
        return minutes1;
    }

    public void setMinutes1(int minutes1) {
        this.minutes1 = minutes1;
    }

    public double getSeconds1() {
        return seconds1;
    }

    public void setSeconds1(double seconds1) {
        this.seconds1 = seconds1;
    }

    public String getComments1() {
        return comments1;
    }

    public void setComments1(String comments1) {
        this.comments1 = comments1;
    }

    public int getMinutes2() {
        return minutes2;
    }

    public void setMinutes2(int minutes2) {
        this.minutes2 = minutes2;
    }

    public double getSeconds2() {
        return seconds2;
    }

    public void setSeconds2(double seconds2) {
        this.seconds2 = seconds2;
    }

    public String getComments2() {
        return comments2;
    }

    public void setComments2(String comments2) {
        this.comments2 = comments2;
    }

    public int getIdCompe() {
        return idCompe;
    }

    public void setIdCompe(int idCompe) {
        this.idCompe = idCompe;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }
}
