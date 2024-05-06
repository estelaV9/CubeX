package com.example.cubex.model;

public class CubeType {
    private int idType;
    private String nameType;

    public CubeType(String nameType) {
        this.nameType = nameType;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public String getNameType() {
        return nameType;
    }

    public void setNameType(String nameType) {
        this.nameType = nameType;
    }
}
