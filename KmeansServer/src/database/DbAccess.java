package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DbAccess è la classe che gestisce la connessione al database.
 * @see Connection
 * @see DriverManager
 * @see SQLException
 */
public class DbAccess {

    /**
     * DRIVER_CLASS_NAME è il nome della classe del driver.
     */
    private final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    /**
     * DBMS indica protocollo e sottoprotocollo di comunicazione.
     */
    private final String DBMS = "jdbc:mysql";
    /**
     * SERVER indica l'indirizzo del server.
     */
    private final String SERVER;
    /**
     * DATABASE indica il nome del database.
     */
    private final String DATABASE;
    /**
     * USER_ID indica l'identificativo dell'utente.
     */
    private final String USER_ID;
    /**
     * PASSWORD indica la password dell'utente.
     */
    private final String PASSWORD;
    /**
     * PORT indica la porta di comunicazione.
     */
    private final int PORT;
    /**
     * conn è l'oggetto {@link  Connection} che gestisce la connessione al database.
     */
    private Connection conn;

    /**
     * Costruttore della classe DbAccess.
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
     * initConnection è il metodo che inizializza la connessione al database.
     * @throws DatabaseConnectionException in caso di problemi relativi alla comunicazione con il database
     */
    public void initConnection() throws DatabaseConnectionException {
        try {
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
     * getConnection è il metodo che restituisce l'oggetto {@link  Connection} che gestisce la connessione al database.
     * @return <code>conn</code>
     */
    Connection getConnection() {
        return conn;
    }

    /**
     * closeConnection è il metodo che chiude la connessione al database.
     */
    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

}
