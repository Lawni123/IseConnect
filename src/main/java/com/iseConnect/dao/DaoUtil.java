package com.iseConnect.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DaoUtil {
    public Connection getConnection() {
        String url = "jdbc:postgresql://dpg-d00k1iali9vc73a0eve0-a.oregon-postgres.render.com/ise_connect";
        String username = "ise_connect_user";
        String password = "t9WCQuEAhfPFbmr72QMpg8kzclJmvmGO";
        
        try {
            // Correct driver for PostgreSQL
            Class.forName("org.postgresql.Driver");

            return DriverManager.getConnection(url, username, password);

        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error connecting to the database.");
            e.printStackTrace();
        }

        return null;
    }
}
