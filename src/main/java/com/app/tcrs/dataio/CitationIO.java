package com.app.tcrs.dataio;

import com.app.tcrs.database.Database;
import com.app.tcrs.model.CitationModel;
import com.mysql.cj.exceptions.CJConnectionFeatureNotAvailableException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class CitationIO {
    private final static Connection connection = Database.getConnection();

    public static int createCitation(CitationModel citationModel) {
        try {
            String insertQuery = "INSERT INTO Citation("
                    + "FirstName" + ","
                    + "LastName" + ","
                    + "DateOfBirth" + ","
                    + "Age" + ","
                    + "Address1" + ","
                    + "Address2" + ","
                    + "City" + ","
                    + "Province" + ","
                    + "TypeOfOffense" + ","
                    + "VehicleRegOrDrivingLicense" + ","
                    + "DateOfOffense" + ","
                    + "FineIssued" + ","
                    + "IssuingOfficer" + ","
                    + "CitationDate" + ","
                    + "Status" + ","
                    + "Remarks" + ","
                    + "LastPaymentDate"
                    + ") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, citationModel.getFirstName());
            preparedStatement.setString(2, citationModel.getLastName());
            preparedStatement.setDate(3, java.sql.Date.valueOf(citationModel.getDateOfBirth()));
            preparedStatement.setInt(4, citationModel.getAge());
            preparedStatement.setString(5, citationModel.getAddress1());
            preparedStatement.setString(6, citationModel.getAddress2());
            preparedStatement.setString(7, citationModel.getCity());
            preparedStatement.setString(8, citationModel.getProvince());
            preparedStatement.setString(9, citationModel.getTypeOfOffense());
            preparedStatement.setString(10, citationModel.getVehicleRegOrDrivingLicense());
            preparedStatement.setDate(11, java.sql.Date.valueOf(citationModel.getDateOfOffense()));
            preparedStatement.setDouble(12, citationModel.getFineIssued());
            preparedStatement.setString(13, citationModel.getIssuingOfficer());
            preparedStatement.setDate(14, java.sql.Date.valueOf( citationModel.getCitationDate()));
            preparedStatement.setString(15, citationModel.getStatus());
            preparedStatement.setString(16, citationModel.getRemarks());
            preparedStatement.setDate(17, java.sql.Date.valueOf(citationModel.getLastPaymentDate()));

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

    //get citations from citations data file
    public static ArrayList<CitationModel> getCitations( ) throws SQLException {
        ArrayList<CitationModel> citations = new ArrayList<>();
        try {

            ResultSet rs = Database.getResultFromSqlQuery("SELECT * FROM Citation ORDER BY CreatedAt DESC");
            if (rs != null) {
                while (rs.next()) {
                        citations.add(resultSetToCitation(rs));
                }
            } else {
                System.out.println("its null");
            }
            return citations;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static CitationModel getCitationById(int citationId) throws SQLException {
        CitationModel citation = new CitationModel();
        try {
            String query = "SELECT * FROM Citation WHERE CitationID = " + citationId + " LIMIT 1";
            ResultSet rs = Database.getResultFromSqlQuery(query);
            if (rs.next()) {
                    citation = resultSetToCitation(rs);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return citation;
    }

    public static CitationModel getCitationByVehicleRegOrLicenceNumber(String vehicleRegOrDrivingLicense) throws SQLException {
        CitationModel citation = null;
        try {
            String query = "SELECT * FROM Citation WHERE VehicleRegOrDrivingLicense = '" + vehicleRegOrDrivingLicense + "' LIMIT 1";
            ResultSet rs = Database.getResultFromSqlQuery(query);
            if (rs.next()) {
                citation = resultSetToCitation(rs);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return citation;
    }

    public static ArrayList<CitationModel> getCitationsByVehicleRegOrLicenceNumber(String vehicleRegOrDrivingLicense) throws SQLException {
        ArrayList<CitationModel> citations = new ArrayList<>();
        try {
            String query = "SELECT * FROM Citation WHERE VehicleRegOrDrivingLicense = '" + vehicleRegOrDrivingLicense + "' ORDER BY CreatedAt DESC";
            ResultSet rs = Database.getResultFromSqlQuery(query);
            while (rs.next()) {
                citations.add(resultSetToCitation(rs));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return citations;
    }

    //update citation
    public static boolean updateCitation(CitationModel citationModel) throws SQLException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Citation SET "+
                    "FirstName=?," +
                            "LastName=?," +
                            "DateOfBirth=?," +
                            "Age=?," +
                            "Address1=?," +
                            "Address2=?," +
                            "City=?," +
                            "Province=?" + ","
                            + "TypeOfOffense=?" + ","
                            + "VehicleRegOrDrivingLicense=?" + ","
                            + "DateOfOffense=?" + ","
                            + "FineIssued=?" + ","
                            + "IssuingOfficer=?" + ","
                            + "CitationDate=?" + ","
                            + "Status=?" + ","
                            + "Remarks=?" + ","
                            + "LastPaymentDate=?"+
                    "WHERE CitationID=?");
            preparedStatement.setString(1, citationModel.getFirstName());
            preparedStatement.setString(2, citationModel.getLastName());
            preparedStatement.setDate(3, java.sql.Date.valueOf(citationModel.getDateOfBirth()));
            preparedStatement.setInt(4, citationModel.getAge());
            preparedStatement.setString(5, citationModel.getAddress1());
            preparedStatement.setString(6, citationModel.getAddress2());
            preparedStatement.setString(7, citationModel.getCity());
            preparedStatement.setString(8, citationModel.getProvince());
            preparedStatement.setString(9, citationModel.getTypeOfOffense());
            preparedStatement.setString(10, citationModel.getVehicleRegOrDrivingLicense());
            preparedStatement.setDate(11, java.sql.Date.valueOf(citationModel.getDateOfOffense()));
            preparedStatement.setDouble(12, citationModel.getFineIssued());
            preparedStatement.setString(13, citationModel.getIssuingOfficer());
            preparedStatement.setDate(14, java.sql.Date.valueOf( citationModel.getCitationDate()));
            preparedStatement.setString(15, citationModel.getStatus());
            preparedStatement.setString(16, citationModel.getRemarks());
            preparedStatement.setDate(17, java.sql.Date.valueOf(citationModel.getLastPaymentDate()));
            preparedStatement.setInt(18, citationModel.getCitationId());
            preparedStatement.executeUpdate();
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    //delete a citation
    public static boolean deleteCitationById(int citationId) throws SQLException {
        try {
            String deleteQuery = "DELETE FROM Citation WHERE CitationID = ?";
            CitationModel citation = getCitationById(citationId);

            if (citation != null) {
                PreparedStatement statement = connection.prepareStatement(deleteQuery);
                statement.setLong(1, citationId);

                statement.execute();
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static CitationModel resultSetToCitation(ResultSet rs) throws SQLException {

        CitationModel citationModel = new CitationModel();

        try {
            citationModel.setCitationId(rs.getInt("CitationID"));
            citationModel.setFirstName(rs.getString("FirstName"));
            citationModel.setLastName(rs.getString("LastName"));
            citationModel.setDateOfBirth(rs.getDate("DateOfBirth").toLocalDate());
            citationModel.setAge(rs.getInt("Age"));
            citationModel.setAddress1(rs.getString("Address1"));
            citationModel.setAddress2(rs.getString("Address2"));
            citationModel.setCity(rs.getString("City"));
            citationModel.setProvince(rs.getString("Province"));
            citationModel.setIssuingOfficer(rs.getString("IssuingOfficer"));
            citationModel.setVehicleRegOrDrivingLicense(rs.getString("VehicleRegOrDrivingLicense"));
            citationModel.setCitationDate(rs.getDate("CitationDate").toLocalDate());
            citationModel.setFineIssued( rs.getDouble("FineIssued"));
            citationModel.setTypeOfOffense(rs.getString("TypeOfOffense"));
            citationModel.setDateOfOffense( rs.getDate("DateOfOffense").toLocalDate());
            citationModel.setLastPaymentDate(rs.getDate("LastPaymentDate").toLocalDate());
            citationModel.setStatus(rs.getString("Status"));
            citationModel.setRemarks(rs.getString("Remarks"));

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return citationModel;
    }

}
