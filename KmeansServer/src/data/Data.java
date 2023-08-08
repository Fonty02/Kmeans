package data;

import database.*;

import java.sql.SQLException;
import java.util.*;

/**
 * <h2>La classe Data rappresenta un dataset.</h2>
 * <p>
 * Contiene un insieme di oggetti {@link Example} rappresentanti le transazioni e un insieme di oggetti {@link Attribute} rappresentanti gli attributi.
 * </p>
 */
public class Data {

    /**
     * <h4>Insieme di oggetti {@link Example} rappresentanti le transazioni.</h4>
     */
    private final List<Example> data;

    /**
     * <h4>Numero di transazioni.</h4>
     */
    private final int numberOfExamples;

    /**
     * <h4>Insieme di oggetti {@link Attribute} rappresentanti gli attributi.</h4>
     */
    private final List<Attribute> attributeSet = new LinkedList<>();


    /**
     * <h4>Costruttore.</h4>
     * <p>
     * Legge i dati dal database e li memorizza.
     * </p>
     * @param server   nome del server
     * @param port     numero della porta
     * @param database nome del database
     * @param user_id  nome utente
     * @param password password
     * @param table    nome della tabella
     * @throws SQLException              in caso di errori nella comunicazione con il database
     * @throws EmptySetException         in caso di insieme vuoto
     * @throws DatabaseConnectionException in caso di errore di connessione al database
     * @throws NoValueException          in caso di valore non presente
     */
    public Data(String server, int port, String database, String user_id, String password, String table) throws SQLException, EmptySetException, DatabaseConnectionException, NoValueException {
        DbAccess dbAccess = new DbAccess(server, port, database, user_id, password);
        dbAccess.initConnection();
        TableData tableData = new TableData(dbAccess);
        try {
            data = tableData.getDistinctTransazioni(table);
            numberOfExamples = data.size();
            TableSchema tableSchema = new TableSchema(dbAccess, table);
            for (int i = 0; i < tableSchema.getNumberOfAttributes(); i++) {
                TableSchema.Column column = tableSchema.getColumn(i);
                if (column.isNumber()) {
                    double min = (Double) tableData.getAggregateColumnValue(table, column, QUERY_TYPE.MIN);
                    double max = (Double) tableData.getAggregateColumnValue(table, column, QUERY_TYPE.MAX);
                    attributeSet.add(new ContinuousAttribute(column.getColumnName(), i, min, max));
                } else {
                    String[] values = new String[tableData.getDistinctColumnValues(table, column).size()];
                    tableData.getDistinctColumnValues(table, column).toArray(values);
                    attributeSet.add(new DiscreteAttribute(column.getColumnName(), i, values));
                }
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == 1146) throw new SQLException("Tabella non esistente");
            else throw e;
        }
        dbAccess.closeConnection();
    }

    /**
     * <h4>Restituisce il numero di transazioni.</h4>
     * @return Il numero di transizioni.
     */
    public int getNumberOfExamples() {
        return this.numberOfExamples;
    }

    /**
     * <h4>Restituisce il numero di attributi.</h4>
     * @return Il numero di attributi.
     */
    public int getNumberOfAttributes() {
        return this.attributeSet.size();
    }
    
    /**
     * <h4>Restituisce l'oggetto specificato dagli indici di riga e colonna.</h4>
     * @param exampleIndex indice di riga
     * @param attributeIndex indice di colonna
     * @return L'oggetto specificato dagli indici di riga e colonna
     */
    public Object getAttributeValue(int exampleIndex, int attributeIndex) {
        return this.data.get(exampleIndex).get(attributeIndex);
    }


    /**
     * <h4>Restituisce la stringa rappresentante il dataset.</h4>
     * @return La stringa rappresentante il dataset
     */
    public String toString() {
        String s = "";
        int i = 0;
        for (Attribute at : attributeSet) {
            s += at.getName() + (i == attributeSet.size() - 1 ? "\n" : ",");
            i++;
        }
        i = 0;
        for (Example ex : data) {
            s += i + ":" + ex.toString() + "\n";
            i++;
        }

        return s;
    }
    

