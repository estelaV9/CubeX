package com.example.cubex.DAO;

import com.example.cubex.Database.DatabaseConnection;
import com.example.cubex.model.CacheStatic;
import com.example.cubex.model.CubeUser;
import com.example.cubex.model.Member;
import javafx.scene.control.Alert;

import java.sql.*;
import java.time.LocalDate;

public class MemberDAO {

    public static int insertIdUser(String mail) {
        String sql = "SELECT ID_USER FROM CUBE_USERS WHERE MAIL = ? AND ROLE_USER = 'MEMBER';";
        int idUser = -1;
        try {
            Connection con = DatabaseConnection.conectar();
            PreparedStatement sentencia = con.prepareStatement(sql);
            sentencia.setString(1, mail);
            ResultSet consulta = sentencia.executeQuery();
            if (consulta.next()) {
                idUser = consulta.getInt("ID_USER");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error al convertir en miembro.");
                alert.setHeaderText("¡ERROR!");
                alert.setContentText("Error al convertir en miembro");
                alert.showAndWait();
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

    public static boolean insertMember(int idUser, LocalDate date) {
        String sql = "INSERT INTO MEMBERS (ID_USER, REGISTRATION_DATE) VALUES (?, ?);";
        boolean successfulModifyProUser = false;
        try {
            Connection con = DatabaseConnection.conectar();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, idUser);
            statement.setString(2, String.valueOf(date));
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                successfulModifyProUser = true;
            } else {
                successfulModifyProUser = false;
            }
            con.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return successfulModifyProUser;
    }

    public static boolean selectMember (String mail) {
        String sqlQuery = "SELECT * FROM MEMBERS WHERE ID_USER = (SELECT ID_USER FROM CUBE_USERS WHERE MAIL = ?);";
        boolean isMemberUser = false;
        try {
            Connection con = DatabaseConnection.conectar();
            PreparedStatement statement = con.prepareStatement(sqlQuery);
            statement.setString(1, mail);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                isMemberUser = true;
            }
            con.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return isMemberUser;
    } // SABER SI UN USUARIO ES MIEMBRO O NO


}
