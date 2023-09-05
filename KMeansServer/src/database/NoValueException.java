package database;

/**
 * <h2>La classe NoValueException gestisce le eccezioni lanciate in caso di risultati vuoti da una query di aggregazione.</h2>
 * <p>
 * Questa eccezione viene lanciata quando il risultato di una query di aggregazione è vuoto.
 * Viene considerato un errore in quanto non è possibile eseguire operazioni su un risultato vuoto.
 * </p>
 * @see Exception
 */
public class NoValueException extends Exception {

    /**
     * <h4>Costruisce un oggetto <code>NoValueException</code> con i dettagli specificati nel messaggio.</h4>
     * @param msg messaggio di errore
     */
    NoValueException(String msg) {
        super(msg);
    }

}
