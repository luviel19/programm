package org.example.demo2.Registration;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.demo2.DB.DBUtil;
import org.example.demo2.DB.DatabaseManager;
import org.example.demo2.auth.Autorization;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class RegController {

    @FXML
    private Button ExitButton;

    @FXML
    private AnchorPane autorization;

    @FXML
    private Text loginLabel;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField passwordField2;

    @FXML
    private Text passwordLabel;

    @FXML
    private Text passwordLabel1;

    @FXML
    private Button registerButton;

    @FXML
    private TextField usernameField = null;

   DBUtil dbUtil = new DBUtil();
   Connection conn = dbUtil.getConnection();

    @FXML
    public void initialize() {

        ExitButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                Autorization autorization = new Autorization();
                Stage stage = (Stage) ExitButton.getScene().getWindow();
                stage.close();
                autorization.start(stage);
            }



        });

        registerButton.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent event) {
                String username = usernameField.getText();
                String password = passwordField.getText();
                String password2 = passwordField2.getText();
                if (username != null && !username.isEmpty() && username.length()==12) {
                    if (password.equals(password2)) {
                        PreparedStatement preparedStatement = null;
                        try {
                            preparedStatement = conn.prepareStatement("SELECT * FROM autorization WHERE login = ?");
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            preparedStatement.setString(1, username);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        ResultSet resultSet = null;
                        try {
                            resultSet = preparedStatement.executeQuery();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            if(resultSet.next()){

                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Информация");
                                alert.setHeaderText(null);
                                alert.setContentText("Данный номер телефона уже зарегестрирован!");

// Show the alert
                                alert.showAndWait();

                            }else{
                                try {
                                    Connection connection = DBUtil.getConnection2();
                                    PreparedStatement pstmt = connection.prepareStatement("INSERT INTO autorization(login, password, status) VALUES(?,?,?)");
                                    pstmt.setString(1, username);
                                    pstmt.setString(2, password);
                                    pstmt.setString(3, "user");
                                    pstmt.executeUpdate();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Информация");
                                alert.setHeaderText(null);
                                alert.setContentText("Регистрация прошла успешно!");
                                alert.showAndWait();
                                Autorization autorization = new Autorization();
                                Stage stage = (Stage) ExitButton.getScene().getWindow();
                                stage.close();
                                autorization.start(stage);
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Информация");
                        alert.setHeaderText(null);
                        alert.setContentText("Пароли не совпадают!");

// Show the alert
                        alert.showAndWait();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Информация");
                    alert.setHeaderText(null);
                    alert.setContentText("Заполните поля!");

// Show the alert
                    alert.showAndWait();
                }
            }
        });

    }

}
