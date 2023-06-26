package database;

/**
 * NoValueException è la classe che gestisce le eccezioni lanciate in caso di risultati vuoti da una query di aggregazione.
 * <p>
 * Questa eccezione viene lanciata quando il risultato di una qeury di aggregazione è vuoto.
 * Viene considerato un errore in quanto non è possibile eseguire operazioni su un risultato vuoto.
 * @see Exception
 */
public class NoValueException extends Exception {

    /**
     * Costruttore della classe NoValueException.
     * @param msg messaggio di errore
     */
    NoValueException(String msg) {
        super(msg);
    }

}
