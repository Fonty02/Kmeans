package database;

import database.TableSchema.Column;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class TableData {

    private final DbAccess db;


    public TableData(DbAccess db) {
        this.db = db;
    }

    public List<Example> getDistinctTransazioni(String table) throws SQLException, EmptySetException {
        TableSchema table_schema = new TableSchema(db, table);
        List<Example> list = new ArrayList<>();
        Connection con = db.getConnection();
        Statement statement = con.createStatement();
        String query = "SELECT DISTINCT * FROM " + table + ";";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            Example example = new Example();
            for (int i = 0; i < table_schema.getNumberOfAttributes(); i++) {
                if (!table_schema.getColumn(i).isNumber())
                    example.add(resultSet.getString(table_schema.getColumn(i).getColumnName()));
                else
                    example.add(resultSet.getDouble(table_schema.getColumn(i).getColumnName()));
            }
            list.add(example);
        }
        resultSet.close();
        statement.close();
        if (list.isEmpty())
            throw new EmptySetException("RESULT SET VUOTO");
        return list;
    }


    public Set<Object> getDistinctColumnValues(String table, Column column) throws SQLException {
        Set<Object> values = new TreeSet<>();
        Connection con = db.getConnection();
        Statement statement = con.createStatement();
        String query = "SELECT DISTINCT " + column.getColumnName() + " FROM " + table + ";";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            if (column.isNumber())
                values.add(resultSet.getDouble(column.getColumnName()));
            else values.add(resultSet.getString(column.getColumnName()));
        }
        resultSet.close();
        statement.close();
        return values;
    }

    public Object getAggregateColumnValue(String table, Column column, QUERY_TYPE aggregate) throws SQLException, NoValueException {
        Connection con = db.getConnection();
        Statement statement = con.createStatement();
        Object o = null;
        String query = "SELECT " + aggregate + "(" + column.getColumnName() + ") AS " + column.getColumnName() + " FROM " + table + ";";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            if (column.isNumber())
                o = resultSet.getDouble(column.getColumnName());
            else
                o = resultSet.getString(column.getColumnName());
        }
        resultSet.close();
        statement.close();
        if (o == null)
            throw new NoValueException("NESSUN VALORE DISPONIBILE");
        return o;
    }


}
