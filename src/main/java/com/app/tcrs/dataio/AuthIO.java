package com.app.tcrs.dataio;

import com.app.tcrs.database.Database;
import com.app.tcrs.model.DriverModel;
import com.app.tcrs.model.LawAgent;
import com.app.tcrs.model.LicenseModel;
import com.app.tcrs.model.ProvisionalAgent;

import java.sql.*;
import java.util.ArrayList;

public class AuthIO {
    private final static Connection connection = Database.getConnection();

    public static int createLawAgent(LawAgent agent) {
        try {
            String insertQuery = "INSERT INTO LawAgent("
                    + "FirstName" + ","
                    + "LastName" + ","
                    + "UniqueKey" + ","
                    + "BadgeNumber"
                    + ") values(?,?,md5(?),?)";

            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, agent.getFirstName());
            preparedStatement.setString(2, agent.getLastName());
            preparedStatement.setString(3, agent.getUniqueKey());
            preparedStatement.setString(4, agent.getBadgeNumber());

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

    public static int createProvincialAgent(ProvisionalAgent agent) {
        try {
            String insertQuery = "INSERT INTO ProvincialAgent("
                    + "FirstName" + ","
                    + "LastName" + ","
                    + "Username" + ","
                    + "Password"
                    + ") values(?,?,md5(?),?)";

            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, agent.getFirstName());
            preparedStatement.setString(2, agent.getLastName());
            preparedStatement.setString(3, agent.getUsername());
            preparedStatement.setString(4, agent.getPassword());

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


    public static LawAgent loginAsLawEnforcementAgent( String badgeNo, String uniqueKey) throws SQLException {
        LawAgent lawAgent = new LawAgent();
        try {

            ResultSet rs = Database.getResultFromSqlQuery("SELECT * FROM LawAgent WHERE BadgeNumber = " +
             "'" + badgeNo + "' AND UniqueKey = md5('" + uniqueKey +"') LIMIT 1" );
            if (rs.next()) {
                lawAgent.setAgentId( rs.getInt("AgentID"));
                lawAgent.setFirstName( rs.getString("FirstName"));
                lawAgent.setLastName( rs.getString("LastName"));
                lawAgent.setBadgeNumber( rs.getString("BadgeNumber"));
            } else {
                System.out.println("law agent is  null");
            }
            return lawAgent;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ProvisionalAgent loginAsProvincialAgent( String username, String password) throws SQLException {
        ProvisionalAgent provisionalAgent = new ProvisionalAgent();
        try {

            String query = "SELECT * FROM ProvincialAgent WHERE Username = " +
            "'" + username + "' AND Password = md5('" + password +"') LIMIT 1";

            ResultSet rs = Database.getResultFromSqlQuery(query);

            if (rs.next()) {
                provisionalAgent.setAgentId( rs.getInt("AgentID"));
                provisionalAgent.setFirstName( rs.getString("FirstName"));
                provisionalAgent.setLastName( rs.getString("LastName"));
                provisionalAgent.setUsername(rs.getString("Username"));
            }else{
                System.out.println("provincial agent is  null");
            }
            return provisionalAgent;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
