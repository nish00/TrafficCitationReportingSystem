package com.app.tcrs;

import com.app.tcrs.dataio.CitationIO;
import com.app.tcrs.dataio.DriverIO;
import com.app.tcrs.dataio.LicenseIO;
import com.app.tcrs.model.CitationModel;
import com.app.tcrs.model.DriverModel;
import com.app.tcrs.model.LicenseModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.LocalDate;

public class CitationController extends  MainController{
    private  static String vehicleRegOrLicense;
    @FXML
    public TextField txtFirstName;
    @FXML
    public TextField txtLastName;
    @FXML
    public DatePicker txtDateOfBirth;
    @FXML
    public TextField txtAge;
    @FXML
    public TextField txtAddress1;
    @FXML
    public TextField txtAddress2;
    @FXML
    public TextField txtCity;
    @FXML
    public TextField txtProvince;
    @FXML
    public TextField txtVehicleRegOrLicense;
    @FXML
    public DatePicker txtCitationDate;
    @FXML
    public TextField txtTypeOfOffense;
    @FXML
    public DatePicker txtDateOfOffense;
    @FXML
    public TextField txtFineIssued;
    @FXML
    public DatePicker txtLastPaymentDate;
    @FXML
    public TextArea txtRemarks;
    @FXML
    public Button btnSaveAsWarrant;
    @FXML
    public Button btnSaveAsCitation;
    @FXML
    public Label errorLabel;

    public CitationController(){
        super();
    }

    public  static void showAddDriver() throws IOException {
        openFXMLFile("new-driver","Add New Driver");
    }

    @FXML
    public  void initialize(){

    }

    public  static  void setVehicleRegOrLicense(String regOrLicense){
        vehicleRegOrLicense = regOrLicense;
    }

    public  static void loadInfo(){

    }
    public CitationModel prepareDetails (){
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        LocalDate dateOfBirth = txtDateOfBirth.getValue();
        int age = Integer.parseInt( txtAge.getText());
        String address1 = txtAddress1.getText();
        String address2 = txtAddress2.getText();
        String city = txtCity.getText();
        String province = txtProvince.getText();

        String vehicleRegOrLicense = txtVehicleRegOrLicense.getText();
        String offenseType = txtTypeOfOffense.getText();
        LocalDate dateOfOffense = txtDateOfOffense.getValue();
        LocalDate citationDate = txtCitationDate.getValue();
        Double fineIssued = Double.parseDouble(txtFineIssued.getText());
        LocalDate lastPaymentDate = txtLastPaymentDate.getValue();
        String remarks = txtRemarks.getText();

        String issuingOfficer = TCRSApplication.getCurrentUser();

        CitationModel citationModel = new CitationModel();
        citationModel.setFirstName(firstName);
        citationModel.setLastName(lastName);
        citationModel.setDateOfBirth(dateOfBirth);
        citationModel.setAge(age);
        citationModel.setAddress1(address1);
        citationModel.setAddress2(address2);
        citationModel.setCity(city);
        citationModel.setProvince(province);
        citationModel.setTypeOfOffense(offenseType);
        citationModel.setDateOfOffense(dateOfOffense);
        citationModel.setCitationDate(citationDate);
        citationModel.setRemarks(remarks);
        citationModel.setIssuingOfficer(issuingOfficer);
        citationModel.setFineIssued(fineIssued);
        citationModel.setLastPaymentDate(lastPaymentDate);
        citationModel.setVehicleRegOrDrivingLicense(vehicleRegOrLicense);

        return citationModel;
    }
    @FXML
    public  void saveAsWarrant(ActionEvent actionEvent) throws Exception {
        CitationModel citationModel = prepareDetails();
        citationModel.setStatus("Warrant");
        saveCitation(citationModel);
    }
    @FXML
    public  void saveAsCitation(ActionEvent actionEvent) throws Exception {
        CitationModel citationModel = prepareDetails();
        citationModel.setStatus("Citation");
        saveCitation(citationModel);
    }

    public  void saveCitation(CitationModel citationModel){
        int citationId = CitationIO.createCitation(citationModel);

        if(citationId!=0){
            showMessage(Alert.AlertType.INFORMATION,"Citation created successful. Citation\\Warrant No:" + citationId);
        }else{
            showMessage(Alert.AlertType.ERROR,"Could not save citation");
        }
    }

    private  void showMessage(Alert.AlertType alertType, String message ){

        if(alertType == Alert.AlertType.INFORMATION){
            errorLabel.setStyle("text-fill:green");
        }else{
            errorLabel.setStyle("text-fill: red");
        }
        errorLabel.setVisible(true);
        errorLabel.setText(message);
    }

}
