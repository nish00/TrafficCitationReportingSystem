package com.app.tcrs.dao;

import com.app.tcrs.dataio.RequestIO;
import com.app.tcrs.model.RequestModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RequestDao {

    //*******************************
    //SELECT an RequestModel
    //*******************************
    public static RequestModel searchRequestModel (int requestId) throws SQLException, ClassNotFoundException {
        //Execute SELECT statement
        try {
            //Return request object
            return RequestIO.getRequestById(requestId);
        } catch (SQLException e) {
            System.out.println("While searching an request with " + requestId + " id, an error occurred: " + e);
            //Return exception
            throw e;
        }
    }

    public static RequestModel searchRequestModelByVehicleRegOrDrivingLicense(String vehicleRegOrDrivingLicense) throws SQLException, ClassNotFoundException {
        //Execute SELECT statement
        try {
            //Return request object
            return RequestIO.getRequestByVehicleRegOrLicenceNumber(vehicleRegOrDrivingLicense);
        } catch (SQLException e) {
            System.out.println("While searching an request with Vehicle Reg / Driving License No" + vehicleRegOrDrivingLicense + ", an error occurred: " + e);
            //Return exception
            throw e;
        }
    }


    //*******************************
    //SELECT RequestModels
    //*******************************
    public static ObservableList<RequestModel> searchRequestModels () throws SQLException, ClassNotFoundException {

        try {
            //Send ResultSet to the getRequestModelList method and get request object
            ArrayList<RequestModel> requestModelArrayList = RequestIO.getRequests();
            ObservableList<RequestModel> requests = FXCollections.observableArrayList();

            if(requestModelArrayList != null) {
                requests.addAll(requestModelArrayList);
            }
            //Return request object
            return requests;
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            //Return exception
            throw e;
        }
    }




}
