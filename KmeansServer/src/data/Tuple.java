package data;

import java.io.Serializable;
import java.util.Set;

/**
 * <h2>La classe Tuple che rappresenta una tupla del dataset.</h2>
 * <p>
 * Una tupla è una sequenza di coppie (attributo, valore).
 * </p>
 */
public class Tuple implements Serializable {

    /**
     * <h4>Oggetti di tipo Item.</h4>
     * @see Item
     */
    private Item[] tuple;

    /**
     * <h4>Costruttore della tupla.</h4>
     * @param size numero di item che costituirà la tupla
     */
    Tuple(int size) {
        tuple = new Item[size];
    }

    /**
     * <h4>Restituisce la lunghezza della tupla.</h4>
     * @return La lunghezza della tupla
     */
    public int getLength() {
        return tuple.length;
    }

    /**
     * <h4>Restituisce l'item di indice i della tupla.</h4>
     * @param i indice dell'item
     * @return L'item di indice i della tupla
     */
    public Item get(int i) {
        return tuple[i];
    }

    /**
     * <h4>Aggiunge l'item nella posizione specificata. </h4>
     * @param c item da aggiungere
     * @param i indice dell'array tuple in cui aggiungere l'item c
     */
    void add(Item c, int i) {
        tuple[i] = c;
    }

    /**
     * <h4> Determina la distanza tra la tupla riferita da obj e la tupla corrente. </h4>
     * <p>La distanza è ottenuta come la somma delle
     * distanze tra gli item in posizioni eguali nelle due tuple. </p>
     * @param obj tupla da confrontare con la tupla corrente
     * @return La distanza tra la tupla corrente e la tupla riferita da obj
     */
    public double getDistance(Tuple obj) {
        double distance = 0;
        for (int i = 0; i < tuple.length; i++)
            distance += tuple[i].distance(obj.get(i).getValue());
        return distance;
    }

    /**
     * <h4> Restituisce la media delle distanze tra la tupla corrente e quelle ottenibili dalle righe della matrice in data aventi indice in
     * clusteredData.</h4>
     * @param data dataset
     * @param clusteredData insieme di indici di righe della matrice data
     * @return La media delle distanze tra la tupla corrente e quelle ottenibili dalle righe della matrice in data aventi indice in clusteredData
     */
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
