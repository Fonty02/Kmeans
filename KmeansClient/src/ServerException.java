/**
 * ServerException è la classe che gestisce le eccezioni che possono essere lanciate dal server.
 * @see Exception
 */
class ServerException extends Exception{
    /**
     * Costruttore della classe ServerException.
     * @param msg messaggio di errore.
     */
    ServerException(String msg)
    {
        super(msg);
    }
}
