package data;

import java.io.Serializable;
import java.util.Set;

public class Tuple implements Serializable {
    private Item[] tuple; //è una tupla del database, quindi un insieme di Attributi (nome e index) e valore

    Tuple(int size) {
        tuple = new Item[size];
    }

    public int getLength() {
        return tuple.length;
    }

    public Item get(int i) {
        return tuple[i];
    }

    void add(Item c, int i) {
        tuple[i] = c;
    }

    //distanza tra la tupla passata come parametro e la tupla corrente che richiama il metodo
    public double getDistance(Tuple obj) {
        double distance = 0;
        for (int i = 0; i < tuple.length; i++)
            distance += tuple[i].distance(obj.get(i).getValue());
        return distance;
    }

    // media delle distanze tra la tupla corrente e tutte le tuple ottenibili dalle righe del dataset data aventi indice di righe in clusteredData
    public double avgDistance(Data data, Set<Integer> clusteredData) {
        double p, sumD = 0.0;
        for (int i : clusteredData) {
            double d = getDistance(data.getItemSet(i));
            sumD += d;
        }
        p = sumD / clusteredData.size();
        return p;
    }

}
