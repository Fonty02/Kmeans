package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbAccess {
    private final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private final String DBMS = "jdbc:mysql";
    private final String SERVER;
    private final String DATABASE;
    private final String USER_ID;
    private final String PASSWORD;
    private final int PORT;
    private Connection conn;

    public DbAccess(String server, int port, String database, String user_id, String password) {
        SERVER = server;
        PORT = port;
        DATABASE = database;
        USER_ID = user_id;
        PASSWORD = password;
    }

    public void initConnection() throws DatabaseConnectionException {
        try {
            Class.forName(DRIVER_CLASS_NAME);
            conn = DriverManager.getConnection(DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE
                    + "?user=" + USER_ID + "&password=" + PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new DatabaseConnectionException("ERRORE DI DRIVER NEL SERVER");
        } catch (SQLException e) {
            String message = "";
            switch (e.getErrorCode()) {
                case 0 -> message = "SERVER NON ESISTENTE";
                case 1044 -> message = "DATABASE NON ESISTENTE";
                case 1045 -> message = "USER e/o PASSWORD ERRATI";
                default -> message = "Errore di comunicazione";
            }
            throw new DatabaseConnectionException(message);
        }
    }

    Connection getConnection() {
        return conn;
    }

    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
