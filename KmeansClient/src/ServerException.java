/**
 * <h2> La classe ServerException gestisce le eccezioni che possono essere lanciate dal server. </h2>
 * @see Exception
 */
class ServerException extends Exception {
    /**
     * <h4>Costruisce un oggetto <code>ServerException</code> con i dettagli specificati nel messaggio.</h4>
     * @param msg messaggio di errore
     */
    ServerException(String msg)
    {
        super(msg);
    }
}
