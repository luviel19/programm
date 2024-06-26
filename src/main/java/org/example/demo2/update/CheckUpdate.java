package org.example.demo2.update;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;
import org.example.demo2.DB.DBUtil;
import org.example.demo2.Main;
import org.example.demo2.auth.Autorization;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CheckUpdate {
    private HostServices hostServices;
    @FXML
    private Hyperlink link;

    @FXML
    private Button okey;


    @FXML
    public void initialize() {


        Connection connection = DBUtil.getConnection();
            try {
                String currentVersion = "2.0.1"; // Замените эту строку на вашу переменную

                PreparedStatement stmt = connection.prepareStatement("SELECT version, link FROM ValidVerion");
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    String dbVersion = rs.getString("version");
                    String dbLink = rs.getString("link");
                    if (currentVersion.equals(dbVersion)) {
                        Autorization main = new Autorization();
                        Stage mainStage = new Stage();
                        main.start(mainStage);
                        // Закрываем авторизационное окно
                        ((Stage) okey.getScene().getWindow()).close();

                    } else {
                        okey.setOnAction(event -> {
                            Autorization main = new Autorization();
                            Stage mainStage = new Stage();
                            main.start(mainStage);
                            // Закрываем авторизационное окно
                            ((Stage) okey.getScene().getWindow()).close();
                        });

                        link.setOnAction(e -> {
                            String url = dbLink;
                            String os = System.getProperty("os.name").toLowerCase();
                            Runtime rt = Runtime.getRuntime();

                            try {
                                if (os.indexOf("win") >= 0) {
                                    rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
                                } else if (os.indexOf("mac") >= 0) {
                                    rt.exec("open " + url);
                                } else if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0) {
                                    rt.exec("xdg-open " + url);
                                } else {
                                    System.err.println("Unsupported platform");
                                }
                            } catch (Exception ex) {
                                System.err.println("Error opening URL: " + ex.getMessage());
                            }
                        });
                        }
                    }
                } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }

    }

