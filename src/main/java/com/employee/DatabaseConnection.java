package com.employee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String DEFAULT_URL =
            "jdbc:mysql://localhost:3306/employee_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    private static final String DEFAULT_USER = "root";
    private static final String DEFAULT_PASSWORD = "root123"; // Replace with your MySQL root password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DEFAULT_URL,
                DEFAULT_USER,
                DEFAULT_PASSWORD
        );
    }
}