package com.app.tcrs.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
    //Creating Connection
    public static Connection connection;

    private static final String DB_HOST = "localhost" ;
    private static final String DB_NAME = "tcrs" ;
    private static final String DB_PORT = "3306" ;
    private static final String DB_USERNAME = "root" ;
    private static final String DB_PASSWORD = "gift" ;

    public Database(){
        getConnection();
    }
    //Creating universal method to open connect will mysql database
    public static Connection getConnection() {
        try {
            //Registering with mysql Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + DB_HOST +":" + DB_PORT + "/" + DB_NAME , DB_USERNAME, DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return (connection);
    }

    //Creating universal method to close connect will mysql database
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    //Creating universal method to query for retrieving information
    public static ResultSet getResultFromSqlQuery(String SqlQueryString) {
        try {
            //Checking whether the connection is null or not null
            if (connection == null) {
                getConnection();
            }
            //Querying the query
            //Creating Result set object
            return connection.createStatement().executeQuery(SqlQueryString);
        } catch (Exception ex) {
            ex.printStackTrace();
            return  null;
        }

    }

    //Creating universal method to query for inserting or updating information in mysql database
    public static int insertUpdateFromSqlQuery(String SqlQueryString) {
        int i = 2;
        try {
            //Checking whether the connection is null or null
            if (connection == null) {
                getConnection();
            }
            //Querying the query
            i = connection.createStatement().executeUpdate(SqlQueryString);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return i;
    }
}
