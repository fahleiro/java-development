package DAO;




import com.fahleiro.db.DBTools;

import java.sql.Connection;
import java.sql.SQLException;

public class Connections {

    private static Connection mySQLConnection;
    private static Connection oracleConnection;
    private static Connection sqlServerConnection;

    public synchronized static Connection getMySQLConnection() throws SQLException, ClassNotFoundException {
        mySQLConnection = DBTools.openMySQLConnection("", 3306, "", "", "");
        return mySQLConnection;
    }

    public synchronized static Connection getOracleConnection() throws SQLException, ClassNotFoundException {
        oracleConnection = DBTools.openOracleConnection("", 1521, "", "", "");
        return oracleConnection;
    }

    public synchronized static Connection getSQLServerConnection() throws SQLException, ClassNotFoundException {
        sqlServerConnection = DBTools.openSQLServerConnection("", "", "", "", true, true);
        return sqlServerConnection;
    }
}
