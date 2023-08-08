package data;

import java.io.Serializable;
import java.util.Set;

/**
 * <h2>La classe astratta Item rappresenta un generico item di un dataset.</h2>
 * <p>Un item è una coppia (attributo, valore).</p>
 */
abstract public class Item implements Serializable {

    /**
     * <h4>Attributo dell'item.</h4>
     */
    private Attribute attribute;

    /**
     * <h4>Valore dell'item.</h4>
     */
    private Object value;

    /**
     * <h4>Costruttore dell'item.</h4>
     * @param attribute Attributo dell'item.
     * @param value Valore dell'item.
     */
    Item(Attribute attribute, Object value) {
        this.attribute = attribute;
        this.value = value;
    }

    /**
     * <h4>Restituisce l'attributo dell'item.</h4>
     * @return L'attributo dell'item
     */
    Attribute getAttribute() {
        return this.attribute;
    }

    /**
     * <h4>Restituisce il valore dell'item.</h4>
     * @return Il valore dell'item
     */
    Object getValue() {
        return this.value;
    }

    /**
     * <h4>Restituisce la stringa rappresentante l'item.</h4>
     * @return La stringa rappresentante il valore dell'item
     */
    public String toString() {
        return this.value.toString();
    }

    /**
     * <h4>Restituisce la distanza tra l'item e l'oggetto passato come parametro.</h4>
     * @param a oggetto di cui calcolare la distanza
     * @return La distanza tra l'item e l'oggetto passato come parametro
     */
    abstract double distance(Object a);

    /**
     * <h4>Modifica il membro value, assegnandogli il valore restituito da {@link Data#computePrototype(Set, Attribute)}</h4>
     * @param data dataset
     * @param clusteredData insieme di indici di righe
     */
    public void update(Data data, Set<Integer> clusteredData) {
        this.value = data.computePrototype(clusteredData, this.attribute);
    }
}
