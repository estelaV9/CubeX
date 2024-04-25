package com.example.cubex.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static Connection conectar() {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CubeX_DB", "cubeX", "CubeX");
            System.out.println("Conectado correctamente");
        } catch (SQLException e) {
            System.out.println("Error al conectarme a la base de datos " + e);
        }
        return con;
    }
}

