package org.example.demo2.DB;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.demo2.MainController;

import java.sql.*;

public class EmployeeDAO {
    private Connection connection;

    public EmployeeDAO(Connection connection) {
        this.connection = connection;
    }

    public ObservableList<Employee> getEmployees() {
        ObservableList<Employee> employees = FXCollections.observableArrayList();

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM User");

            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setStatus(resultSet.getString("Status"));
                employee.setTime(resultSet.getString("Time"));
                employee.setName(resultSet.getString("Name"));
                employee.setPhone(resultSet.getString("PhoneNumber"));
                employee.setPost(resultSet.getString("Post"));

                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }
    public void updateEmployee(Employee employee) throws SQLException {
        Connection connection = DBUtil.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE User SET name =?, phone_number =?, status =?, time =?WHERE post =?");
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getPhone());
            preparedStatement.setString(3, employee.getStatus());
            preparedStatement.setString(4, employee.getTime());
            preparedStatement.setString(5, employee.getPost());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            DBUtil.closeConnection();
        }
    }
    }



