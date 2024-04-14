package DAO.Queries;

import DAO.Connections;
import com.fahleiro.db.QueryTools;
import java.sql.SQLException;

public class Query {
    public static void queryMethod() throws SQLException, ClassNotFoundException {
        QueryTools.executeQuery(Connections.getMySQLConnection(),
                "SELECT * FROM ...",
                resultSet -> {
                    try {
                        String var1 = resultSet.getString("column_1");
                        int var2 = resultSet.getInt("column_2");
                        System.out.println("column_1 : " + var1 + " column_2: " + var2);
                    } catch (Exception  ex) {
                       System.out.println("Unable to execute queryMethod. Error: " + ex.getMessage());
                    }
                }
        );
    }
}
