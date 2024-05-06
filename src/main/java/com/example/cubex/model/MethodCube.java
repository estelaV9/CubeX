package com.example.cubex.model;

public class MethodCube {
    private int idMethod;
    private String nameMethod;

    public MethodCube(String nameMethod) {
        this.nameMethod = nameMethod;
    }

    public int getIdMethod() {
        return idMethod;
    }

    public void setIdMethod(int idMethod) {
        this.idMethod = idMethod;
    }

    public String getNameMethod() {
        return nameMethod;
    }

    public void setNameMethod(String nameMethod) {
        this.nameMethod = nameMethod;
    }
}
