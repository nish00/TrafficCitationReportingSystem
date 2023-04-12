package com.app.tcrs.dao;

import com.app.tcrs.dataio.VehicleIO;
import com.app.tcrs.model.VehicleModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VehicleDao {

    //*******************************
    //SELECT an VehicleModel
    //*******************************
    public static VehicleModel searchVehicleModel (int vehicleId, boolean withDriver) throws SQLException, ClassNotFoundException {
        //Execute SELECT statement
        try {
            //Return driver object
            return VehicleIO.getVehicle(vehicleId, withDriver);
        } catch (SQLException e) {
            System.out.println("While searching an vehicle with " + vehicleId + " id, an error occurred: " + e);
            //Return exception
            throw e;
        }
    }

    public static VehicleModel searchVehicleModelByRegNo (String regNo, boolean withDriver) throws SQLException, ClassNotFoundException {
        //Execute SELECT statement
        try {
            //Return driver object
            return VehicleIO.getVehicleByRegistrationNumber(regNo, withDriver);
        } catch (SQLException e) {
            System.out.println("While searching an vehicle with RegNo: " + regNo + ", an error occurred: " + e);
            //Return exception
            throw e;
        }
    }

    //Use ResultSet from DB as parameter and set VehicleModel Object's attributes and return driver object.
    private static VehicleModel getVehicleModelFromResultSet(ResultSet rs, boolean withDriver) throws SQLException
    {
        VehicleModel vehicleModel = null;
        if (rs.next()) {
            vehicleModel = VehicleIO.resultSetToVehicle(rs, withDriver);
        }
        return vehicleModel;
    }

    //*******************************
    //SELECT VehicleModels
    //*******************************
    public static ObservableList<VehicleModel> searchVehicleModels (boolean withDriver) throws SQLException, ClassNotFoundException {

        try {
            //Send ResultSet to the getVehicleModelList method and get driver object
            ArrayList<VehicleModel> vehicleModelArrayList = VehicleIO.getVehicles(withDriver);
            ObservableList<VehicleModel> drivers = FXCollections.observableArrayList();

            if(vehicleModelArrayList != null) {
                drivers.addAll(vehicleModelArrayList);
            }
            //Return driver object
            return drivers;
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            //Return exception
            throw e;
        }
    }




}
