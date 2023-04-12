package com.app.tcrs;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public abstract class  MainController {
    static Stage mainStage ;
    MainController(){};
    public static void setMainStage(Stage mainStage) {
        mainStage = mainStage;
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public static void openFXMLFile(String filename, String title) throws IOException {
        URL fxmlUrl = MainController.class.getResource(filename +".fxml" );
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        AnchorPane root = loader.load();
        Stage stage = new Stage();
       setMainStage(stage);
        stage.setScene(new Scene(root));
        stage.setTitle(title);
        stage.show();
    }

    public void quit() {
        mainStage.close();
    }
}