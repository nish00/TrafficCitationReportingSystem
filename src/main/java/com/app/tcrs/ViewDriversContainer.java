package com.app.tcrs;

import com.app.tcrs.dao.DriverDao;
import com.app.tcrs.dataio.CitationIO;
import com.app.tcrs.dataio.DriverIO;
import com.app.tcrs.dataio.RequestIO;
import com.app.tcrs.model.CitationModel;
import com.app.tcrs.model.DriverModel;
import com.app.tcrs.model.RequestModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ViewDriversContainer extends MainController{
    @FXML
    private  TableView<DriverModel> tblDrivers;

    @FXML
    private TableColumn<DriverModel, Integer> idColumn;

    @FXML
    private TableColumn<DriverModel, String> nameColumn;

    @FXML
    private TableColumn<DriverModel, LocalDate> dobColumn;

    @FXML
    private TableColumn<DriverModel, Integer> ageColumn;
    @FXML
    private TableColumn<DriverModel, String> addressColumn;
    @FXML
    private TableColumn<DriverModel, String> cityColumn;
    @FXML
    private TableColumn<DriverModel, String> provinceColumn;
    @FXML
    private TableColumn<DriverModel, String> licenseNoColum;
    @FXML
    private TableColumn<DriverModel, String> licenseTypeColumn;
    @FXML
    private TableColumn<DriverModel, LocalDate> licenseExpiryColumn;

    @FXML
    private TableColumn<DriverModel, Boolean> statusColumn;
    @FXML
    private TextField  txtSearchById;
    @FXML
    private TextField  txtSearchByLicense;

    @FXML
    public Label errorLabel;

    @FXML
    public  TextArea txtInfo;
    @FXML
    public TextField txtRequestId;
    @FXML
    public  Button btnApprove;

    private String comment = null;

    public ViewDriversContainer () throws SQLException, ClassNotFoundException {
        super();
    }

    @FXML
    public void initialize() throws SQLException, ClassNotFoundException {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().driverIdProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().fullNameProperty());
        dobColumn.setCellValueFactory(cellData -> cellData.getValue().dobProperty());
        ageColumn.setCellValueFactory(cellData -> cellData.getValue().ageProperty().asObject());
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().address1Property());
        cityColumn.setCellValueFactory(cellData -> cellData.getValue().cityProperty());
        provinceColumn.setCellValueFactory(cellData -> cellData.getValue().provinceProperty());
        licenseNoColum.setCellValueFactory(cellData -> cellData.getValue().getLicense().licenseNumberProperty());
        licenseTypeColumn.setCellValueFactory(cellData -> cellData.getValue().getLicense().licenseTypeProperty());
        licenseExpiryColumn.setCellValueFactory(cellData -> cellData.getValue().getLicense().expiryDateProperty());

        loadAll();
    }
    public void showNewDriverForm(ActionEvent actionEvent) throws IOException {
        openFXMLFile("new-driver","Add New Driver");
    }

    @FXML
    public  void  showDriverDatabase(ActionEvent actionEvent) throws IOException {
        TCRSApplication.changeScene("driver-database.fxml", true);
    }

    @FXML
    public  void  showVehicleDatabase(ActionEvent actionEvent) throws IOException {
        TCRSApplication.changeScene("vehicle-database.fxml", true);
    }
    @FXML
    private void loadAll() throws SQLException, ClassNotFoundException  {
        //Get all
        ObservableList<DriverModel> drivers = DriverDao.searchDriverModels(true);
        //Populate  TableView
        populateDrivers(drivers);
    }

    @FXML
    public  void populateDrivers(ObservableList<DriverModel> drivers){
       tblDrivers.setItems(drivers);
       tblDrivers.autosize();
    }

    @FXML
    private void populateDriver (DriverModel driverModel) throws ClassNotFoundException {
        //Declare and ObservableList for table view
        ObservableList<DriverModel> drivers = FXCollections.observableArrayList();
        //Add employee to the ObservableList
        drivers.add(driverModel);
        //Set items to the table
        tblDrivers.setItems(drivers);
        tblDrivers.autosize();

        // check driver
    }

    @FXML
    private void searchDriverById (ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
        try {
            //Get driver information
            DriverModel driverModel = DriverIO.getDriver( Integer.parseInt(txtSearchById.getText()), true);
            //Populate driver on TableView and Display on TextArea
            populateDriver(driverModel);
            showInfo(driverModel);
        } catch (SQLException e) {
            e.printStackTrace();
            showMessage( Alert.AlertType.ERROR, "Error occurred while getting driver information from DB.\n" + e);
            throw e;
        }
    }
    @FXML
    private void searchDriverByLicense (ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
        try {
            //Get driver information
            DriverModel driverModel = DriverIO.getDriverByLicenceNumber(txtSearchByLicense.getText(), true);
            //Populate driver on TableView and Display on TextArea
            populateDriver(driverModel);
            showInfo(driverModel);
        } catch (SQLException e) {
            e.printStackTrace();
            showMessage( Alert.AlertType.ERROR, "Error occurred while getting driver information from DB.\n" + e);
            throw e;
        }
    }

    public  void showInfo(DriverModel driverModel){
        try{
            ArrayList<CitationModel> citationModels = CitationIO.getCitationsByVehicleRegOrLicenceNumber(driverModel.getLicense().getLicenseNumber());
            int unresolved = 0;
            if (!citationModels.isEmpty()){
                for (CitationModel citation:
                     citationModels) {
                    if(!citation.getStatus().equalsIgnoreCase("Resolved")){
                        unresolved +=1;
                    }
                }
            }

            String info = "";
            if(unresolved == 0){
                info = "Driver has clean record\r\nThere are no pending citations/warrant issued to driver";
            }else{
                CitationModel citationModel =citationModels.get(0);
                info ="Driver has been issued with " + citationModels.size() +" Citations\\Warrants\r\n"+
                        "Recent Citation\\Warrant\r\n" +
                        "Citation No: " + citationModel.getCitationId() +"\r\n" +
                        "Type Of Offense: " + citationModel.getTypeOfOffense() + "\r\n" +
                        "Citation Date: " + citationModel.getCitationDate().toString() +"\r\n" +
                        "Issuing Officer: " + citationModel.getIssuingOfficer() + "\r\n";
                if (!citationModel.toString().equalsIgnoreCase("Resolved")){
                    info +="Pending Payment: CAD " + citationModel.getFineIssued()+ "\r\n";
                }

                info += "Status: " + citationModel.getStatus();

            }
            txtInfo.setText(info);
            comment= info;

        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    public  void approveRequest(){
        try{
            int request = Integer.parseInt(txtRequestId.getText());
            RequestModel requestModel = RequestIO.getRequestById(request);
            if(requestModel!=null){
                requestModel.setApproved(true);
                requestModel.setComment(comment);
                requestModel.setApprovedBy(TCRSApplication.getCurrentUser());
                RequestIO.updateRequest(requestModel);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //Delete  driver with a given id
    @FXML
    private void deleteDriver (ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            DriverIO.deleteDriverById( Integer.parseInt(txtSearchById.getText()));
            showMessage( Alert.AlertType.INFORMATION,"Driver deleted! Driver id: " + txtSearchById.getText() + "\n");
        } catch (SQLException e) {
            showMessage( Alert.AlertType.ERROR,"Problem occurred while deleting driver " + e);
            throw e;
        }
    }
    private  void showMessage(Alert.AlertType alertType, String message ){

        if(alertType == Alert.AlertType.INFORMATION){
            errorLabel.setStyle("text-fill:green");
        }
        errorLabel.setVisible(true);
        errorLabel.setText(message);
    }
}
