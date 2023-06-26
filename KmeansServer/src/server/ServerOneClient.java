package server;

import data.Data;
import data.OutOfRangeSampleSize;
import database.DatabaseConnectionException;
import database.EmptySetException;
import database.NoValueException;
import mining.KMeansMiner;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;

/**
 * ServerOneClient è la classe che gestisce la comunicazione con un client.
 * <p>
 * La classe estende {@link Thread} e effettua overriding del metodo {@link Thread#run()}.
 * La classe riceve una richiesta dal client e la esegue, inviando il risultato al client.
 * Le richieste che il server può ricevere sono:
 * <ul>
 *     <li>0: Connessione a un database</li>
 *     <li>1: Esegui K-Means</li>
 *     <li>2: Salva K-Means su un file</li>
 *     <li>3: Carica K-Means da un file</li>
 * </ul>
 * @see Thread
 */
class ServerOneClient extends Thread {
    /**
     * Socket per la comunicazione con il client.
     */
    private final Socket socket;
    /**
     * Stream di output per mandare messaggi al client.
     */
    private final ObjectOutputStream out;
    /**
     * Stream di input per ricevere messaggi dal client.
     */
    private final ObjectInputStream in;
    /**
     * Oggetto di tipo {@link KMeansMiner} per effettuare il K-Means.
     */
    private KMeansMiner kmeans;

    /**
     * Costruttore della classe.
     * <p>
     * Il costruttore inizializza gli attributi della classe e avvia il thread.
     *
     * @param socket Socket per la comunicazione con il client.
     * @throws IOException In caso di errore nella comunicazione.
     */
    ServerOneClient(Socket socket) throws IOException {
        this.socket = socket;
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        start();
    }

    /**
     * Chiude la comunicazione con il client.
     * <p>
     * Il metodo chiude la comunicazione con il client e stampa un messaggio di avviso.
     * In caso di errore, stampa un messaggio di errore.
     */
    private void closeConnection() {
        try {
            String user = socket.getInetAddress().toString();
            socket.close();
            System.out.println("Comunicazione chiusa con " + user);
        } catch (IOException e) {
            System.err.println("Errore nella chiusura della comunicazione");
            e.printStackTrace();
            System.err.println();
        }
    }

    /**
     * Il metodo run esegue il thread.
     * <p>
     * Il metodo esegue il thread, ricevendo una richiesta dal client con relativi parametri e inviando un messaggio di conferma.
     * Se la conferma è OK, esegue la richiesta e invia il risultato al client.
     * Se la conferma è negativa viene mandato al client il messaggio relativo al problema verificatosi.
     * In caso di errore nella comunicazione, stampa un messaggio di errore e chiude la comunicazione.
     * Se il client chiude la comunicazione, stampa un messaggio di avviso e chiude la comunicazione.
     */
    public void run() {
        String tablename = null, server = null, db = null, user = null, pass = null;
        Data data = null;
        int k = 0, numIter = -1, portaDatabase = 0, choice;
        String filename;
        while (true) {
            try {
                choice = (Integer) in.readObject();
            } catch (IOException | ClassNotFoundException | ClassCastException e) {
                System.err.println("Errore nella comunicazione");
                e.printStackTrace();
                System.err.println();
                this.closeConnection();
                return;
            }
            switch (choice) {
                case 0 -> { // Carica Dati da zero
                    String result = "OK";
                    try {
                        server = (String) in.readObject();
                        portaDatabase = (int) in.readObject();
                        db = (String) in.readObject();
                        tablename = (String) in.readObject();
                        user = (String) in.readObject();
                        pass = (String) in.readObject();
                    } catch (IOException | ClassNotFoundException | ClassCastException e) {
                        System.err.println("Errore nella comunicazione");
                        e.printStackTrace();
                        System.err.println();
                        this.closeConnection();
                    }
                    try {
                        data = new Data(server, portaDatabase, db, user, pass, tablename);
                    } catch (NoValueException | DatabaseConnectionException | EmptySetException | SQLException e) {
                        result = "SI E' VERIFICATO UN ERRORE DURANTE L'INTERROGAZIONE AL DATABASE -> " + e.getMessage();
                        e.printStackTrace();
                        System.err.println();
                    }
                    try {
                        out.writeObject(result);
                    } catch (IOException e) {
                        System.err.println("Errore nella comunicazione");
                        e.printStackTrace();
                        System.err.println();
                        return;
                    }

                }
                case 1 -> {
                    String result = "OK";
                    try {
                        k = (int) in.readObject();
                    } catch (IOException | ClassNotFoundException | ClassCastException e) {
                        System.err.println("Errore nella comunicazione");
                        e.printStackTrace();
                        System.err.println();
                        this.closeConnection();
                        return;
                    }
                    try {
                        kmeans = new KMeansMiner(k);
                        numIter = kmeans.kmeans(data);
                    } catch (OutOfRangeSampleSize e) {
                        result = "ERRORE NEL NUMERO DEI CLUSTER -> " + e.getMessage();
                        e.printStackTrace();
                        System.err.println();
                    }
                    try {
                        out.writeObject(result);
                        if (result.equals("OK")) {
                            out.writeObject("Numero di iterazioni: " + numIter);
                            out.writeObject(kmeans.getC().toString(data));
                        }
                    } catch (IOException e) {
                        System.err.println("Errore nella comunicazione");
                        e.printStackTrace();
                        System.err.println();
                        return;
                    }
                }
                case 2 -> {
                    String result = "OK";
                    filename = "Salvataggi\\" + db + tablename + k + ".dat";
                    try {
                        kmeans.salva(filename);
                    } catch (IOException e) {
                        result = "Impossibile effettuare salvataggio su file";
                        e.printStackTrace();
                        System.err.println();
                    }
                    try {
                        out.writeObject(result);
                    } catch (IOException e) {
                        System.err.println("Errore nella comunicazione");
                        e.printStackTrace();
                        System.err.println();
                        return;
                    }
                }
                case 3 -> {
                    String result = "OK";
                    try {
                        db = (String) in.readObject();
                        tablename = (String) in.readObject();
                        numIter = (Integer) in.readObject();
                    } catch (IOException | ClassNotFoundException | NullPointerException | ClassCastException e) {
                        System.err.println("Errore nella comunicazione");
                        e.printStackTrace();
                        System.err.println();
                        this.closeConnection();
                        return;
                    }
                    filename = "Salvataggi\\" + db + tablename + numIter + ".dat";
                    try {
                        kmeans = new KMeansMiner(filename);
                    } catch (IOException | ClassNotFoundException e) {
                        result = "Impossibile caricare il salvataggio";
                        e.printStackTrace();
                        System.err.println();
                    }
                    try {
                        out.writeObject(result);
                        if (result.equals("OK")) {
                            out.writeObject(kmeans.getC().toString());
                        }
                    } catch (IOException e) {
                        System.err.println("Errore nella comunicazione");
                        e.printStackTrace();
                        System.err.println();
                    }
                }
            }
        }
    }

}
