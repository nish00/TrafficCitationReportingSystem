package com.app.tcrs;

import com.app.tcrs.dataio.CitationIO;
import com.app.tcrs.dataio.DriverIO;
import com.app.tcrs.dataio.DrivingSchoolIO;
import com.app.tcrs.dataio.LicenseIO;
import com.app.tcrs.model.CitationModel;
import com.app.tcrs.model.DriverModel;
import com.app.tcrs.model.DrivingSchoolModel;
import com.app.tcrs.model.LicenseModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.LocalDate;

public class DrivingSchoolController extends  MainController{
    public static int citationId;

    @FXML
    public Button btnRegisterDrivingSchool;
    @FXML
    public RadioButton rdoEightHourClass;
    @FXML
    public RadioButton rdoFourHourClass;
    @FXML
    public DatePicker txtStartDate;
    public  DatePicker txtEndDate;
    @FXML
    public Label errorLabel;

    public DrivingSchoolController(){
        super();
    }


    public  void register() throws Exception {
    String drivingClass = "8 Hour Class";
       if(!rdoEightHourClass.isSelected()){
           drivingClass = "4 Hour Class X 2 days";
       }

       LocalDate startDate = txtStartDate.getValue();
       LocalDate endDate = txtEndDate.getValue();

        DrivingSchoolModel drivingSchoolModel = new DrivingSchoolModel();
        drivingSchoolModel.setCitationId(citationId);
        drivingSchoolModel.setDrivingClass(drivingClass);
        drivingSchoolModel.setStartDate(startDate);
        drivingSchoolModel.setEndDate(endDate);

        int regId = DrivingSchoolIO.createDrivingSchool(drivingSchoolModel);
        if(regId != 0){
            showMessage(Alert.AlertType.WARNING,"Registered Successfully. ");
            CitationModel citationModel = CitationIO.getCitationById(citationId);
            citationModel.setStatus("Resolved");
            CitationIO.updateCitation(citationModel);
            TCRSApplication.changeScene("traffic-school-main.fxml", true);

        }else{
            showMessage(Alert.AlertType.WARNING,"Could not register. ");
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
