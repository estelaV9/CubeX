package com.example.cubex.model;

import java.time.LocalDate;

public class Championship {
    private int ID_CHAMP;
    private int idUser;
    private String name_champ;
    private int price;
    private int numberPart;
    private String descriptionChamp;
    private LocalDate date;
    private boolean membersOnly;
    private String descriptionCategory;

    public Championship(int idUser, String name_champ, int price, int numberPart, String descriptionChamp, LocalDate date, boolean membersOnly, String descriptionCategory) {
        this.idUser = idUser;
        this.name_champ = name_champ;
        this.price = price;
        this.numberPart = numberPart;
        this.descriptionChamp = descriptionChamp;
        this.date = date;
        this.membersOnly = membersOnly;
        this.descriptionCategory = descriptionCategory;
    }

    public int getID_CHAMP() {
        return ID_CHAMP;
    }

    public void setID_CHAMP(int ID_CHAMP) {
        this.ID_CHAMP = ID_CHAMP;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getName_champ() {
        return name_champ;
    }

    public void setName_champ(String name_champ) {
        this.name_champ = name_champ;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getNumberPart() {
        return numberPart;
    }

    public void setNumberPart(int numberPart) {
        this.numberPart = numberPart;
    }

    public String getDescriptionChamp() {
        return descriptionChamp;
    }

    public void setDescriptionChamp(String descriptionChamp) {
        this.descriptionChamp = descriptionChamp;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isMembersOnly() {
        return membersOnly;
    }

    public void setMembersOnly(boolean membersOnly) {
        this.membersOnly = membersOnly;
    }

    public String getDescriptionCategory() {
        return descriptionCategory;
    }

    public void setDescriptionCategory(String descriptionCategory) {
        this.descriptionCategory = descriptionCategory;
    }
}
