package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <h2>La classe TableSchema permette di ottenere lo schema di una tabella del database relazionale.</h2>
 */
public class TableSchema {

    /**
     * <h4>Riferimento al database.</h4>
     */
    private DbAccess db;

    /**
     * <h2>La classe Column rappresenta una colonna della tabella.</h2>
     * <p>Una colonna viene rappresentata mediante il nome e il tipo di dato.</p>
     */
    public class Column {

        /**
         * <h4>Nome della colonna.</h4>
         */
        private final String name;
        /**
         * <h4>Tipo di dato della colonna.</h4>
         */
        private final String type;

        /**
         * <h4>Costruttore della classe.</h4>
         * @param name Nome della colonna.
         * @param type Tipo di dato della colonna.
         */
        private Column(String name, String type) {
            this.name = name;
            this.type = type;
        }

        /**
         * <h4>Restituisce il nome della colonna.</h4>
         * @return Il nome della colonna.
         */
        public String getColumnName() {
            return name;
        }

        /**
         * <h4>Restituisce il tipo di dato della colonna.</h4>
         * @return Il tipo di dato della colonna.
         */
        public boolean isNumber() {
            return type.equals("number");
        }

        /**
         * <h4>Restituisce la stringa che rappresenta la colonna.</h4>
         * @return La stringa che rappresenta la colonna.
         */
        public String toString() {
            return name + ":" + type;
        }

    }

    /**
     * <h4>Lista delle colonne della tabella.</h4>
     */
    private List<Column> tableSchema = new ArrayList<>();

    /**
     * <h4>Costruttore della classe</h4>
     * @param db Riferimento al database.
     * @param tableName Nome della tabella.
     * @throws SQLException in caso di errore nell'esecuzione della query.
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
     * <h4>Restituisce il numero di colonne della tabella.</h4>
     * @return Il numero di colonne della tabella.
     */
    public int getNumberOfAttributes() {
        return tableSchema.size();
    }

    /**
     * <h4>Restituisce la colonna della tabella all'indice specificato.</h4>
     * @param index Indice della colonna.
     * @return La colonna della tabella all'indice specificato.
     */
    public Column getColumn(int index) {
        return tableSchema.get(index);
    }

}
