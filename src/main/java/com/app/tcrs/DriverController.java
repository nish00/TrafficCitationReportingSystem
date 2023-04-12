package com.app.tcrs;

import com.app.tcrs.dataio.DriverIO;
import com.app.tcrs.dataio.LicenseIO;
import com.app.tcrs.model.DriverModel;
import com.app.tcrs.model.LicenseModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.time.LocalDate;

public class DriverController extends  MainController{
    @FXML
    public Button btnSaveDriver;
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
    public TextField txtLicenseNumber;
    @FXML
    public TextField txtLicenseType;
    @FXML
    public  DatePicker txtLicenseExpiryDate;
    @FXML
    public Label errorLabel;

    public DriverController(){
        super();
    }

    public  static void showAddDriver() throws IOException {
        openFXMLFile("new-driver","Add New Driver");
    }

    public  void saveDriver() throws Exception {

        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        LocalDate dateOfBirth = txtDateOfBirth.getValue();
        int age = Integer.parseInt( txtAge.getText());
        String address1 = txtAddress1.getText();
        String address2 = txtAddress2.getText();
        String city = txtCity.getText();
        String province = txtProvince.getText();
        String licenseNumber = txtLicenseNumber.getText();
        String licenseType = txtLicenseType.getText();
        LocalDate licenseExpiryDate = txtLicenseExpiryDate.getValue();

        DriverModel driver = new DriverModel();
        driver.setFirstName(firstName);
        driver.setLastName(lastName);
        driver.setDateOfBirth(dateOfBirth);
        driver.setAge(age);
        driver.setAddress1(address1);
        driver.setAddress2(address2);
        driver.setCity(city);
        driver.setProvince(province);

        // save driver
        int driverId = DriverIO.createDriver(driver);
        if(driverId != 0){
            LicenseModel license = new LicenseModel();
            license.setDriverId(driverId);
            license.setLicenseNumber(licenseNumber);
            license.setLicenseType(licenseType);
            license.setLicenseExpiryDate(licenseExpiryDate);

            // save license
            int licenseId = LicenseIO.createLicense(license);
            if(licenseId !=0){
                driver.setDriverId(driverId);
                driver.setLicenseId(licenseId);

                // save driver
                DriverIO.updateDriver(driver);
            }
            TCRSApplication.changeScene("driver-database.fxml", true);
            this.quit();
        }else{
            showMessage(Alert.AlertType.WARNING,"Could not save driver");
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
