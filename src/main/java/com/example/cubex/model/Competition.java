package com.example.cubex.model;

import java.time.LocalDate;

public class Competition {
    private int idCompe;
    private int idUser;
    private String cuber1;
    private String cuber2;
    private String winner;
    private LocalDate registrationDate;

    // AL SER WINNER NULO, SE CREA EL CONSTRUCTOR SIN EL WINNER
    public Competition(int idUser, String cuber1, String cuber2, LocalDate registrationDate) {
        this.idUser = idUser;
        this.cuber1 = cuber1;
        this.cuber2 = cuber2;
        this.registrationDate = registrationDate;
    }

    public int getIdCompe() {
        return idCompe;
    }

    public void setIdCompe(int idCompe) {
        this.idCompe = idCompe;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getCuber1() {
        return cuber1;
    }

    public void setCuber1(String cuber1) {
        this.cuber1 = cuber1;
    }

    public String getCuber2() {
        return cuber2;
    }

    public void setCuber2(String cuber2) {
        this.cuber2 = cuber2;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }
}
