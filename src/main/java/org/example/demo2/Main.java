package org.example.demo2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;



import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        try {
            // Создаем FXMLLoader
            // Указываем путь к файлу FXML
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("test.fxml"));
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
