package com.app.tcrs.dao;

import com.app.tcrs.dataio.DriverIO;
import com.app.tcrs.model.DriverModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DriverDao {

    //*******************************
    //SELECT an DriverModel
    //*******************************
    public static DriverModel searchDriverModel (int driverId, boolean withLicense) throws SQLException, ClassNotFoundException {
        //Execute SELECT statement
        try {
            //Return driver object
            return DriverIO.getDriver(driverId, withLicense);
        } catch (SQLException e) {
            System.out.println("While searching an driver with " + driverId + " id, an error occurred: " + e);
            //Return exception
            throw e;
        }
    }

    public static DriverModel searchDriverModelByLicenseNo (String licenseNo, boolean withLicense) throws SQLException, ClassNotFoundException {
        //Execute SELECT statement
        try {
            //Return driver object
            return DriverIO.getDriverByLicenceNumber(licenseNo, withLicense);
        } catch (SQLException e) {
            System.out.println("While searching an driver with License No" + licenseNo + " id, an error occurred: " + e);
            //Return exception
            throw e;
        }
    }

    //Use ResultSet from DB as parameter and set DriverModel Object's attributes and return driver object.
    private static DriverModel getDriverModelFromResultSet(ResultSet rs, boolean withLicense) throws SQLException
    {
        DriverModel driverModel = null;
        if (rs.next()) {
            driverModel = DriverIO.resultSetToDriver(rs, withLicense);
        }
        return driverModel;
    }

    //*******************************
    //SELECT DriverModels
    //*******************************
    public static ObservableList<DriverModel> searchDriverModels (boolean withLicense) throws SQLException, ClassNotFoundException {

        try {
            //Send ResultSet to the getDriverModelList method and get driver object
            ArrayList<DriverModel> driverModelArrayList = DriverIO.getDrivers(withLicense);
            ObservableList<DriverModel> drivers = FXCollections.observableArrayList();

            if(driverModelArrayList != null) {
                drivers.addAll(driverModelArrayList);
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
