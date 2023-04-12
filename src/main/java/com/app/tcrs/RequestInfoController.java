package com.app.tcrs;

import com.app.tcrs.dao.DriverDao;
import com.app.tcrs.database.Database;
import com.app.tcrs.dataio.CitationIO;
import com.app.tcrs.dataio.RequestIO;
import com.app.tcrs.dataio.VehicleIO;
import com.app.tcrs.model.CitationModel;
import com.app.tcrs.model.DriverModel;
import com.app.tcrs.model.RequestModel;
import com.app.tcrs.model.VehicleModel;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class RequestInfoController extends  MainController{
    @FXML
    public TextField txtSearch;
    @FXML
    public TextArea txtDrivingRecord;
    @FXML
    public Label lblFullName;
    @FXML
    public Label lblDob;
    @FXML
    public Label lblAge;
    @FXML
    public Label lblAddress;
    @FXML
    public Label lblLicenseNo;
    @FXML
    public Label lblLicenseType;
    @FXML
    public Label lblLicenseExp;
    @FXML
    public Label lblOwner;
    @FXML
    public Label lblRegNo;
    @FXML
    public Label lblMake;
    @FXML
    public Label lblModel;
    @FXML
    public Label lblType;
    @FXML
    public Label lblColor;
    @FXML
    public Label errorLabel;
    @FXML
    public Label lblCurrentUser;

    @FXML
    public  Button btnIssueCitation;
    @FXML
    public  Button btnRequestByLicense;
    @FXML
    public Button btnRequestByRegNo;

    @FXML
    public ListView<String> listCitations;
    @FXML
    public ProgressIndicator progressIndicator;

    private int requestId;

    long delay = 2 * 1000; // delay in milliseconds = 2 seconds
    LoopTask task = new LoopTask();
    Timer timer = new Timer("RequestTimer");

    public RequestInfoController(){
        super();
    }

    public void initialize() {
        timer.cancel();
        timer = new Timer("RequestTimer");
        Date executionDate = new Date(); // no params = now
        timer.scheduleAtFixedRate(task, executionDate, delay);
        lblCurrentUser.setText( TCRSApplication.getCurrentUser());
        progressIndicator.setVisible(false);
    }
    @FXML
    public  void issueCitation(ActionEvent actionEvent) throws IOException {
        openFXMLFile("citation-form","Issue Citation");
    }

    public void logout() throws IOException {
        LawAgencyController.logout();
    }
    public  void requestInfoByLicense(ActionEvent actionEvent){
        try {
            RequestModel requestModel = new RequestModel();
            requestModel.setVehicleRegOrDrivingLicense(txtSearch.getText());
            requestModel.setRequestType("Driver License");
            requestModel.setRequestedBy(TCRSApplication.getCurrentUser());
            int requestId =RequestIO.createRequest(requestModel);

            if(requestId != 0 ){
                disableSearch(true);
                this.requestId = requestId;
                task.run();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private  void disableSearch( boolean disabled){

        if(disabled){
            progressIndicator.setVisible(true);
            showMessage(Alert.AlertType.INFORMATION, "Request Send. Waiting Approval ...");
        }

        txtSearch.setDisable(disabled);
        btnRequestByRegNo.setDisable(disabled);
        btnRequestByLicense.setDisable(disabled);
    }

    public  void requestInfoByVehicleRegNo(ActionEvent actionEvent){
        try {
            RequestModel requestModel = new RequestModel();
            requestModel.setVehicleRegOrDrivingLicense(txtSearch.getText());
            requestModel.setRequestType("Vehicle Reg No:");
            requestModel.setRequestedBy(TCRSApplication.getCurrentUser());
            int requestId =RequestIO.createRequest(requestModel);

            if(requestId !=0 ){

                disableSearch(true);
                this.requestId = requestId;
                task.run();
            }
        }catch (Exception e){
            e.printStackTrace();
            return;
        }
    }

    public  void showDriverInfo(DriverModel driver) throws Exception {
        lblFullName.setText(driver.getFullName());
        lblDob.setText(driver.getDateOfBirth().toString());
        lblAge.setText(driver.getAge()+"");
        lblAddress.setText(driver.getAddress1() +
                "," + driver.getCity() +
                "," + driver.getProvince());
        lblLicenseNo.setText(driver.getLicense().getLicenseNumber() );
        lblLicenseType.setText(driver.getLicense().getLicenseType());
        lblLicenseExp.setText(driver.getLicense().getLicenseExpiryDate().toString());
    }
    public  void showVehicleInfo(VehicleModel vehicle) throws Exception {
        lblOwner.setText(vehicle.getFullName());
        lblRegNo.setText(vehicle.getRegistrationNumber());
        lblType.setText(vehicle.getType());
        lblModel.setText(vehicle.getModel());
        lblMake.setText(vehicle.getMake() );
        lblColor.setText(vehicle.getColor());

    }

    public void searchByLicense(String licenseNo){
        try{
            DriverModel driver = DriverDao.searchDriverModelByLicenseNo( licenseNo, true);
            showDriverInfo(driver);
//            ResultSet rs = Database.getResultFromSqlQuery("SELECT * FROM Vehicle WHERE DriverID =" + driver.getDriverId() + "  LIMIT 1");
//            if(rs.next()){
//                VehicleModel vehicle = VehicleIO.resultSetToVehicle(rs, true);
//                showVehicleInfo(vehicle);
//            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    public void searchByRegNo(String vehicleRegNo){
        try{
            VehicleModel vehicle = VehicleIO.getVehicleByRegistrationNumber( vehicleRegNo, false);
//            showDriverInfo(vehicle.getDriver());
            showVehicleInfo(vehicle);
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
    private  void showCitations(String vehicleRegNoOrDrivingLicense){
       try{
           ArrayList<String> citations = new ArrayList<>();
           ArrayList<CitationModel> citationModels = CitationIO.getCitationsByVehicleRegOrLicenceNumber(vehicleRegNoOrDrivingLicense);

           if (!citationModels.isEmpty()){
               for (CitationModel citation:
                       citationModels) {
                   String text = "Citation No: " + citation.getCitationId() +
                           "\r\nDate: " + citation.getCitationDate().toString() +
                           "\r\nOffense: " + citation.getTypeOfOffense() +
                           "\r\nIssuing Officer: " + citation.getIssuingOfficer() ;

                   if (!citation.getStatus().equalsIgnoreCase("Resolved")){
                       text +="Pending Payment: CAD " + citation.getFineIssued()+ "\r\n";
                   }

                   text += "Status: " + citation.getStatus();
                   citations.add(text);
               }
           }
           listCitations.setItems(FXCollections.observableArrayList(citations));
       } catch (Exception exception){
           exception.printStackTrace();
           showMessage(Alert.AlertType.ERROR, "Could not retrieve citations:" + exception.getMessage());
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
                RequestModel request = RequestIO.getRequestById(requestId);
                if(request != null && request.isApproved() ){
                    task.cancel();
                    timer.cancel();
                    timer.purge();
                    Platform.runLater(() -> {
                        showMessage(Alert.AlertType.INFORMATION, "Done. Request approved ");
                        progressIndicator.setVisible(false);
                        txtDrivingRecord.setEditable(false);
                        txtDrivingRecord.setText(request.getComment());
                        disableSearch(false);
                        if (request.getRequestType().equalsIgnoreCase("Driver License")) {
                            searchByLicense(request.getVehicleRegOrDrivingLicense());
                        } else {
                            searchByRegNo(request.getVehicleRegOrDrivingLicense());
                        }
                        showCitations(request.getVehicleRegOrDrivingLicense());
                    });
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
