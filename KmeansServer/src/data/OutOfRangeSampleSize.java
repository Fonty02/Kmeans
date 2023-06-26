package data;

/**
 * Eccezione che viene lanciata quando la dimensione del sample è fuori dal range consentito.
 */
public class OutOfRangeSampleSize extends Exception {

    /**
     * Costruttore della classe.
     * @param msg messaggio di errore
     */
    public OutOfRangeSampleSize(String msg) {
        super(msg);
    }

}
