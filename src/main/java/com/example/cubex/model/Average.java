package com.example.cubex.model;

public class Average {
    private int idAverage;
    private int idUser;
    private int avgMinutes;
    private double avgSeconds;
    private int periodAvg;
    private int pbMinutes;
    private double pbSeconds;
    private int worstMinutes;
    private double worstSeconds;


    public Average(int idUser, int avgMinutes, double avgSeconds, int periodAvg, int pbMinutes, double pbSeconds, int worstMinutes, double worstSeconds) {
        this.idUser = idUser;
        this.avgMinutes = avgMinutes;
        this.avgSeconds = avgSeconds;
        this.periodAvg = periodAvg;
        this.pbMinutes = pbMinutes;
        this.pbSeconds = pbSeconds;
        this.worstMinutes = worstMinutes;
        this.worstSeconds = worstSeconds;
    }

    public int getIdAverage() {
        return idAverage;
    }

    public void setIdAverage(int idAverage) {
        this.idAverage = idAverage;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getAvgMinutes() {
        return avgMinutes;
    }

    public void setAvgMinutes(int avgMinutes) {
        this.avgMinutes = avgMinutes;
    }

    public double getAvgSeconds() {
        return avgSeconds;
    }

    public void setAvgSeconds(double avgSeconds) {
        this.avgSeconds = avgSeconds;
    }

    public int getPeriodAvg() {
        return periodAvg;
    }

    public void setPeriodAvg(int periodAvg) {
        this.periodAvg = periodAvg;
    }

    public int getPbMinutes() {
        return pbMinutes;
    }

    public void setPbMinutes(int pbMinutes) {
        this.pbMinutes = pbMinutes;
    }

    public double getPbSeconds() {
        return pbSeconds;
    }

    public void setPbSeconds(double pbSeconds) {
        this.pbSeconds = pbSeconds;
    }

    public int getWorstMinutes() {
        return worstMinutes;
    }

    public void setWorstMinutes(int worstMinutes) {
        this.worstMinutes = worstMinutes;
    }

    public double getWorstSeconds() {
        return worstSeconds;
    }

    public void setWorstSeconds(double worstSeconds) {
        this.worstSeconds = worstSeconds;
    }
}
