package database;

import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * <h2>La classe DbAccess è la classe che gestisce la connessione al database.</h2>
 * @see Connection
 * @see DriverManager
 * @see SQLException
 */
public class DbAccess {

    /**
     * <h4>Nome della classe del driver.</h4>
     */
    private final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    /**
     * <h4>Protocollo e sottoprotocollo di comunicazione.</h4>
     */
    private final String DBMS = "jdbc:mysql";
    /**
     * <h4>Indirizzo del server.</h4>
     */
    private final String SERVER;
    /**
     * <h4>Nome del database.</h4>
     */
    private final String DATABASE;
    /**
     * <h4>Identificativo dell'utente.</h4>
     */
    private final String USER_ID;
    /**
     * <h4>Password dell'utente.</h4>
     */
    private final String PASSWORD;
    /**
     * <h4>Porta di comunicazione.</h4>
     */
    private final int PORT;
    /**
     * <h4>Gestisce la connessione al database.</h4>
     */
    private Connection conn;

    /**
     * <h4>Costruttore della classe.</h4>
     * @param server indirizzo del server
     * @param port porta di comunicazione
     * @param database nome del database
     * @param user_id identificativo dell'utente
     * @param password password dell'utente
     */
    public DbAccess(String server, int port, String database, String user_id, String password) {
        SERVER = server;
        PORT = port;
        DATABASE = database;
        USER_ID = user_id;
        PASSWORD = password;
    }

    /**
     * <h4>Inizializza la connessione al database.</h4>
     * @throws DatabaseConnectionException in caso di problemi relativi alla comunicazione con il database
     */
    public void initConnection() throws DatabaseConnectionException {
        try {
            try {
                (new Socket(SERVER, PORT)).close();
            } catch(Exception e) {
              String message = "SERVER NON ESISTENTE";
              throw new DatabaseConnectionException(message);
            }
            Class.forName(DRIVER_CLASS_NAME);
            conn = DriverManager.getConnection(DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE
                    + "?user=" + USER_ID + "&password=" + PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new DatabaseConnectionException("ERRORE DI DRIVER NEL SERVER");
        } catch (SQLException e) {
            String message;
            switch (e.getErrorCode()) {
                case 0 -> message = "SERVER NON ESISTENTE";
                case 1044 -> message = "DATABASE NON ESISTENTE";
                case 1045 -> message = "USER e/o PASSWORD ERRATI";
                default -> message = "Errore di comunicazione";
            }
            throw new DatabaseConnectionException(message);
        }
    }

    /**
     * <h4>Restituisce l'oggetto {@link Connection} che gestisce la connessione al database.</h4>
     * @return L'oggetto {@link Connection} che gestisce la connessione al database.
     */
    Connection getConnection() {
        return conn;
    }

    /**
     * <h4>Chiude la connessione al database.</h4>
     */
    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

}
