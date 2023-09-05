package database;

/**
 * <h2>La classe DatabaseConnectionException gestisce le eccezioni lanciate in caso di problemi
 * relativi alla comunicazione con il database.</h2>
 * @see Exception
 */
public class DatabaseConnectionException extends Exception {

    /**
     * <h4>Costruisce un oggetto <code>DatabaseConnectionException</code> con i dettagli specificati nel messaggio.</h4>
     * @param msg messaggio di errore
     */
    DatabaseConnectionException(String msg) {
        super(msg);
    }

}
