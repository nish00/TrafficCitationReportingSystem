package com.app.tcrs;

import com.app.tcrs.dataio.CitationIO;
import com.app.tcrs.dataio.RequestIO;
import com.app.tcrs.model.CitationModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;

public class TrafficSchoolMainController extends  MainController{
    private static int citationId;
    @FXML
    public Button btnMakePayment;
    @FXML
    public Button btnRegisterDrivingSchool;
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
    public TrafficSchoolMainController(){
        super();
    }

    public  void initialize() throws IOException {
            showCitation();
    }

    public  static void setCitationId(int id){
        citationId = id;
    }
    public void  showCitation() throws IOException {
        try {
            CitationModel citation = CitationIO.getCitationById(citationId);

            if (citation != null) {

                lblCitationId.setText(citation.getCitationId() + "");
                String text = "Citation No: " + citation.getCitationId() +
                        "\r\nCitation Date: " + citation.getCitationDate().toString() +
                        "\r\nOffense: " + citation.getTypeOfOffense() +
                        "\r\nDate of Offense: " + citation.getDateOfOffense().toString() +
                        "\r\nIssuing Officer: " + citation.getIssuingOfficer() + "\r\n";

                text += "Pending Payment: CAD " + citation.getFineIssued() + "\r\n";

                text += "Status: " + citation.getStatus() + "\r\n";
                text += "Remarks: " + citation.getRemarks();

                txtCitationInfo.setText(text);

                lblFullName.setText(citation.getFullName());
                lblDob.setText(citation.getDateOfBirth().toString());
                lblAge.setText(citation.getAge() + "");
                lblAddress.setText(citation.getAddress1() +
                        ", " + citation.getCity() +
                        ", " + citation.getProvince());
                lblCity.setText(citation.getCity());
                lblProvince.setText(citation.getProvince());
                lblRegNoOrLicense.setText(citation.getVehicleRegOrDrivingLicense());

                if (citation.getStatus().equalsIgnoreCase("Resolved")) {
                    btnMakePayment.setStyle("--fxbackground-color: #8CB969");
                    btnMakePayment.setText("Paid");
                    btnRegisterDrivingSchool.setVisible(false);
                }

            } else {
                showMessage(Alert.AlertType.ERROR, "Citation\\warrant  not found");
            }

        }  catch (Exception exception){
        exception.printStackTrace();
        showMessage(Alert.AlertType.ERROR, "Could not get citation\\warrant");
    }
    }
    @FXML
    public  void makePayment(ActionEvent actionEvent){
        try{
            System.out.println("Making payment");
            CitationModel citationModel = CitationIO.getCitationById(citationId);
            citationModel.setStatus("Resolved");
            CitationIO.updateCitation(citationModel);
            showCitation();
        }catch (Exception exception){
            exception.printStackTrace();
            showMessage(Alert.AlertType.ERROR, "Payment failed");
        }
    }
    public  void showDrivingSchoolForm() throws IOException {
        DrivingSchoolController.citationId = citationId;
        openFXMLFile("driving-school-form","Register For Driving School");
    }

    private  void showMessage(Alert.AlertType alertType, String message ){

        if(alertType == Alert.AlertType.INFORMATION){
            errorLabel.setStyle("text-fill:green");
        }
        errorLabel.setVisible(true);
        errorLabel.setText(message);
    }
}
