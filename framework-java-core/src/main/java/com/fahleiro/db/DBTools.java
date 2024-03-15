package com.fahleiro.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBTools {

    public static Connection openSQLServerConnection(String host, String database, String username, String password, boolean encrypt, boolean trustServerCertificate) throws ClassNotFoundException, SQLException {
        Connection sqlServerConnection = null;
        System.out.println("Connecting to the database.");
        try {
            Class.forName ("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://" + host + ";databaseName=" + database;

            if (encrypt) {
                url += ";encrypt=true";
            }

            if (trustServerCertificate) {
                url += ";trustServerCertificate=true";
            }

            sqlServerConnection = DriverManager.getConnection (url, username, password);

        } catch (ClassNotFoundException ex) {
            System.out.println("Database driver not found: " + ex.getMessage());
        }catch (SQLException ex) {
            System.out.println("An error occurred while trying to access the database: " + ex.getMessage());
        }
        return sqlServerConnection;
    }

    public static Connection openOracleConnection(String host, int port, String serviceName, String username, String password) throws ClassNotFoundException, SQLException {
        Connection oracleConnection = null;
        System.out.println("Connecting to the database.");
        try {
            Class.forName ("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@//" + host + ":" + port + "/" + serviceName;

            oracleConnection = DriverManager.getConnection (url, username, password);

        } catch (ClassNotFoundException ex) {
            System.out.println("Database driver not found: " + ex.getMessage());
        }catch (SQLException ex) {
            System.out.println("An error occurred while trying to access the database: " + ex.getMessage());
        }
        return oracleConnection;
    }

    public static Connection openMySQLConnection(String host, int port, String database, String username, String password) throws ClassNotFoundException, SQLException {
        Connection mysqlConnection = null;
        System.out.println("Connecting to the database.");
        try {
            Class.forName ("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://" + host + ":" + port + "/" + database;

            mysqlConnection = DriverManager.getConnection (url, username, password);

        } catch (ClassNotFoundException ex) {
            System.out.println("Database driver not found: " + ex.getMessage());
        }catch (SQLException ex) {
            System.out.println("An error occurred while trying to access the database: " + ex.getMessage());
        }
        return mysqlConnection;
    }

}
