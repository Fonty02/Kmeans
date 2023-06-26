package data;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Classe che rappresenta un attributo discreto (stringa).
 * 
 * Estende la classe astratta {@link Attribute} e implementa l'interfaccia {@link Iterable} per poter iterare sui valori del dominio dell'attributo.
 */
class DiscreteAttribute extends Attribute implements Iterable<String> {

    /**
     * Insieme ordinato dei valori del dominio dell'attributo.
     */
    private final TreeSet<String> values;

    /**
     * Costruttore della classe.
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
     * Restituisce l'iteratore per iterare sui valori del dominio dell'attributo.
     *
     * @return iteratore per iterare sui valori del dominio dell'attributo
     */
    public Iterator<String> iterator() {
        return this.values.iterator();
    }

    /**
     * Restituisce il numero di volte che il valore v compare nel dataset.
     * @param data dataset
     * @param idList l'insieme degli indici di riga
     * @param v valore
     * @return numero di volte che il valore v compare nel dataset
     */
    int frequency(Data data, Set<Integer> idList, String v) {
        int count = 0;
        for (int i : idList) {
            if (data.getAttributeValue(i, this.getIndex()).equals(v)) count++;
        }
        return count;
    }

}
