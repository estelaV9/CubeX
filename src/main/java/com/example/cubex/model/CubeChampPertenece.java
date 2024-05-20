package com.example.cubex.model;

public class CubeChampPertenece {
    private int idType;
    private int idChamp;
    private String winner;

    public CubeChampPertenece(int idType, int idChamp, String winner) {
        this.idType = idType;
        this.idChamp = idChamp;
        this.winner = winner;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public int getIdChamp() {
        return idChamp;
    }

    public void setIdChamp(int idChamp) {
        this.idChamp = idChamp;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
