package com.app.tcrs;

import com.app.tcrs.dataio.CitationIO;
import com.app.tcrs.model.CitationModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;

public class TrafficSchoolLoginController extends  MainController{
    @FXML
    public Button btnLogin;
    @FXML
    public TextField txtSearch;
    @FXML
    public Label errorLabel;

    @FXML
    public Label lblCitationId;
    @FXML
    public Label lblFullName;
    @FXML
    public Label lblDob;
    @FXML
    public Label lblAge;
    @FXML
    public Label lblAddress;
    @FXML
    public  Label lblCity;
    @FXML
    public  Label lblProvince;
    @FXML
    public Label lblRegNoOrLicense;

    @FXML
    public TextArea txtCitationInfo;

    private  CitationModel citationModel = new CitationModel();
    public TrafficSchoolLoginController(){
        super();
    }


    public  void login() throws SQLException {
        try {
            String searchText = txtSearch.getText();
            System.out.println(searchText);
            if (searchText.isEmpty()) {
                showMessage(Alert.AlertType.WARNING,"Please enter citation/warrant no");
            }else {
                // search the citation or warrant
                CitationModel citationModel = CitationIO.getCitationById(Integer.parseInt(searchText));

                if(citationModel!= null){
                    TrafficSchoolMainController.setCitationId(citationModel.getCitationId());
                    TCRSApplication.changeScene("traffic-school-main.fxml", true);

                }else{
                    showMessage(Alert.AlertType.ERROR, "Citation\\warrant  not found");
                }
            }
        }catch (Exception exception){
            exception.printStackTrace();
            showMessage(Alert.AlertType.ERROR, "Could not get citation\\warrant");
        }

    }


    private  void showMessage(Alert.AlertType alertType, String message ){

        if(alertType == Alert.AlertType.INFORMATION){
            errorLabel.setStyle("text-fillgreen");
        }
        errorLabel.setVisible(true);
        errorLabel.setText(message);
    }
}
