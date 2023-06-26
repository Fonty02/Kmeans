package data;

import java.io.Serializable;
import java.util.Set;

/**
 * Classe che rappresenta una tupla del dataset.
 * Una tupla è una sequenza di coppie (attributo, valore).
 */
public class Tuple implements Serializable {

    /**
     * Array di oggetti di tipo Item.
     * @see Item
     */
    private Item[] tuple;

    /**
     * Costruttore della classe.
     * @param size numero di item che costituirà la tupla
     */
    Tuple(int size) {
        tuple = new Item[size];
    }

    /**
     * Restituisce la lunghezza della tupla.
     * @return lunghezza della tupla
     */
    public int getLength() {
        return tuple.length;
    }

    /**
     * Restituisce l'item di indice i della tupla.
     * @param i indice dell'item
     * @return item di indice i della tupla
     */
    public Item get(int i) {
        return tuple[i];
    }

    /**
     * Aggiunge l'item c all'array tuple nella posizione i.
     * @param c item da aggiungere
     * @param i indice dell'array tuple in cui aggiungere l'item c
     */
    void add(Item c, int i) {
        tuple[i] = c;
    }

    /**
     * Determina la distanza tra la tupla riferita da obj e la
     * tupla corrente.. La distanza è ottenuta come la somma delle
     * distanze tra gli item in posizioni eguali nelle due tuple.
     * @param obj tupla da confrontare con la tupla corrente
     * @return distanza tra la tupla corrente e la tupla riferita da obj
     */
    public double getDistance(Tuple obj) {
        double distance = 0;
        for (int i = 0; i < tuple.length; i++)
            distance += tuple[i].distance(obj.get(i).getValue());
        return distance;
    }

    /**
     * Restituisce la media delle distanze tra la tupla
     * corrente e quelle ottenibili dalle righe della matrice in data aventi indice in
     * clusteredData.
     * @param data dataset
     * @param clusteredData insieme di indici di righe della matrice data
     * @return media delle distanze tra la tupla corrente e quelle ottenibili dalle righe della matrice in data aventi indice in clusteredData
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
