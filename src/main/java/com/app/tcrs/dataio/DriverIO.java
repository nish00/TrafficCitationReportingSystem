package com.app.tcrs.dataio;

import com.app.tcrs.database.Database;
import com.app.tcrs.model.DriverModel;
import com.app.tcrs.model.LicenseModel;

import java.sql.*;
import java.util.ArrayList;

public class DriverIO {
    private final static Connection connection = Database.getConnection();

    public static int createDriver(DriverModel driverModel) {
        try {
            String insertQuery = "INSERT INTO Driver("
                    + "FirstName" + ","
                    + "LastName" + ","
                    + "DateOfBirth" + ","
                    + "Age" + ","
                    + "Address1" + ","
                    + "Address2" + ","
                    + "City" + ","
                    + "Province"
                    + ") values(?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, driverModel.getFirstName());
            preparedStatement.setString(2, driverModel.getLastName());
            preparedStatement.setDate(3, java.sql.Date.valueOf(driverModel.getDateOfBirth()));
            preparedStatement.setInt(4, driverModel.getAge());
            preparedStatement.setString(5, driverModel.getAddress1());
            preparedStatement.setString(6, driverModel.getAddress2());
            preparedStatement.setString(7, driverModel.getCity());
            preparedStatement.setString(8, driverModel.getProvince());

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

    //get drivers from drivers data file
    public static ArrayList<DriverModel> getDrivers( boolean withLicense) throws SQLException {
        ArrayList<DriverModel> drivers = new ArrayList<>();
        try {

            ResultSet rs = Database.getResultFromSqlQuery("SELECT * FROM Driver ORDER BY CreatedAt DESC");
            if (rs != null) {
                while (rs.next()) {
                        drivers.add(resultSetToDriver(rs, withLicense));
                }
            } else {
                System.out.println("its null");
            }
            return drivers;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static DriverModel getDriver(int driverId, boolean withLicense) throws SQLException {
        DriverModel driver = null;
        try {
            String query = "SELECT * FROM Driver WHERE DriverID = " + driverId + " LIMIT 1";
            ResultSet rs = Database.getResultFromSqlQuery(query);
            if (rs.next()) {
                    driver = resultSetToDriver(rs, withLicense);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return driver;
    }

    public static DriverModel getDriverByLicenceNumber(String licenseNumber, boolean withLicense) throws SQLException {
        DriverModel driver = null;
        try {
           LicenseModel licenseModel = LicenseIO.getLicenseByLicenseNumber(licenseNumber, true);
           if(licenseModel !=null){
               driver = licenseModel.getDriver();
           }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return driver;
    }

    //update driver
    public static boolean updateDriver(DriverModel driverModel) throws SQLException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Driver SET " +
                    "FirstName=?," +
                    "LastName=?," +
                    "DateOfBirth=?," +
                    "Age=?," +
                    "Address1=?," +
                    "Address2=?," +
                    "City=?," +
                    "Province=?, " +
                    "LicenseID=? " +
                    "WHERE DriverID=?");
            preparedStatement.setString(1, driverModel.getFirstName());
            preparedStatement.setString(2, driverModel.getLastName());
            preparedStatement.setDate(3, java.sql.Date.valueOf(driverModel.getDateOfBirth()));
            preparedStatement.setInt(4, driverModel.getAge());
            preparedStatement.setString(5, driverModel.getAddress1());
            preparedStatement.setString(6, driverModel.getAddress2());
            preparedStatement.setString(7, driverModel.getCity());
            preparedStatement.setString(8, driverModel.getProvince());
            preparedStatement.setInt(9, driverModel.getLicenseId());
            preparedStatement.setInt(10, driverModel.getDriverId());
            preparedStatement.executeUpdate();
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    //delete a driver
    public static boolean deleteDriverById(int driverId) throws SQLException {
        try {
            String deleteQuery = "DELETE FROM Driver WHERE DriverID = ?";
            DriverModel driver = getDriver(driverId, false);

            if (driver != null) {
                PreparedStatement statement = connection.prepareStatement(deleteQuery);
                statement.setLong(1, driverId);

                statement.execute();
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static DriverModel resultSetToDriver(ResultSet rs) throws SQLException {

        DriverModel driverModel = new DriverModel();

        try {
            driverModel.setDriverId(rs.getInt("DriverID"));
            driverModel.setFirstName(rs.getString("FirstName"));
            driverModel.setLastName(rs.getString("LastName"));
            driverModel.setDateOfBirth(rs.getDate("DateOfBirth").toLocalDate());
            driverModel.setAge(rs.getInt("Age"));
            driverModel.setAddress1(rs.getString("Address1"));
            driverModel.setAddress2(rs.getString("Address2"));
            driverModel.setCity(rs.getString("City"));
            driverModel.setProvince(rs.getString("Province"));
            driverModel.setDriverId(rs.getInt("LicenseID"));

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return driverModel;
    }

    public static DriverModel resultSetToDriver(ResultSet rs, boolean withLicense) throws SQLException {
        DriverModel driverModel = resultSetToDriver(rs);
        LicenseModel license = LicenseIO.getLicense(rs.getInt("LicenseID"), withLicense );
        driverModel.setLicense(license);
        return driverModel;
    }
}
