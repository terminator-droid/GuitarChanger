package com.dudev.jdbc.starter;

import com.dudev.jdbc.starter.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcRunner {
    public static void main(String[] args) throws SQLException {
        int id = 3;
        List<Integer> values = getValues(id);
        System.out.println(values);

        checkMetaData();
    }

    private static void checkMetaData() throws SQLException {
        try (Connection connection = ConnectionManager.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet catalogs = metaData.getCatalogs();
            while (catalogs.next()) {
                String catalog = catalogs.getString(1);
                System.out.println(catalog);
                ResultSet schema = metaData.getSchemas(catalog, "%");

            }
        }
    }

    private static List<Integer> getValues(int id) throws SQLException {
        var sql = """
                INSERT INTO jdbc_tester (description)
                VALUES (%s, 'FUCK')
                """.formatted(id);
        var deleteRowSql = """
                DELETE FROM jdbc_tester
                WHERE id = 
                """ + id;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try  {
            connection = ConnectionManager.getConnection();
            connection.setAutoCommit(false);

            statement = connection.createStatement();
            statement.addBatch(sql);
            statement.addBatch(deleteRowSql);

            int[] ints = statement.executeBatch();
            connection.commit();
    } catch (Exception e) {
            connection.rollback();
        }
        finally {
            if (connection != null) {
                connection.close();
//            }
//            boolean statementSelect = false;
//            if (statementSelect != null) {
//                statementSelect.close();
//            }
        }
        List<Integer> result = new ArrayList<>();
        if (resultSet != null) {
            while (resultSet.next()) {
                result.add(resultSet.getObject("id", Integer.class));
            }
        }
        return result;
    }}
}
