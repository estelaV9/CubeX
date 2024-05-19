package com.example.cubex.model;

public class TimesChampionship {
    private int idTimesChamp;
    private int idUser;
    private String scramble;
    private int minutes;
    private double seconds;
    private String comments;
    private int idChamp;
    private int idType;

    public TimesChampionship(int idUser, String scramble, int minutes, double seconds, int idChamp, int idType) {
        this.idUser = idUser;
        this.scramble = scramble;
        this.minutes = minutes;
        this.seconds = seconds;
        this.idChamp = idChamp;
        this.idType = idType;
    }

    public int getIdTimesChamp() {
        return idTimesChamp;
    }

    public void setIdTimesChamp(int idTimesChamp) {
        this.idTimesChamp = idTimesChamp;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getScramble() {
        return scramble;
    }

    public void setScramble(String scramble) {
        this.scramble = scramble;
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

    public int getIdChamp() {
        return idChamp;
    }

    public void setIdChamp(int idChamp) {
        this.idChamp = idChamp;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }
}
