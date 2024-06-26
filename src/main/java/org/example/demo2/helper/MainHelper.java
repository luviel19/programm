package org.example.demo2.helper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.demo2.Main;

import java.io.IOException;

public class MainHelper extends Application {
    @Override
    public void start(Stage stage) {
        try {

            // Указываем путь к файлу FXML
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("help.fxml"));
            // Загружаем контроллер
            Scene scene = new Scene(fxmlLoader.load(), 818, 651);
            //устанавливамем название
            stage.setTitle("Хелпер");
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
