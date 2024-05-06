package com.example.cubex.model;

import java.time.LocalDate;

public class TimeTraining {
    private int idTimesTraining;
    private String descriptionScramble;
    private int minutes;
    private double seconds;
    private String comments;
    private LocalDate registrationDate;
    private int idType;
    private int idSession;

    public TimeTraining(String descriptionScramble, int minutes, double seconds, String comments, LocalDate registrationDate, int idType, int idSession) {
        this.descriptionScramble = descriptionScramble;
        this.minutes = minutes;
        this.seconds = seconds;
        this.comments = comments;
        this.registrationDate = registrationDate;
        this.idType = idType;
        this.idSession = idSession;
    }

    public int getIdTimesTraining() {
        return idTimesTraining;
    }

    public void setIdTimesTraining(int idTimesTraining) {
        this.idTimesTraining = idTimesTraining;
    }

    public String getDescriptionScramble() {
        return descriptionScramble;
    }

    public void setDescriptionScramble(String descriptionScramble) {
        this.descriptionScramble = descriptionScramble;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public double getSeconds() {
        return seconds;
    }

    public void setSeconds(double seconds) {
        this.seconds = seconds;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public int getIdSession() {
        return idSession;
    }

    public void setIdSession(int idSession) {
        this.idSession = idSession;
    }
}
