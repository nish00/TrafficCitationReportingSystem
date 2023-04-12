package com.app.tcrs;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ApplicationController extends MainController {
    @FXML
    private Button btnLawAgency;
    @FXML
    private Button btnProvincialAgency;
    @FXML
    private Button btnTrafficSchool;
    @FXML
    private Button btnQuit;

    public ApplicationController() {
        super();
    }

    public  void openLawAgency() throws IOException {
        TCRSApplication.changeScene("law-agency-login.fxml", false);

    }
    public  void openProvincialAgency() throws IOException {
        TCRSApplication.changeScene("provincial-agency-login.fxml", false);
    }

    public  void openTrafficSchool() throws IOException {
        TCRSApplication.changeScene("traffic-school-login.fxml", false);
    }
    public void quit() {
        TCRSApplication.quitApplication();
    }

}
