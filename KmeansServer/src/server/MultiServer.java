package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

class MultiServer {
    private int PORT = 8080; //porta di default

    private MultiServer(int port) {
        PORT = port;
        run();
    }

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
