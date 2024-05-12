package com.example.cubex.model;

import com.example.cubex.DAO.MemberDAO;

import java.time.LocalDate;

public class Member extends CubeUser{
    private int idMember;
    private int idUserMember;
    private int discount;
    private LocalDate registrationDate;


    public Member(String mail, int idUserMember, LocalDate registrationDate) {
        super(mail);
        this.idUserMember = MemberDAO.insertIdUser(mail);
        this.registrationDate = registrationDate;
    }

    public Member(String nameUser, String passwordUser, int levelUser, Role roleUser, String mail, LocalDate registrationDate, String urlImagen, int idUserMember, int discount, LocalDate registrationDate1) {
        super(nameUser, passwordUser, levelUser, roleUser, mail, registrationDate, urlImagen);
        this.idUserMember = MemberDAO.insertIdUser(mail);
        this.discount = discount;
        this.registrationDate = registrationDate1;
    }

    @Override
    public String toString() {
        return "Member{" +
                "idMember=" + idMember +
                ", idUser=" + idUserMember +
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

    public int getIdUserMember() {
        return idUserMember;
    }

    public void setIdUserMember(int idUserMember) {
        this.idUserMember = idUserMember;
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
