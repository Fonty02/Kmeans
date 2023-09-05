package data;

import java.io.Serializable;

/**
 * <h2>La classe astratta Attribute rappresenta un generico attributo di un dataset.</h2>
 */
abstract class Attribute implements Serializable {

    /**
     * <h4>Nome simbolico dell'attributo.</h4>
     */
    private final String name;

    /**
     * <h4>Identificativo numerico dell'attributo all'interno del dataset.</h4>
     */
    private final int index;

    /**
     * <h4>Costruttore dell'attributo.</h4>
     * @param name Nome simbolico dell'attributo.
     * @param index Identificativo numerico dell'attributo all'interno del dataset.
     */
    Attribute(String name, int index) {
        this.name = name;
        this.index = index;
    }

    /**
     * <h4>Restituisce il nome dell'attributo.</h4>
     * @return Il nome dell'attributo.
     */
    String getName() {
        return this.name;
    }

    /**
     * <h4>Restituisce l'identificativo numerico dell'attributo all'interno del dataset.</h4>
     * @return L'identificativo numerico dell'attributo all'interno del dataset.
     */
    int getIndex() {
        return this.index;
    }

    /**
     * <h4>Restituisce la stringa rappresentante l'attributo.</h4>
     * @return La stringa contenente il nome dell'attributo.
     */
    public String toString() {
        return this.name;
    }

}