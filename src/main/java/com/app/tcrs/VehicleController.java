package com.app.tcrs;

import com.app.tcrs.dao.DriverDao;
import com.app.tcrs.dataio.DriverIO;
import com.app.tcrs.dataio.LicenseIO;
import com.app.tcrs.dataio.VehicleIO;
import com.app.tcrs.model.DriverModel;
import com.app.tcrs.model.LicenseModel;
import com.app.tcrs.model.VehicleModel;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class VehicleController extends  MainController{
    @FXML
    public Button btnSaveVehicle;
    @FXML
    public TextField txtDriverId;
    @FXML
    public TextField txtOwnerFirstName;
    @FXML
    public TextField txtOwnerLastName;
    @FXML
    public ComboBox<Integer> cboDrivers;
    @FXML
    public TextField txtRegNo;
    @FXML
    public TextField txtColor;
    @FXML
    public TextField txtModel;
    @FXML
    public TextField txtMake;
    @FXML
    public  TextField txtType;
    @FXML
    public Label errorLabel;

    public VehicleController(){
        super();
    }

    public  static void showAddVehicle() throws IOException {
        openFXMLFile("new-vehicle","Add New Vehicle");
    }



    public  void saveVehicle() throws Exception {

        String ownerFirstName = txtOwnerFirstName.getText();
        String ownerLastName = txtOwnerLastName.getText();
        String regNo = txtRegNo.getText();
        int driverId = Integer.parseInt(txtDriverId.getText());
        String color = txtColor.getText();
        String make = txtMake.getText();
        String type = txtType.getText();
        String model = txtModel.getText();

        VehicleModel vehicle = new VehicleModel();
        vehicle.setRegistrationNumber(regNo);
        vehicle.setOwnerLastName(ownerLastName);
        vehicle.setOwnerLastName(ownerLastName);
        vehicle.setDriverId(driverId);
        vehicle.setModel(model);
        vehicle.setColor(color);
        vehicle.setType(type);
        vehicle.setMake(make);

        // save vehicle
        int vehicleId = VehicleIO.createVehicle(vehicle);
        if(vehicleId != 0){
            TCRSApplication.changeScene("vehicle-database.fxml", true);
            this.quit();
        }else{
            showMessage(Alert.AlertType.WARNING,"Could not save vehicle");
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
