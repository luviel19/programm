package org.example.demo2.DB;

import org.example.demo2.BDName;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static Connection connection;
static BDName bdName = new BDName();
    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://109.71.243.44:3306/Jobs?serverTimezone=UTC", bdName.getName(), bdName.getPassword());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
    public static Connection getConnection2() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://109.71.243.44:3306/autorization?serverTimezone=UTC",bdName.getName(), bdName.getPassword());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}