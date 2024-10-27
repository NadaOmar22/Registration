package com.registration.configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    public static final String JDBC_URL = "jdbc:postgresql://localhost:5432/registrationDB";
    public static final String JDBC_USER = "postgres";
    public static final String JDBC_PASSWORD = "2212";


    public Connection getConnection() throws SQLException, ClassNotFoundException {
        System.out.println("DatabaseConfig : getConnection : START");
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        System.out.println("DatabaseConfig : getConnection : END");
        return connection;
    }
}
