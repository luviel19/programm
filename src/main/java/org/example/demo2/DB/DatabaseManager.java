package org.example.demo2.DB;// DatabaseManager.java
import java.sql.*;

public class DatabaseManager {
    private Connection connection;

    public DatabaseManager() {
        // инициализируйте соединение с базой данных
        connection = DBUtil.getConnection();
    }

    public boolean authenticateUser(String username, String password,String status) {
        boolean authenticated = false;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM autorization WHERE login =? AND password =? AND status=?");
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, status);
            resultSet = statement.executeQuery();
            authenticated = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return authenticated;
    }
}