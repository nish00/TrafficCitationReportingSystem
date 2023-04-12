package com.app.tcrs;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.Objects;

public class TCRSApplication extends Application {
    private static Stage primaryStage;

    public static String currentUser;

    public static void  changeScene(String fxml, boolean fullscreen) throws IOException {
        Parent pane = FXMLLoader.load(Objects.requireNonNull(TCRSApplication.class.getResource(fxml)));
        primaryStage.getScene().setRoot(pane);
        primaryStage.setResizable(true);
        if(fullscreen)
            primaryStage.setMaximized(true);
        else {
            primaryStage.setMaximized(false);
            primaryStage.centerOnScreen();
        }
    }

    public static void setCurrentUser(String name) {
        System.out.println(name);
        currentUser = name;
        System.out.println(currentUser);
    }

    public static String getCurrentUser() {
        System.out.println(currentUser);
        return currentUser;
    }

    public  static  void quitApplication(){
        primaryStage.close();
        System.exit(0);
    }

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                quitApplication();
            }
        });

        FXMLLoader loader = new FXMLLoader(getClass().getResource("application.fxml"));
        AnchorPane root = loader.load();
        ApplicationController appController = loader.getController();
        appController.setMainStage(primaryStage);

        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.setTitle("TCRS");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}