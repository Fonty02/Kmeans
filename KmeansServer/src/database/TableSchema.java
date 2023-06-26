package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * La classe TableSchema permette di ottenere lo schema di una tabella del database relazionale.
 */
public class TableSchema {

    /**
     * Riferimento al database.
     */
    private DbAccess db;

    /**
     * La classe Column rappresenta una colonna della tabella.
     * Una colonna viene rappresentata mediante il nome e il tipo di dato.
     */
    public class Column {

        /**
         * Nome della colonna.
         */
        private final String name;
        /**
         * Tipo di dato della colonna.
         */
        private final String type;

        /**
         * Costruttore della classe Column.
         * @param name Nome della colonna.
         * @param type Tipo di dato della colonna.
         */
        private Column(String name, String type) {
            this.name = name;
            this.type = type;
        }

        /**
         * Restituisce il nome della colonna.
         * @return Nome della colonna.
         */
        public String getColumnName() {
            return name;
        }

        /**
         * Restituisce il tipo di dato della colonna.
         * @return Tipo di dato della colonna.
         */
        public boolean isNumber() {
            return type.equals("number");
        }

        /**
         * Restituisce la stringa che rappresenta la colonna.
         * @return Stringa che rappresenta la colonna.
         */
        public String toString() {
            return name + ":" + type;
        }

    }

    /**
     * Lista delle colonne della tabella.
     */
    private List<Column> tableSchema = new ArrayList<>();

    /**
     * Costruttore della classe TableSchema.
     * @param db Riferimento al database.
     * @param tableName Nome della tabella.
     * @throws SQLException Eccezione lanciata in caso di errore nell'esecuzione della query.
     */
    public TableSchema(DbAccess db, String tableName) throws SQLException {
        this.db = db;
        HashMap<String, String> mapSQL_JAVATypes = new HashMap<>();
        mapSQL_JAVATypes.put("CHAR", "string");
        mapSQL_JAVATypes.put("VARCHAR", "string");
        mapSQL_JAVATypes.put("LONGVARCHAR", "string");
        mapSQL_JAVATypes.put("BIT", "string");
        mapSQL_JAVATypes.put("SHORT", "number");
        mapSQL_JAVATypes.put("INT", "number");
        mapSQL_JAVATypes.put("LONG", "number");
        mapSQL_JAVATypes.put("FLOAT", "number");
        mapSQL_JAVATypes.put("DOUBLE", "number");

        Connection con = db.getConnection();
        DatabaseMetaData meta = con.getMetaData();
        ResultSet res = meta.getColumns(con.getCatalog(), null, tableName, null);

        while (res.next()) {
            if (mapSQL_JAVATypes.containsKey(res.getString("TYPE_NAME")))
                tableSchema.add(new Column(
                        res.getString("COLUMN_NAME"),
                        mapSQL_JAVATypes.get(res.getString("TYPE_NAME")))
                );
        }
        res.close();
    }

    /**
     * Restituisce il numero di colonne della tabella.
     * @return Numero di colonne della tabella.
     */
    public int getNumberOfAttributes() {
        return tableSchema.size();
    }

    /**
     * Restituisce la colonna della tabella all'indice specificato.
     * @param index Indice della colonna.
     * @return Colonna della tabella all'indice specificato.
     */
    public Column getColumn(int index) {
        return tableSchema.get(index);
    }

}
