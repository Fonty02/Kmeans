package database;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe Example rappresenta una transazione del database.
 * Una transazione viene rappresentata mediante una lista di oggetti.
 */
public class Example implements Comparable<Example> {

    /**
     * Lista di oggetti che rappresentano una transazione.
     */
    private final List<Object> example = new ArrayList<>();

    /**
     * Aggiunge un oggetto alla transazione.
     * @param o Oggetto da aggiungere alla transazione.
     */
    void add(Object o) {
        example.add(o);
    }

    /**
     * Restituisce l'oggetto nella posizione specificata.
     * @param i Posizione dell'oggetto da restituire.
     * @return Oggetto in posizione i.
     */
    public Object get(int i) {
        return example.get(i);
    }

    /**
     * compareTo confronta due transazioni.
     * Due transizioni sono uguali se hanno gli stessi oggetti nella stessa posizione.
     * @param ex la transazione da confrontare.
     * @return 0 se le transizioni sono uguali, un altro intero se sono diverse.
     */
    public int compareTo(Example ex) {
        int i = 0;
        for (Object o : ex.example) {
            if (!o.equals(this.example.get(i))) return ((Comparable) o).compareTo(example.get(i));
            i++;
        }
        return 0;
    }

    /**
     * Restituisce la stringa che rappresenta la transazione.
     * @return Stringa che rappresenta la transazione.
     */
    public String toString() {
        String str = "";
        for (Object o : example)
            str += o.toString() + " ";
        return str;
    }

}