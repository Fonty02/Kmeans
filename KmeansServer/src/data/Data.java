package data;


import database.*;

import java.sql.SQLException;
import java.util.*;

public class Data {
    private final List<Example> data;
    private final int numberOfExamples;
    private final List<Attribute> attributeSet = new LinkedList<>(); //nomi degli attributi del dataset con il rispettivo indice


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
        }catch (SQLException e){
            if(e.getErrorCode()==1146)
                throw new SQLException("Tabella non esistente");
            else
                throw e;
        }
        dbAccess.closeConnection();
    }

    public int getNumberOfExamples() {
        return this.numberOfExamples;
    }

    public int getNumberOfAttributes() {
        return this.attributeSet.size();
    }


    public Object getAttributeValue(int exampleIndex, int attributeIndex) {
        return this.data.get(exampleIndex).get(attributeIndex);
    }


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

    //restutuisce la tupla sullindice di riga index
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


    //restituisce un array di k interi generati casualmente e che non si ripetono
    public int[] sampling(int k) throws OutOfRangeSampleSize {
        if (k <= 0 || k > data.size())
            throw new OutOfRangeSampleSize("Numero di cluster non valido, deve essere compreso tra 1 e " + data.size());
        int[] centroidIndexes = new int[k];
//choose k random different centroids in data.
        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());
        for (int i = 0; i < k; i++) {
            boolean found = false;
            int c;
            do {
                found = false;
                c = rand.nextInt(getNumberOfExamples());
// verify that centroid[c] is not equal to a centroide already stored in CentroidIndexes
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

    private boolean compare(int i, int j) {
        for (Attribute at : attributeSet) {
            if (!data.get(i).get(at.getIndex()).equals(data.get(j).get(at.getIndex())))
                return false;
        }
        return true;
    }

    Object computePrototype(Set<Integer> idList, Attribute attribute) {
        if (attribute instanceof DiscreteAttribute) return computePrototype(idList, (DiscreteAttribute) attribute);
        else return computePrototype(idList, (ContinuousAttribute) attribute);

    }

    //calcolo del centroide (moda -> valore che occorre piu volte per attribute)
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

    private Double computePrototype(Set<Integer> idList, ContinuousAttribute attribute) {
        double sum = 0;
        for (int i : idList)
            sum += (Double) data.get(i).get(attribute.getIndex());
        return sum / idList.size();
    }

}
