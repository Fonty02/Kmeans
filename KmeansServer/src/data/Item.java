package data;

import java.io.Serializable;
import java.util.Set;

/**
 * La classe astratta Item rappresenta un generico item di un dataset.
 * Un item è una coppia (attributo, valore).
 */
abstract public class Item implements Serializable {

    /**
     * Attributo dell'item.
     */
    Attribute attribute;

    /**
     * Valore dell'item.
     */
    Object value;

    /**
     * Costruttore della classe Item.
     * @param attribute Attributo dell'item.
     * @param value Valore dell'item.
     */
    Item(Attribute attribute, Object value) {
        this.attribute = attribute;
        this.value = value;
    }

    /**
     * Restituisce il valore dell'item.
     * @return valore dell'item
     */
    Object getValue() {
        return this.value;
    }

    /**
     * Restituisce la stringa rappresentante l'item.
     * @return stringa rappresentante il valore dell'item
     */
    public String toString() {
        return this.value.toString();
    }

    /**
     * Restituisce la distanza tra l'item e l'oggetto passato come parametro.
     * @param a oggetto di cui calcolare la distanza
     * @return distanza tra l'item e l'oggetto passato come parametro
     */
    abstract double distance(Object a);

    /**
     * Modifica il membro value, assegnandogli il valore
     * restituito da data.computePrototype(clusteredData,attribute)
     * @param data dataset
     * @param clusteredData insieme di indici di righe
     */
    public void update(Data data, Set<Integer> clusteredData) {
        this.value = data.computePrototype(clusteredData, this.attribute);
    }
}
