/**
 * <h2> ServerException è la classe che gestisce le eccezioni che possono essere lanciate dal server. </h2>
 * @see Exception
 */
class ServerException extends Exception {
    /**
     * <h4> Costruttore della classe ServerException. </h4>
     * @param msg messaggio di errore.
     */
    ServerException(String msg)
    {
        super(msg);
    }
}
