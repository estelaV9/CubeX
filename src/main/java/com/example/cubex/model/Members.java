package com.example.cubex.model;

import java.time.LocalDate;

public class Members {
    private int idMember;
    private int idUser;
    private int discount;
    private LocalDate registrationDate;

    public Members(int idUser, int discount, LocalDate registrationDate) {
        this.idUser = idUser;
        this.discount = discount;
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return "Members{" +
                "idMember=" + idMember +
                ", idUser=" + idUser +
                ", discount=" + discount +
                ", registrationDate=" + registrationDate +
                '}';
    }

    public int getIdMember() {
        return idMember;
    }

    public void setIdMember(int idMember) {
        this.idMember = idMember;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }
}
