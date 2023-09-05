package database;

import java.util.ArrayList;
import java.util.List;

/**
 * <h2>La classe Example rappresenta una transazione del database.</h2>
 * <p>Una transazione viene rappresentata mediante una lista di oggetti.</p>
 */
public class Example implements Comparable<Example> {

    /**
     * <h4>Costruttore della classe.</h4>
     */
    Example() {}

    /**
     * <h4>Lista di oggetti che rappresentano una transazione.</h4>
     */
    private final List<Object> example = new ArrayList<>();

    /**
     * <h4>Aggiunge un oggetto alla transazione.</h4>
     * @param o Oggetto da aggiungere alla transazione.
     */
    void add(Object o) {
        example.add(o);
    }

    /**
     * <h4>Restituisce l'oggetto nella posizione specificata.</h4>
     * @param i Posizione dell'oggetto da restituire.
     * @return L'oggetto in posizione i.
     */
    public Object get(int i) {
        return example.get(i);
    }

    /**
     * <h4>Confronta due transazioni.</h4>
     * <p>Due transizioni sono uguali se hanno gli stessi oggetti nella stessa posizione.</p>
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
     * <h4>Restituisce la stringa che rappresenta la transazione.</h4>
     * @return La stringa che rappresenta la transazione.
     */
    public String toString() {
        String str = "";
        for (Object o : example)
            str += o.toString() + " ";
        return str;
    }

}