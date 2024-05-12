package com.example.cubex.model;

import javafx.fxml.FXML;

import java.time.LocalDate;

public class Session {
    private int idSession;
    private int idUser;
    private String nameSession;
    private LocalDate registrationDate;
    private int idType;

    public Session(int idUser, String nameSession, LocalDate registrationDate, int idType) {
        this.idUser = idUser;
        this.nameSession = nameSession;
        this.registrationDate = registrationDate;
        this.idType = idType;
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
