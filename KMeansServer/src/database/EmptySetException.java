package database;

/**
 * <h2>La classe EmptySetException gestisce le eccezioni lanciate in caso di risultati vuoti da una query. </h2>
 * <p>Questa eccezione viene lanciata quando il risultato di una query è vuoto.
 * Viene considerato un errore in quanto non è possibile eseguire operazioni su un risultato vuoto.</p>
 * @see Exception
 */
public class EmptySetException extends Exception {

    /**
     * <h4>Costruisce un oggetto <code>EmptySetException</code> con i dettagli specificati nel messaggio.</h4>
     * @param msg messaggio di errore
     */
    EmptySetException(String msg) {
        super(msg);
    }

}
