package Server;

import java.io.IOException;
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
                Socket socket = null;
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
        MultiServer multiServer = new MultiServer(8080);
    }

}
