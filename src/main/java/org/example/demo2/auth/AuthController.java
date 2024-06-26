package org.example.demo2.auth;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.demo2.DB.DatabaseManager;
import org.example.demo2.Main;
import org.example.demo2.Registration.RegMain;
import org.example.demo2.UserPanel.MainUserPane;

public class AuthController {

    @FXML
    private Button ExitButton;
    @FXML
    private Button regButton;
    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    private DatabaseManager databaseManager;

    @FXML
    private AnchorPane autorization;


    @FXML
    private void initialize() {


        autorization.setOnKeyPressed(new EventHandler<KeyEvent>() {
    public void handle(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
           loginButton.fire();
        }else if (event.getCode() == KeyCode.ESCAPE) {
            ExitButton.fire();
        }
    }
});


        ExitButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                ((Stage) autorization.getScene().getWindow()).close();
            }
        });
        databaseManager = new DatabaseManager();
        regButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                RegMain regMain = new RegMain();
                Stage stage = (Stage) regButton.getScene().getWindow();
                stage.close();
                regMain.start(stage);
            }
        });
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String username = usernameField.getText();
                String password = passwordField.getText();

                if (databaseManager.authenticateUser(username, password,"admin")) {
                    // Авторизация успешна, запускаем другую панель
                    Main main = new Main();
                    Stage mainStage = new Stage();
                    main.start(mainStage);

                    // Закрываем авторизационное окно
                    ((Stage) loginButton.getScene().getWindow()).close();
                } else if (databaseManager.authenticateUser(username, password,"user")) {

                    MainUserPane mainUserPane = new MainUserPane();
                    Stage mainStage = new Stage();
                    mainUserPane.start(mainStage);

                    ((Stage) loginButton.getScene().getWindow()).close();
            } else {
                    // Авторизация неудачна, выводим сообщение об ошибке
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка авторизации!");
                    alert.setHeaderText(null);
                    alert.setContentText("Не верный логин или пароль!");
                    alert.showAndWait();
                }
            }
        });








    }




}