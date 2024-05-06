package com.example.cubex.model;

import java.time.LocalDate;

public class Session {
    private int idSession;
    private int idUser;
    private String nameSession;
    private LocalDate registrationDate;

    public Session(int idUser, String nameSession, LocalDate registrationDate) {
        this.idUser = idUser;
        this.nameSession = nameSession;
        this.registrationDate = registrationDate;
    }

    public int getIdSession() {
        return idSession;
    }

    public void setIdSession(int idSession) {
        this.idSession = idSession;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNameSession() {
        return nameSession;
    }

    public void setNameSession(String nameSession) {
        this.nameSession = nameSession;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }
}
