package database;

/**
 * NoValueException è la classe che gestisce le eccezioni lanciate in caso
 * risultati vuoti da una query.
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
