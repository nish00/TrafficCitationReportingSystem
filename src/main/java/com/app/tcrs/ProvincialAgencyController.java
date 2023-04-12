package com.app.tcrs;

import com.app.tcrs.dao.RequestDao;
import com.app.tcrs.dataio.AuthIO;
import com.app.tcrs.dataio.RequestIO;
import com.app.tcrs.model.LawAgent;
import com.app.tcrs.model.ProvisionalAgent;
import com.app.tcrs.model.RequestModel;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ProvincialAgencyController extends  MainController{
    @FXML
    public Button btnLogin;
    @FXML
    public TextField txtUsername;
    @FXML
    public TextField txtPassword;
    @FXML
    public Label errorLabel;
    @FXML
    public ListView<String> listRequests;
    @FXML
    public  Label lblCurrentUser;
    long delay = 2 * 1000; // delay in milliseconds = 2 SECONDS
    LoopTask task = new LoopTask();
    Timer timer = new Timer("RequestTimer");

    public ProvincialAgencyController(){
        super();
    }

    public void initialize() {
        timer.cancel();
        timer = new Timer("RequestTimer");
        Date executionDate = new Date(); // no params = now
        timer.scheduleAtFixedRate(task, executionDate, delay);

        if(lblCurrentUser != null) {
            lblCurrentUser.setText(TCRSApplication.getCurrentUser());
        }
    }
    public  void getRequests() throws SQLException, ClassNotFoundException {
        ArrayList<String> requests = new ArrayList<>();
        ObservableList<RequestModel> requestModels = RequestDao.searchRequestModels();
        if(!requestModels.isEmpty()){
            for (RequestModel request :
                    requestModels) {
                if(!request.isApproved()) {
                    String requestText = "Request No: " + request.getRequestId() + "" +
                            "\r\n" + request.getRequestType() + ": " + request.getVehicleRegOrDrivingLicense();
                   requests.add(requestText);
                }
            }
        }
        listRequests.setItems(FXCollections.observableArrayList(requests));

    }

    public  void login() throws IOException, SQLException {

        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showMessage(Alert.AlertType.WARNING,"Please enter username and password");
        }else{
            ProvisionalAgent agent = AuthIO.loginAsProvincialAgent(username, password);
            if(agent != null && !agent.getUsername().isEmpty()){
                TCRSApplication.setCurrentUser(agent.getFullName());
                // show next screen on success
                TCRSApplication.changeScene("driver-database.fxml",true);
            }else{
                showMessage(Alert.AlertType.WARNING,"Login failed. Try again");
            }

        }
    }

    public void logout() throws IOException {
        TCRSApplication.setCurrentUser(null);
        // show next screen on success
        TCRSApplication.changeScene("application.fxml",false);
    }

    public  void  showDriverDatabase(ActionEvent actionEvent) throws IOException {
        TCRSApplication.changeScene("driver-database.fxml", true);
        if(lblCurrentUser != null) {
            lblCurrentUser.setText(TCRSApplication.getCurrentUser());
        }
    }

    @FXML
    public  void  showVehicleDatabase(ActionEvent actionEvent) throws IOException {
        TCRSApplication.changeScene("vehicle-database.fxml", true);
        if(lblCurrentUser != null) {
            lblCurrentUser.setText(TCRSApplication.getCurrentUser());
        }
    }
    private  void showMessage(Alert.AlertType alertType, String message ){

        if(alertType == Alert.AlertType.INFORMATION){
            errorLabel.setStyle("text-fill:green");
        }
        errorLabel.setVisible(true);
        errorLabel.setText(message);
    }

    private class LoopTask extends TimerTask {
        public void run() {
            try {
                getRequests();
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
