package com.app.tcrs.dataio;

import com.app.tcrs.database.Database;
import com.app.tcrs.model.RequestModel;

import java.sql.*;
import java.util.ArrayList;

public class RequestIO {
    private final static Connection connection = Database.getConnection();

    public static int createRequest(RequestModel requestModel) {
        try {
            String insertQuery = "INSERT INTO Request("
                    + "RequestedBy" + ","
                    + "VehicleRegOrDrivingLicense" + ","
                    + "RequestType" + ","
                    + "Approved" + ","
                    + "ApprovedBy" + ","
                    + "Comment"
                    + ") values(?,?,?,?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, requestModel.getRequestedBy());
            preparedStatement.setString(2, requestModel.getVehicleRegOrDrivingLicense());
            preparedStatement.setString(3, requestModel.getRequestType());
            preparedStatement.setBoolean(4, false);
            preparedStatement.setString(5, null);
            preparedStatement.setString(6, null);

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

    //get requests from requests data file
    public static ArrayList<RequestModel> getRequests( ) throws SQLException {
        ArrayList<RequestModel> requests = new ArrayList<>();
        try {

            ResultSet rs = Database.getResultFromSqlQuery("SELECT * FROM Request ORDER BY CreatedAt DESC");
            if (rs != null) {
                while (rs.next()) {
                        requests.add(resultSetToRequest(rs));
                }
            } else {
                System.out.println("its null");
            }
            return requests;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static RequestModel getRequestById(int requestId) throws SQLException {
        RequestModel request = null;
        try {
            String query = "SELECT * FROM Request WHERE RequestID = " + requestId + " LIMIT 1";
            ResultSet rs = Database.getResultFromSqlQuery(query);
            if (rs.next()) {
                    request = resultSetToRequest(rs);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return request;
    }

    public static RequestModel getRequestByVehicleRegOrLicenceNumber(String vehicleRegOrDrivingLicense) throws SQLException {
        RequestModel request = null;
        try {
            String query = "SELECT * FROM Request WHERE VehicleRegOrDrivingLicense = '" + vehicleRegOrDrivingLicense + "' LIMIT 1";
            ResultSet rs = Database.getResultFromSqlQuery(query);
            if (rs.next()) {
                request = resultSetToRequest(rs);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return request;
    }

    //update request
    public static boolean updateRequest(RequestModel requestModel) throws SQLException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Request SET "
                            + "RequestedBy=?" + ","
                            + "VehicleRegOrDrivingLicense=?" + ","
                            + "RequestType=?" + ","
                            + "Approved=?" + ","
                            + "ApprovedBy=? " +  ","
                    +"Comment=? " +
                    "WHERE RequestID=?");
            preparedStatement.setString(1, requestModel.getRequestedBy());
            preparedStatement.setString(2, requestModel.getVehicleRegOrDrivingLicense());
            preparedStatement.setString(3, requestModel.getRequestType());
            preparedStatement.setBoolean(4, requestModel.isApproved());
            preparedStatement.setString(5, requestModel.getApprovedBy());
            preparedStatement.setString(6, requestModel.getComment());
            preparedStatement.setInt(7, requestModel.getRequestId());
            preparedStatement.executeUpdate();
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    //delete a request
    public static boolean deleteRequestById(int requestId) throws SQLException {
        try {
            String deleteQuery = "DELETE FROM Request WHERE RequestID = ?";
            RequestModel request = getRequestById(requestId);

            if (request != null) {
                PreparedStatement statement = connection.prepareStatement(deleteQuery);
                statement.setLong(1, requestId);

                statement.execute();
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static RequestModel resultSetToRequest(ResultSet rs) throws SQLException {

        RequestModel requestModel = new RequestModel();

        try {
            requestModel.setRequestId(rs.getInt("RequestID"));
            requestModel.setRequestedBy(rs.getString("RequestedBy"));
            requestModel.setVehicleRegOrDrivingLicense(rs.getString("VehicleRegOrDrivingLicense"));
            requestModel.setRequestType(rs.getString("RequestType"));
            requestModel.setApproved(rs.getBoolean("Approved"));
            requestModel.setApprovedBy(rs.getString("ApprovedBy"));
            requestModel.setComment(rs.getString("Comment"));

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return requestModel;
    }

}
