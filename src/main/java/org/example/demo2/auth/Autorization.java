package org.example.demo2.auth;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.demo2.Main;

import java.io.IOException;

public class Autorization extends Application {
    @Override
    public void start(Stage stage) {
        try {
            // Создаем FXMLLoader
            // Указываем путь к файлу FXML
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("autorization.fxml"));
            // Загружаем контроллер
            Scene scene = new Scene(fxmlLoader.load());
            //устанавливамем название
            stage.setTitle("Авторизация");
            // Устанавливаем сцену в primaryStage
            stage.setScene(scene);


            stage.setResizable(true);
            // Показываем primaryStage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main (String[]args){
        launch();
    }
}
