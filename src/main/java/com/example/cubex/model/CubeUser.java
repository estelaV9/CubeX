package com.example.cubex.model;

import java.time.LocalDate;

public class CubeUser {
    private int idUser;
    private String nameUser;
    private String passwordUser;
    private int levelUser;
    private Role roleUser;
    private String mail;
    private LocalDate registrationDate;
    private String urlImagen;

    public CubeUser(String passwordUser, String mail) {
        this.passwordUser = passwordUser;
        this.mail = mail;
    }

    public CubeUser(String nameUser, String passwordUser, String mail, LocalDate registrationDate) {
        this.nameUser = nameUser;
        this.passwordUser = passwordUser;
        this.mail = mail;
        this.registrationDate = registrationDate;
    }

    public CubeUser(String nameUser, String passwordUser, int levelUser, Role roleUser, String mail, LocalDate registrationDate, String urlImagen) {
        this.nameUser = nameUser;
        this.passwordUser = passwordUser;
        this.levelUser = levelUser;
        this.roleUser = roleUser;
        this.mail = mail;
        this.registrationDate = registrationDate;
        this.urlImagen = urlImagen;
    }

    public enum Role{ USER, MEMBER; }

    @Override
    public String toString() {
        return "CubeUser{" +
                "idUser=" + idUser +
                ", nameUser='" + nameUser + '\'' +
                ", passwordUser='" + passwordUser + '\'' +
                ", levelUser=" + levelUser +
                ", roleUser='" + roleUser + '\'' +
                ", mail='" + mail + '\'' +
                ", registrationDate=" + registrationDate +
                ", urlImagen='" + urlImagen + '\'' +
                '}';
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }

    public int getLevelUser() {
        return levelUser;
    }

    public void setLevelUser(int levelUser) {
        this.levelUser = levelUser;
    }

    public Role getRoleUser() {
        return roleUser;
    }

    public void setRoleUser(Role roleUser) {
        this.roleUser = roleUser;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }
}