    /**
     * <h4>Restituisce la tupla sull'indice di riga index.</h4>
     * @param index indice di riga
     * @return La tupla sull'indice di riga index
     * @see Tuple
     */
    public Tuple getItemSet(int index) {
        Tuple tuple = new Tuple(attributeSet.size());
        for (Attribute at : attributeSet) {
            if (at instanceof DiscreteAttribute)
                tuple.add(new DiscreteItem((DiscreteAttribute) at, (String) data.get(index).get(at.getIndex())), at.getIndex());
            else
                tuple.add(new ContinuousItem(at, (Double) data.get(index).get(at.getIndex())), at.getIndex());
        }
        return tuple;
    }

    /**
     * <h4>Restituisce un array di k interi distinti generati casualmente.</h4>
     * @param k numero di interi da generare
     * @return L'array di k interi distinti generati casualmente
     * @throws OutOfRangeSampleSize in caso di numero di cluster non valido
     */
    public int[] sampling(int k) throws OutOfRangeSampleSize {
        if (k <= 0 || k > data.size())
            throw new OutOfRangeSampleSize("Numero di cluster non valido, deve essere compreso tra 1 e " + data.size());
        int[] centroidIndexes = new int[k];
        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());
        for (int i = 0; i < k; i++) {
            boolean found;
            int c;
            do {
                found = false;
                c = rand.nextInt(getNumberOfExamples());
                for (int j = 0; j < i; j++)
                    if (compare(centroidIndexes[j], c)) {
                        found = true;
                        break;
                    }
            }
            while (found);
            centroidIndexes[i] = c;
        }
        return centroidIndexes;
    }

    /**
     * <h4>Restituisce <code>true</code> se le righe i e j sono uguali, <code>false</code> altrimenti.</h4>
     * @param i indice di riga
     * @param j indice di riga
     * @return <code>true</code> se le righe i e j sono uguali, <code>false</code> altrimenti
     */
    private boolean compare(int i, int j) {
        for (Attribute at : attributeSet) {
            if (!data.get(i).get(at.getIndex()).equals(data.get(j).get(at.getIndex())))
                return false;
        }
        return true;
    }

    /**
     * <h4>Restituisce il valore del centroide per l'attributo specificato.</h4>
     * @param idList lista degli indici di riga
     * @param attribute attributo
     * @return Il valore del centroide per l'attributo attribute
     */
    Object computePrototype(Set<Integer> idList, Attribute attribute) {
        if (attribute instanceof DiscreteAttribute) return computePrototype(idList, (DiscreteAttribute) attribute);
        else return computePrototype(idList, (ContinuousAttribute) attribute);

    }

    /**
     * <h4>Restituisce il centroide rispetto ad un attributo discreto.</h4>
     * @param idList lista degli indici di riga
     * @param attribute attributo discreto
     * @return Il centroide rispetto all'attributo specificato
     */
    private String computePrototype(Set<Integer> idList, DiscreteAttribute attribute) {
        Iterator<String> it = attribute.iterator();
        String first = it.next();
        int max = attribute.frequency(this, idList, first);
        int tmp;
        String prototype = first;
        String tmp_string;
        while (it.hasNext()) {
            tmp_string = it.next();
            tmp = attribute.frequency(this, idList, tmp_string);
            if (tmp > max) {
                max = tmp;
                prototype = tmp_string;
            }
        }
        return prototype;
    }

    /**
     * <h4>Restituisce il centroide rispetto ad un attributo continuo.</h4>
     * @param idList lista degli indici di riga
     * @param attribute attributo continuo
     * @return Il centroide rispetto all'attributo specificato
     */
    private Double computePrototype(Set<Integer> idList, ContinuousAttribute attribute) {
        double sum = 0;
        for (int i : idList)
            sum += (Double) data.get(i).get(attribute.getIndex());
        return sum / idList.size();
    }

}
