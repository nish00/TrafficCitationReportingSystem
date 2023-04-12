package com.app.tcrs.dataio;

import com.app.tcrs.database.Database;
import com.app.tcrs.model.DriverModel;
import com.app.tcrs.model.VehicleModel;

import java.sql.*;
import java.util.ArrayList;

public class VehicleIO {
    private final static Connection connection = Database.getConnection();
    public static int createVehicle(VehicleModel vehicleModel) {
        try {
            String insertQuery = "INSERT INTO Vehicle("
                    + "RegistrationNumber" + ","
                    + "OwnerFirstName" + ","
                    + "OwnerLastName" + ","
                    + "Color" + ","
                    + "Type" + ","
                    + "Model" + ","
                    + "Make" + ","
                    + "DriverID"
                    + ") VALUES(?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, vehicleModel.getRegistrationNumber());
            preparedStatement.setString(2, vehicleModel.getOwnerFirstName());
            preparedStatement.setString(3, vehicleModel.getOwnerLastName());
            preparedStatement.setString(4, vehicleModel.getColor());
            preparedStatement.setString(5,vehicleModel.getType());
            preparedStatement.setString(6, vehicleModel.getModel());
            preparedStatement.setString(7, vehicleModel.getMake());
            preparedStatement.setInt(8, vehicleModel.getDriverId());

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();

            if(rs.next())
            {
                return rs.getInt(1);
            }

            return 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    //get drivers from drivers data file
    public static ArrayList<VehicleModel> getVehicles( boolean withDriver) throws SQLException {
        ArrayList<VehicleModel> drivers = new ArrayList<>();
        try
        {

            ResultSet rs = Database.getResultFromSqlQuery("SELECT * FROM Vehicle ORDER BY CreatedAt DESC");
            while (rs.next()) {
                drivers.add( resultSetToVehicle(rs, withDriver));
            }
            return drivers;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static VehicleModel getVehicle(int vehicleId, boolean withDriver) throws SQLException {
        VehicleModel vehicle = null;
        try
        {
            String query = "SELECT * FROM Vehicle WHERE VehicleID = " + vehicleId + " LIMIT 1";
            ResultSet rs = Database.getResultFromSqlQuery(query);
            while (rs.next())
            {
                vehicle = resultSetToVehicle(rs, withDriver);
            }

        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return vehicle;
    }
    public static VehicleModel getVehicleByRegistrationNumber(String registrationNumber, boolean withDriver) throws SQLException {
        VehicleModel vehicle = null;
        try
        {
            String query = "SELECT * FROM Vehicle WHERE RegistrationNumber = '" + registrationNumber + "' LIMIT 1";
            ResultSet rs = Database.getResultFromSqlQuery(query);
            while (rs.next())
            {
                vehicle = resultSetToVehicle(rs, withDriver);
            }

        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return vehicle;
    }

    public static boolean registrationNumberExists(String registrationNumber) throws SQLException {
        boolean exists = false;
        try
        {
            VehicleModel vehicleModel = getVehicleByRegistrationNumber(registrationNumber,false);
            if(vehicleModel != null){
                exists = true;
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return exists;
    }


    //add new vehicle


    //update vehicle
    public static boolean updateDriver(VehicleModel vehicleModel) throws SQLException {
        try {
            String updateQuery = "UPDATE  Vehicle SET "
                    + "RegistrationNumber=?" + ","
                    + "OwnerFirstName=?" + ","
                    + "OwnerLastName=?" + ","
                    + "Color=?" + ","
                    + "Type=?" + ","
                    + "Model=?" + ","
                    + "Make=?" + ","
                    + "DriverID=? " +
                    "WHERE VehicleID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, vehicleModel.getRegistrationNumber());
            preparedStatement.setString(2, vehicleModel.getOwnerFirstName());
            preparedStatement.setString(3, vehicleModel.getOwnerLastName());
            preparedStatement.setString(4, vehicleModel.getColor());
            preparedStatement.setString(5,vehicleModel.getType());
            preparedStatement.setString(6, vehicleModel.getModel());
            preparedStatement.setString(7, vehicleModel.getMake());
            preparedStatement.setInt(8, vehicleModel.getDriverId());
            preparedStatement.setInt(9, vehicleModel.getVehicleId());
            preparedStatement.executeUpdate();
            return true;
        }catch (Exception exception){
            exception.printStackTrace();
            return false;
        }
    }

    //delete a vehicle
    public static boolean deleteVehicleById(int vehicleId) throws SQLException {
        try
        {
            String deleteQuery = "DELETE FROM Vehicle WHERE VehicleID = ?";
            VehicleModel vehicle = getVehicle(vehicleId, false);

            if(vehicle != null)
            {
                PreparedStatement statement = connection.prepareStatement(deleteQuery);
                statement.setLong(1, vehicleId);

                statement.execute();
                return true;
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return false;
    }

    public static VehicleModel resultSetToVehicle(ResultSet rs, boolean withDriver) throws SQLException {
        VehicleModel vehicleModel = new VehicleModel();
        try{

            vehicleModel.setVehicleId(rs.getInt("VehicleID"));
            vehicleModel.setDriverId(rs.getInt("DriverID"));
            vehicleModel.setOwnerFirstName(rs.getString("OwnerFirstName"));
            vehicleModel.setOwnerLastName(rs.getString("OwnerLastName"));
            vehicleModel.setRegistrationNumber(rs.getString("RegistrationNumber"));
            vehicleModel.setColor(rs.getString("Color"));
            vehicleModel.setModel( rs.getString("Model"));
            vehicleModel.setMake(rs.getString("Make"));
            vehicleModel.setType(rs.getString("Type"));

            if(withDriver) {
                DriverModel driver = DriverIO.getDriver(rs.getInt("DriverID"), true);
                vehicleModel.setDriver(driver);
            }
        }catch (Exception exception){
           exception.printStackTrace();
       }
        return vehicleModel;
    }
}
