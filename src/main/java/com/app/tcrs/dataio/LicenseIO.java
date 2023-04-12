package com.app.tcrs.dataio;

import com.app.tcrs.database.Database;
import com.app.tcrs.model.DriverModel;
import com.app.tcrs.model.LicenseModel;

import java.sql.*;
import java.util.ArrayList;

public class LicenseIO {
    private final static Connection connection = Database.getConnection();
    public static int createLicense(LicenseModel licenseModel) {
        try {
            String insertQuery = "INSERT INTO License("
                    + "DriverID" + ","
                    + "LicenseNumber" + ","
                    + "LicenseType" + ","
                    + "LicenseExpiryDate"
                    + ") values(?,?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, licenseModel.getDriverId());
            preparedStatement.setString(2, licenseModel.getLicenseNumber());
            preparedStatement.setString(3, licenseModel.getLicenseType());
            preparedStatement.setDate(4, java.sql.Date.valueOf(licenseModel.getLicenseExpiryDate()));

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();

            if (rs.next()) {
                return rs.getInt(1);
            }

            return 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    //get licenses from licenses data file
    public static ArrayList<LicenseModel> getLicenses( boolean withDriver) throws SQLException {
        ArrayList<LicenseModel> licenses = new ArrayList<>();
        try
        {
            ResultSet rs = Database.getResultFromSqlQuery("SELECT * FROM License ORDER BY CreatedAt");
            while (rs.next()) {
                licenses.add( resultSetToLicense(rs, withDriver));
            }
            return licenses;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static LicenseModel getLicense(int licenseId, boolean withDriver) throws SQLException {
        LicenseModel license = new LicenseModel();
        try
        {
            String query = "SELECT * FROM License WHERE LicenseID = " + licenseId + " LIMIT 1";
            ResultSet rs = Database.getResultFromSqlQuery(query);
            while (rs.next())
            {
                license = resultSetToLicense(rs, withDriver);
            }

        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return license;
    }
    public static LicenseModel getLicenseByLicenseNumber(String licenseNumber, boolean withDriver) throws SQLException {
        LicenseModel license = new LicenseModel();
        try
        {
            String query = "SELECT * FROM License WHERE LicenseNumber = '" + licenseNumber + "' LIMIT 1";
            ResultSet rs = Database.getResultFromSqlQuery(query);
            while (rs.next())
            {
                license = resultSetToLicense(rs, withDriver);
            }

        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return license;
    }

    public static boolean licenseNumberExists(String licenseNumber) throws SQLException {
        boolean exists = false;
        try
        {
            LicenseModel licenseModel = getLicenseByLicenseNumber(licenseNumber, false);
            if(licenseModel != null){
                exists = true;
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return exists;
    }


    //add new license


    //update license
    public static boolean updateLicense(LicenseModel licenseModel) throws SQLException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE License SET "
                    + "DriverID=?" + ","
                    + "LicenseNumber=?" + ","
                    + "LicenseType=?" + ","
                    + "LicenseExpiryDate=?"
                    +" WHERE LicenseID=?");
            preparedStatement.setInt(1, licenseModel.getDriverId());
            preparedStatement.setString(2, licenseModel.getLicenseNumber());
            preparedStatement.setString(3,licenseModel.getLicenseType());
            preparedStatement.setDate(4, java.sql.Date.valueOf(licenseModel.getLicenseExpiryDate()));
            preparedStatement.setInt(5, licenseModel.getLicenseId());
            preparedStatement.executeUpdate();
            return true;
        }catch (Exception exception){
            exception.printStackTrace();
            return false;
        }
    }

    //delete a license
    public static boolean deleteLicenseById(int licenseId) throws SQLException {
        try
        {
            String deleteQuery = "DELETE FROM License WHERE LicenseID = ?";
            LicenseModel license = getLicense(licenseId, false);

            if(license != null)
            {
                PreparedStatement statement = connection.prepareStatement(deleteQuery);
                statement.setLong(1, licenseId);

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

    private static LicenseModel resultSetToLicense(ResultSet rs, boolean withLicense) throws SQLException {
        LicenseModel licenseModel = new LicenseModel();
        try{
            licenseModel.setLicenseId(rs.getInt("LicenseID"));
            licenseModel.setLicenseNumber(rs.getString("LicenseNumber"));
            licenseModel.setLicenseType(rs.getString("LicenseType"));
            licenseModel.setLicenseExpiryDate(rs.getDate("LicenseExpiryDate").toLocalDate());
            licenseModel.setDriverId(rs.getInt("DriverID"));

            if(withLicense) {
                DriverModel driver = DriverIO.getDriver(rs.getInt("DriverID"), false);
                licenseModel.setDriver(driver);
            }
            return licenseModel;
        }catch (Exception exception){
            exception.printStackTrace();
        }

        return  licenseModel;
    }
}
