package database;

/**
 * EmptySetException è la classe che gestisce le eccezioni lanciate in caso di risultati vuoti da una query.
 * Questa eccezione viene lanciata quando il risultato di una qeury è vuoto.
 * Viene considerato un errore in quanto non è possibile eseguire operazioni su un risultato vuoto.
 * @see Exception
 */
public class EmptySetException extends Exception {

    /**
     * Costruttore dell'eccezione
     * @param msg il messaggio di errore
     */
    EmptySetException(String msg) {
        super(msg);
    }

}
