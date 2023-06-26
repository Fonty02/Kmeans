package database;

/**
 * DatabaseConnectionException è la classe che gestisce le eccezioni lanciate in caso di problemi
 * relativi alla comunicazione con il database.
 * @see Exception
 */
public class DatabaseConnectionException extends Exception {

    /**
     * Costruttore della classe DatabaseConnectionException.
     * @param msg messaggio di errore
     */
    DatabaseConnectionException(String msg) {
        super(msg);
    }

}
