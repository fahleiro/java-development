package com.fahleiro.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;

public class QueryTools {

    public static void executeQuery(Connection connection, String query, Consumer<ResultSet> resultSetConsumer) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                resultSetConsumer.accept(resultSet);
            }
        } catch (SQLException ex) {
            System.out.println("An error occurred while trying to execute the query: " + ex.getMessage());
        } finally {
            if (connection != null) {
                System.out.println("Closing database connection.");
                connection.close();
            }
        }
    }

}
