package org.example.demo2.UserPanel;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.demo2.Main;

import java.io.IOException;

import static javafx.application.Application.launch;

public class MainUserPane extends Application {
    @Override
    public void start(Stage stage) {
        try {
            // Создаем FXMLLoader
            // Указываем путь к файлу FXML
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("user.fxml"));
            // Загружаем контроллер
            Scene scene = new Scene(fxmlLoader.load(), 818, 651);
            //устанавливамем название
            stage.setTitle("Справочник");
            // Устанавливаем сцену в primaryStage
            stage.setScene(scene);
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