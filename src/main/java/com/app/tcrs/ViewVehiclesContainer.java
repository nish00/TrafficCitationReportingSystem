package com.app.tcrs;

import com.app.tcrs.dao.DriverDao;
import com.app.tcrs.dao.VehicleDao;
import com.app.tcrs.dataio.CitationIO;
import com.app.tcrs.dataio.DriverIO;
import com.app.tcrs.dataio.RequestIO;
import com.app.tcrs.dataio.VehicleIO;
import com.app.tcrs.model.CitationModel;
import com.app.tcrs.model.DriverModel;
import com.app.tcrs.model.RequestModel;
import com.app.tcrs.model.VehicleModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ViewVehiclesContainer extends MainController{
    @FXML
    private  TableView<VehicleModel> tblVehicle;

    @FXML
    private TableColumn<VehicleModel, Integer> idColumn;

    @FXML
    private TableColumn<VehicleModel, String> regNoColumn;

    @FXML
    private TableColumn<VehicleModel, String> modelColumn;

    @FXML
    private TableColumn<VehicleModel, String> makeColumn;
    @FXML
    private TableColumn<VehicleModel, String> typeColumn;
    @FXML
    private TableColumn<VehicleModel, String> ownerColumn;
    @FXML
    private TableColumn<VehicleModel, String> driverColumn;
    @FXML
    private TableColumn<VehicleModel, String> licenseNoColum;
    @FXML
    private TableColumn<VehicleModel, String> licenseTypeColumn;
    @FXML
    private TableColumn<VehicleModel, LocalDate> licenseExpiryColumn;

    @FXML
    private TableColumn<VehicleModel, Boolean> statusColumn;
    @FXML
    private TextField  txtSearchByRegNo;
    @FXML
    private TextField  txtSearchById;

    @FXML
    public Label errorLabel;
    @FXML
    public  TextArea txtInfo;
    @FXML
    public TextField txtRequestId;
    private String comment = null;

    public ViewVehiclesContainer() throws SQLException, ClassNotFoundException {
        super();
    }

    @FXML
    public void initialize() throws SQLException, ClassNotFoundException {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().vehicleIdProperty().asObject());
        regNoColumn.setCellValueFactory(cellData -> cellData.getValue().registrationNumberProperty());
        modelColumn.setCellValueFactory(cellData -> cellData.getValue().modelProperty());
        makeColumn.setCellValueFactory(cellData -> cellData.getValue().makeProperty());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        ownerColumn.setCellValueFactory(cellData -> cellData.getValue().ownerFullNameProperty());
        driverColumn.setCellValueFactory(cellData -> cellData.getValue().getDriver().fullNameProperty());
        licenseNoColum.setCellValueFactory(cellData -> cellData.getValue().getDriver().getLicense().licenseNumberProperty());
        licenseTypeColumn.setCellValueFactory(cellData -> cellData.getValue().getDriver().getLicense().licenseTypeProperty());
        licenseExpiryColumn.setCellValueFactory(cellData -> cellData.getValue().getDriver().getLicense().expiryDateProperty());
        loadAll();
    }
    public void showNewVehicleForm(ActionEvent actionEvent) throws IOException {
        openFXMLFile("new-vehicle","Add New Vehicle");
    }

    @FXML
    private void loadAll() throws SQLException, ClassNotFoundException  {
        //Get all
        ObservableList<VehicleModel> vehicleModels = VehicleDao.searchVehicleModels(true);
        //Populate  TableView
        populateVehicles(vehicleModels);
    }

    @FXML
    public  void populateVehicles(ObservableList<VehicleModel> vehicleModels){
        System.out.println(vehicleModels.size());
       tblVehicle.setItems(vehicleModels);
       tblVehicle.autosize();
    }

    @FXML
    private void populateVehicle (VehicleModel vehicleModel) throws ClassNotFoundException {
        //Declare and ObservableList for table view
        ObservableList<VehicleModel> vehicleModels = FXCollections.observableArrayList();
        //Add to the ObservableList
        vehicleModels.add(vehicleModel);
        //Set items to the table
        tblVehicle.setItems(vehicleModels);
        tblVehicle.autosize();
    }

    @FXML
    private void searchVehicleRegNo (ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
        try {
            //Get vehicle information
            VehicleModel vehicleModel = VehicleDao.searchVehicleModelByRegNo( txtSearchByRegNo.getText(), true);
            //Populate vehicle on TableView and Display on TextArea
            populateVehicle(vehicleModel);
            showInfo(vehicleModel);
        } catch (SQLException e) {
            e.printStackTrace();
            showMessage( Alert.AlertType.ERROR, "Error occurred while getting vehicle information from DB.\n" + e);
            throw e;
        }
    }
    @FXML
    private void searchByID (ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
        try {
            //Get vehicle information
            VehicleModel vehicleModel = VehicleIO.getVehicle(Integer.parseInt(txtSearchById.getText()), true);
            //Populate vehicle on TableView and Display on TextArea
            populateVehicle(vehicleModel);
            showInfo(vehicleModel);
        } catch (SQLException e) {
            e.printStackTrace();
            showMessage( Alert.AlertType.ERROR, "Error occurred while getting driver information from DB.\n" + e);
            throw e;
        }
    }

    public  void showInfo(VehicleModel vehicleModel){
        try{
            ArrayList<CitationModel> citationModels = CitationIO.getCitationsByVehicleRegOrLicenceNumber(vehicleModel.getRegistrationNumber());
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
                info = "Vehicle has clean record\r\nThere are no pending citations/warrant issued to vehicle";
            }else{
                CitationModel citationModel =citationModels.get(0);
                info ="Vehicle has been issued with " + citationModels.size() +" Citations\\Warrants\r\n"+
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
            txtInfo.setEditable(false);
            comment = info;
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
    private void deleteVehicle (ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            VehicleIO.deleteVehicleById( Integer.parseInt(txtSearchById.getText()));
            showMessage( Alert.AlertType.INFORMATION,"Vehicle deleted! Vehicle id: " + txtSearchById.getText() + "\n");
        } catch (SQLException e) {
            showMessage( Alert.AlertType.ERROR,"Problem occurred while deleting vehicle " + e);
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
