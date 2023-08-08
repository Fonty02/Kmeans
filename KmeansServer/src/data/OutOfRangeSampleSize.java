package data;

/**
 * <h2>La classe OutOfRangeSampleSize gestisce le eccezioni lanciate quando la dimensione del sample è fuori dal range consentito.</h2>
 */
public class OutOfRangeSampleSize extends Exception {

    /**
     * <h4>Costruisce un oggetto <code>OutOfRangeSampleSize</code> con i dettagli specificati nel messaggio.</h4>
     * @param msg messaggio di errore
     */
    public OutOfRangeSampleSize(String msg) {
        super(msg);
    }

}
