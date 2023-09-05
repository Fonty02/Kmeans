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


/**
 * <h2>La classe TableData modella l'insieme di transazioni collezionate in una tabella.</h2>
 * <p>La singola transazione è modellata dalla classe {@link Example}.
 * 
 * La classe permette di ottenere i seguenti tipi di dati:
 * <ul>
 *     <li>Lista di transazioni distinte</li>
 *     <li>Insieme di valori distinti di una colonna</li>
 *     <li>Valore aggregato di una colonna</li>
 * </ul>
 * @see DbAccess
 * @see TableSchema
 * @see Column
 * @see Example
 * @see EmptySetException
 * @see NoValueException
 */
public class TableData {

    /**
     * <h4>Connessione al database.</h4>
     */
    private final DbAccess db;

    /**
     * <h4>Costruttore della classe.</h4>
     *
     * @param db Oggetto {@link DbAccess} che permette la connessione al database.
     * @see DbAccess
     */
    public TableData(DbAccess db) {
        this.db = db;
    }

    /**
     * <h4>Permette di ottenere la lista di transazioni distinte presenti nella tabella specificata.</h4>
     * <p>
     * La lista di transazioni distinte è modellata da una lista di oggetti {@link Example}.
     * </p>
     * @param table Nome della tabella.
     * @return La lista di transazioni distinte.
     * @throws SQLException Se si verifica un errore nell'esecuzione della query.
     * @throws EmptySetException Se il risultato della query è vuoto.
     */
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

    /**
     * <h4>Permette di ottenere l'insieme di valori distinti di una colonna specificata.</h4>
     * <p>
     * L'insieme di valori distinti è modellato da un {@link Set} di oggetti.
     * </p>
     * @param table Nome della tabella.
     * @param column Colonna della tabella.
     * @return L'insieme di valori distinti.
     * @throws SQLException Se si verifica un errore nell'esecuzione della query.
     */
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

    /**
     * <h4>Permette di ottenere il valore aggregato di una colonna specificata.</h4>
     * <p>
     * Il valore aggregato è modellato da un {@link Object}.
     * </p>
     * @param table Nome della tabella.
     * @param column Colonna della tabella.
     * @param aggregate Tipo di aggregazione.
     * @return Il valore aggregato.
     * @throws SQLException se si verifica un errore nell'esecuzione della query.
     * @throws NoValueException se il risultato della query è vuoto.
     * @see QUERY_TYPE
     */
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
