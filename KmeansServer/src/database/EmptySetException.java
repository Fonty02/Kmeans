package database;

/**
 * EmptySetException è la classe che gestisce le eccezioni lanciate in caso di risultati vuoti da una query.
 * <p>
 * Questa eccezione viene lanciata quando il risultato di una qeury è vuoto.
 * Viene considerato un errore in quanto non è possibile eseguire operazioni su un risultato vuoto.
 * @see Exception
 */
public class EmptySetException extends Exception {
    EmptySetException(String msg) {
        super(msg);
    }

}
