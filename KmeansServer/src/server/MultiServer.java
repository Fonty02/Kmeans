package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * MultiServer è la classe che implementa il server multithread.
 * 
 * Il server multithread è un server che può gestire più client contemporaneamente.
 * Viene creato l'oggetto ServerSocket che si mette in ascolto su una specifica porta.
 * Quando viene stabilita una connessione con un client, viene creato un oggetto ServerOneClient che gestisce la comunicazione con quel client.
 * @see ServerOneClient
 */
class MultiServer {
    /**
     * Porta su cui il server è in ascolto
     */
    private final int PORT;

    /**
     * Costruttore del server multithread
     * 
     * Viene inizializzato il valore della porta e viene chiamato il metodo run().
     * @param port porta su cui il server è in ascolto
     */

    private MultiServer(int port) {
        PORT = port;
        run();
    }

    /**
     * run è il metodo che crea il ServerSocket e si mette in ascolto su una specifica porta.
     * 
     * Quando viene stabilita una connessione con un client, viene creato un oggetto ServerOneClient che gestisce la comunicazione con quel client.
     * In caso di errore nella creazione del ServerSocket, viene stampato un messaggio di errore e il programma termina.
     * In caso di errore nell'accept del ServerSocket, viene stampato un messaggio di errore, liberata la porta e il programma termina.
     * @see ServerOneClient
     * @see IOException
     */
    private void run() {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(PORT);
            while (true) {
                Socket socket;
                try {
                    socket = serverSocket.accept();
                    new ServerOneClient(socket);
                } catch (IOException e) {
                    e.printStackTrace();
                    serverSocket.close();
                    System.err.println(e + "\nLiberazione porta " + PORT + " riuscita");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Porta occupata");
            System.exit(1);
        }
    }

    /**
     * Main del server multithread
     * Viene settato il file di log e viene creato un oggetto MultiServer.
     * In caso di errore nell'accesso al file di log, viene stampato un messaggio di errore e il programma termina.
     * @param args argomenti passati da linea di comando
     */
    public static void main(String[] args) {
        try {
            System.setErr(new PrintStream(new BufferedOutputStream(new FileOutputStream("Logs/log.txt")), true));
        } catch (IOException e) {
            System.err.println("Impossibile accedere al file di log");
            e.printStackTrace();
            System.err.println();
            return;
        }
        new MultiServer(8080);
    }

}
