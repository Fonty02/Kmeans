package data;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * <h2>La classe DiscreteAttribute che rappresenta un attributo discreto (stringa).</h2>
 * <p>
 * Estende la classe astratta {@link Attribute} e implementa l'interfaccia {@link Iterable} per poter iterare sui valori del dominio dell'attributo.
 * </p>
 */
class DiscreteAttribute extends Attribute implements Iterable<String> {

    /**
     * <h4>Insieme ordinato dei valori del dominio dell'attributo.</h4>
     */
    private final TreeSet<String> values;

    /**
     * <h4>Costruttore dell'attributo discreto.</h4>
     *
     * @param name   nome dell'attributo
     * @param index  indice dell'attributo
     * @param values array di stringhe che rappresentano i valori del dominio dell'attributo
     */
    DiscreteAttribute(String name, int index, String[] values) {
        super(name, index);
        this.values = new TreeSet<>();
        Collections.addAll(this.values, values);
    }

    /**
     * <h4>Restituisce l'iteratore per iterare sui valori del dominio dell'attributo.</h4>
     *
     * @return L'iteratore per iterare sui valori del dominio dell'attributo
     */
    public Iterator<String> iterator() {
        return this.values.iterator();
    }

    /**
     * <h4>Restituisce il numero di volte che il valore specificato compare nel dataset.</h4>
     * @param data dataset
     * @param idList l'insieme degli indici di riga
     * @param v valore
     * @return Il numero di volte che il valore specificato compare nel dataset
     */
    int frequency(Data data, Set<Integer> idList, String v) {
        int count = 0;
        for (int i : idList) {
            if (data.getAttributeValue(i, this.getIndex()).equals(v)) count++;
        }
        return count;
    }

}
