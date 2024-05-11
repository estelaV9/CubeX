package com.example.cubex.DAO;

import com.example.cubex.Database.DatabaseConnection;
import com.example.cubex.model.CacheStatic;
import com.example.cubex.model.CubeUser;
import com.example.cubex.model.Member;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class MemberDAO {
    public static int seleccionarUser(String mail) {
        String sql = "SELECT ID_USER FROM CUBE_USERS WHERE MAIL = ? AND ROLE_USER = 'MEMBER';";
        int idUser = -1;
        try {
            Connection con = DatabaseConnection.conectar();
            PreparedStatement sentencia = con.prepareStatement(sql);
            sentencia.setString(1, mail);
            ResultSet consulta = sentencia.executeQuery();
            if (consulta.next()) {
                idUser = consulta.getInt("ID_USER");
            }
            con.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return idUser;
    }


    public static void insertNewMember(String nameUser, String passwordUser, String mail) {
        String insertMemberQuery = "INSERT INTO MEMBERS (ID_USER, DISCOUNT, REGISTRATION_DATE) VALUES (LAST_INSERT_ID(), 0, ?)";

        try (Connection con = DatabaseConnection.conectar();
             PreparedStatement userStatement = con.prepareStatement(insertUserQuery);
             PreparedStatement memberStatement = con.prepareStatement(insertMemberQuery)) {

            // Setear parámetros para la inserción en CUBE_USERS
            userStatement.setString(1, nameUser);
            userStatement.setString(2, passwordUser);
            userStatement.setString(3, mail);
            userStatement.setDate(4, java.sql.Date.valueOf(LocalDate.now())); // Obtener la fecha actual

            // Ejecutar la inserción en CUBE_USERS
            userStatement.executeUpdate();

            // Setear parámetros para la inserción en MEMBERS
            memberStatement.setDate(1, java.sql.Date.valueOf(LocalDate.now())); // Obtener la fecha actual

            // Ejecutar la inserción en MEMBERS
            memberStatement.executeUpdate();

            // Mostrar mensaje de éxito
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Nuevo miembro creado");
            alert.setHeaderText("Éxito");
            alert.setContentText("Se ha creado un nuevo miembro correctamente.");
            alert.showAndWait();

        }
    }
}
