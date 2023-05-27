package Server;

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

class ServerOneClient extends Thread {
    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private KMeansMiner kmeans;

     ServerOneClient(Socket socket) throws IOException {
        this.socket = socket;
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        start();
    }

    private void closeConnection() {
        try {
            String user = socket.getInetAddress().toString();
            socket.close();
            System.out.println("Comunicazione chiusa con " + user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
                this.closeConnection();
                return;
            }
            switch (choice) {
                case 0 -> // Carica Dati da zero
                {
                    String result = "OK";
                    try {
                        server = (String) in.readObject();
                        portaDatabase = (int) in.readObject();
                        db = (String) in.readObject();
                        tablename = (String) in.readObject();
                        user = (String) in.readObject();
                        pass = (String) in.readObject();
                    } catch (IOException | ClassNotFoundException  | ClassCastException e ) {
                        System.err.println("Errore nella comunicazione");
                        e.printStackTrace();
                        this.closeConnection();
                    }
                    try {
                        data = new Data(server, portaDatabase, db, user, pass, tablename);
                    } catch (NoValueException | DatabaseConnectionException | EmptySetException e) {
                        result = "SI E' VERIFICATO UN ERRORE DURANTE L'INTERROGAZIONE AL DATABASE->" + e.getMessage();
                    } catch (SQLException e) {
                        String error = "";
                        if (e.getErrorCode() == 1146) error += "Tabella non esistente";
                        result = "SI E' VERIFICATO UN ERRORE DURANTE L'INTERROGAZIONE AL DATABASE->" + error;
                    }
                    try {
                        out.writeObject(result);
                    } catch (IOException e) {
                        System.err.println("Errore nella comunicazione");
                        e.printStackTrace();
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
                        this.closeConnection();
                        return;
                    }
                    try {
                        kmeans = new KMeansMiner(k);
                        numIter = kmeans.kmeans(data);
                    } catch (OutOfRangeSampleSize e) {
                        result = "ERRORE NEL NUMERO DEI CLUSTER->" + e.getMessage();
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
                        return;
                    }
                }
                case 2 -> {
                    String result = "OK";
                    filename =  "Salvataggi\\"+db + tablename + k + ".dat";
                    try {
                        kmeans.salva(filename);
                    } catch (IOException e) {
                        result = "Impossibile effettuare salvataggio su file";
                    }
                    try {
                        out.writeObject(result);
                    } catch (IOException e) {
                        System.err.println("Errore nella comunicazione");
                        e.printStackTrace();
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
                        this.closeConnection();
                        return;
                    }
                    filename = "Salvataggi\\" + db + tablename + numIter + ".dat";
                    try {
                        kmeans = new KMeansMiner(filename);
                    } catch (IOException | ClassNotFoundException e) {
                        result = "Impossibile caricare il salvataggio";
                    }
                    try {
                        out.writeObject(result);
                        if (result.equals("OK")) {
                            out.writeObject(kmeans.getC().toString());
                        }
                    } catch (IOException e) {
                        System.err.println("Errore nella comunicazione");
                        e.printStackTrace();

                    }
                }
            }
        }
    }
}


