package org.example.demo2.Registration;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.demo2.Main;

import java.io.IOException;

public class RegMain extends Application{
    @Override
    public void start(Stage stage) {
        try {

            // Указываем путь к файлу FXML
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("registration.fxml"));
            // Загружаем контроллер
            Scene scene = new Scene(fxmlLoader.load());
            //устанавливамем название
            stage.setTitle("регистрация");
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
