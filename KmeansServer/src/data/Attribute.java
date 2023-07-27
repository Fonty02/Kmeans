package data;

import java.io.Serializable;

/**
 * La classe astratta Attribute rappresenta un generico attributo di un dataset.
 */
abstract class Attribute implements Serializable {

    /**
     * Nome simbolico dell'attributo.
     */
    private final String name;

    /**
     * Identificativo numerico dell'attributo all'interno del dataset.
     */
    private final int index;

    /**
     * Costruttore della classe Attribute.
     * @param name Nome simbolico dell'attributo.
     * @param index Identificativo numerico dell'attributo all'interno del dataset.
     */
    Attribute(String name, int index) {
        this.name = name;
        this.index = index;
    }

    /**
     * Metodo che restituisce il nome dell'attributo.
     * @return Nome dell'attributo.
     */
    String getName() {
        return this.name;
    }

    /**
     * Metodo che restituisce l'identificativo numerico dell'attributo all'interno del dataset.
     * @return Identificativo numerico dell'attributo all'interno del dataset.
     */
    int getIndex() {
        return this.index;
    }

    /**
     * Metodo che restituisce la stringa rappresentante l'attributo.
     * @return Nome dell'attributo.
     */
    public String toString() {
        return this.name;
    }

}