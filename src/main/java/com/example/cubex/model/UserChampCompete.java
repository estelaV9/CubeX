package com.example.cubex.model;

public class UserChampCompete {
    private int idUser;
    private int idChamp;

    public UserChampCompete(int idUser) {
        this.idUser = idUser;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdChamp() {
        return idChamp;
    }

    public void setIdChamp(int idChamp) {
        this.idChamp = idChamp;
    }
}
