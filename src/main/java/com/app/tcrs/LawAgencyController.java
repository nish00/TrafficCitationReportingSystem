package com.app.tcrs;

import com.app.tcrs.dataio.AuthIO;
import com.app.tcrs.model.LawAgent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;

public class LawAgencyController extends  MainController{
    @FXML
    public Button btnLogin;
    @FXML
    public TextField txtBadgeNumber;
    @FXML
    public TextField txtUniqueKey;
    @FXML
    public Label errorLabel;

    @FXML
    private TextField titleField;

    public LawAgencyController()throws SQLException {
        super();
    }

    public  void login() throws Exception {

        String badgeNumber = txtBadgeNumber.getText();
        String uniqueKey = txtUniqueKey.getText();

        if (badgeNumber.isEmpty() || uniqueKey.isEmpty()) {
            showMessage(Alert.AlertType.WARNING,"Please enter badge number and unique key");
        }else{
            LawAgent lawAgent = AuthIO.loginAsLawEnforcementAgent(badgeNumber, uniqueKey);
            if(lawAgent != null && !lawAgent.getBadgeNumber().isEmpty()){
                TCRSApplication.setCurrentUser(lawAgent.getFullName());
                TCRSApplication.changeScene("request-info.fxml", true);
            }else{
                showMessage(Alert.AlertType.WARNING,"Login failed. Try again");
            }
        }
    }
    @FXML
    public static void logout() throws IOException {
        TCRSApplication.setCurrentUser(null);
        // show next screen on success
        TCRSApplication.changeScene("application.fxml",false);
    }
    @FXML
    private  void showMessage(Alert.AlertType alertType, String message ){

        if(alertType == Alert.AlertType.INFORMATION){
            errorLabel.setStyle("text-fill:green");
        }
        errorLabel.setVisible(true);
        errorLabel.setText(message);
    }


}
