package com.app.tcrs.dataio;

import com.app.tcrs.database.Database;
import com.app.tcrs.model.DrivingSchoolModel;

import java.sql.*;
import java.util.ArrayList;

public class DrivingSchoolIO{
    private final static Connection connection = Database.getConnection();

    public static int createDrivingSchool(DrivingSchoolModel drivingSchoolModel) {
        try {
            String insertQuery = "INSERT INTO DrivingSchool("
                    + "CitationID" + ","
                    + "DrivingClass" + ","
                    + "StartDate" + ","
                    + "EndDate"
                    + ") values(?,?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, drivingSchoolModel.getCitationId());
            preparedStatement.setString(2, drivingSchoolModel.getDrivingClass());
            preparedStatement.setDate(3, java.sql.Date.valueOf(drivingSchoolModel.getStartDate()));
            preparedStatement.setDate(4, java.sql.Date.valueOf(drivingSchoolModel.getEndDate()));


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

    //get drivingSchools from drivingSchools data file
    public static ArrayList<DrivingSchoolModel> getDrivingSchools( ) throws SQLException {
        ArrayList<DrivingSchoolModel> drivingSchools = new ArrayList<>();
        try {

            ResultSet rs = Database.getResultFromSqlQuery("SELECT * FROM DrivingSchool ORDER BY CreatedAt DESC");
            if (rs != null) {
                while (rs.next()) {
                    drivingSchools.add(resultSetToDrivingSchool(rs));
                }
            } else {
                System.out.println("its null");
            }
            return drivingSchools;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static DrivingSchoolModel getDrivingSchoolById(int drivingSchoolId) throws SQLException {
        DrivingSchoolModel drivingSchool = null;
        try {
            String query = "SELECT * FROM DrivingSchool WHERE RegistrationID = " + drivingSchoolId + " LIMIT 1";
            ResultSet rs = Database.getResultFromSqlQuery(query);
            if (rs.next()) {
                drivingSchool = resultSetToDrivingSchool(rs);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return drivingSchool;
    }

    public static DrivingSchoolModel getDrivingSchoolByCitationId(int citationId) throws SQLException {
        DrivingSchoolModel drivingSchool = null;
        try {
            String query = "SELECT * FROM DrivingSchool WHERE CitationID = " + citationId + " LIMIT 1";
            ResultSet rs = Database.getResultFromSqlQuery(query);
            if (rs.next()) {
                drivingSchool = resultSetToDrivingSchool(rs);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return drivingSchool;
    }

    //update drivingSchool
    public static boolean updateDrivingSchool(DrivingSchoolModel drivingSchoolModel) throws SQLException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE DrivingSchool SET "
                            + "CitationID=?" + ","
                            + "DrivingClass=?" + ","
                            + "StartDate=?" + ","
                            + "EndDate=?"+
                    "WHERE RegistrationID=?");
            preparedStatement.setInt(1, drivingSchoolModel.getCitationId());
            preparedStatement.setString(2, drivingSchoolModel.getDrivingClass());
            preparedStatement.setDate(3, java.sql.Date.valueOf(drivingSchoolModel.getStartDate()));
            preparedStatement.setDate(4, java.sql.Date.valueOf(drivingSchoolModel.getEndDate()));
            preparedStatement.setInt(5, drivingSchoolModel.getRegistrationId());
            preparedStatement.executeUpdate();
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    //delete a drivingSchool
    public static boolean deleteDrivingSchoolById(int drivingSchoolId) throws SQLException {
        try {
            String deleteQuery = "DELETE FROM DrivingSchool WHERE DrivingSchoolID = ?";
            DrivingSchoolModel drivingSchool = getDrivingSchoolById(drivingSchoolId);

            if (drivingSchool != null) {
                PreparedStatement statement = connection.prepareStatement(deleteQuery);
                statement.setLong(1, drivingSchoolId);

                statement.execute();
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static DrivingSchoolModel resultSetToDrivingSchool(ResultSet rs) throws SQLException {

        DrivingSchoolModel drivingSchoolModel = new DrivingSchoolModel();

        try {
            drivingSchoolModel.setRegistrationId(rs.getInt("RegistrationID"));
            drivingSchoolModel.setCitationId(rs.getInt("CitationID"));
            drivingSchoolModel.setDrivingClass(rs.getString("DrivingClass"));
            drivingSchoolModel.setStartDate(rs.getDate("StartDate").toLocalDate());
            drivingSchoolModel.setEndDate(rs.getDate("EndDate").toLocalDate());
            drivingSchoolModel.setCitationModel(CitationIO.getCitationById(rs.getInt("CitationModel")));

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return drivingSchoolModel;
    }

}
