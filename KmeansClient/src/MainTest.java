import keyboardinput.Keyboard;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;


 class MainTest {

    private final ObjectOutputStream out;
    private final ObjectInputStream in; // stream con richieste del client


    private  MainTest(String ip, int port) throws IOException {
        InetAddress addr = InetAddress.getByName(ip); //ip
        System.out.println("addr = " + addr);
        Socket socket = new Socket(addr, port); //Port
        System.out.println(socket);

        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        // stream con richieste del client
    }

    private int menu() {
        int answer;
        System.out.println("Scegli una opzione");
        do {
            System.out.println("(1) Carica Cluster da File");
            System.out.println("(2) Carica Dati");
            System.out.print("Risposta:");
            answer = Keyboard.readInt();
        }
        while (answer <= 0 || answer > 2);
        return answer;

    }

    private String learningFromFile() throws ServerException, IOException, ClassNotFoundException {
        out.writeObject(3);
        System.out.print("Nome database:");
        String dbName = Keyboard.readString();
        out.writeObject(dbName);
        System.out.print("Nome tabella:");
        String tabName = Keyboard.readString();
        out.writeObject(tabName);
        System.out.print("Numero di cluster:");
        int k = Keyboard.readInt();
        out.writeObject(k);
        String result = (String) in.readObject();
        if (result.equals("OK"))
            return (String) in.readObject();
        else throw new ServerException(result);

    }

    private void storeTableFromDb() throws ServerException, IOException, ClassNotFoundException {
        out.writeObject(0);
        boolean def=false;
        do {
            System.out.print("Vuoi usere dei valori di default per il database? (y/n)");
            String answer = Keyboard.readString();
            if (answer.equals("y")) {
                def=true;
                break;
            } else if (answer.equals("n")) {
                break;
            }
        }
        while (true);
        if (def)
        {
            out.writeObject("localhost");
            out.writeObject(3306);
            out.writeObject("MapDB");
            out.writeObject("playtennis");
            out.writeObject("MapUser");
            out.writeObject("map");
        }
        else {
            System.out.print("Inserisci l'indirizzo ip/DNS del database (localhost / 127.0.0.1 se il database si trova nel server a cui si è connessi):");
            String ip = Keyboard.readString();
            out.writeObject(ip);
            System.out.print("Inserisci la porta del database (3306 se il database si trova nel server a cui si è connessi):");
            int port = Keyboard.readInt();
            out.writeObject(port);
            System.out.print("Inserisci il nome del database:");
            String dbName = Keyboard.readString();
            out.writeObject(dbName);
            System.out.print("Inserisci il nome tabella:");
            String tabName = Keyboard.readString();
            out.writeObject(tabName);
            System.out.print("Inserisci il nome utente del database:");
            String user = Keyboard.readString();
            out.writeObject(user);
            System.out.print("Inserisci la password del database:");
            String pass = Keyboard.readString();
            out.writeObject(pass);
        }
        String result = (String) in.readObject();
        if (!result.equals("OK"))
            throw new ServerException(result);

    }

    private String learningFromDbTable() throws ServerException, IOException, ClassNotFoundException {
        out.writeObject(1);
        System.out.print("Numero di cluster:");
        int k = Keyboard.readInt();
        out.writeObject(k);
        String result = (String) in.readObject();
        if (result.equals("OK")) {
            System.out.println("Clustering output:" + in.readObject());
            return (String) in.readObject();
        } else throw new ServerException(result);


    }

    private void storeClusterInFile() throws ServerException, IOException, ClassNotFoundException {
        out.writeObject(2);
        String result = (String) in.readObject();
        if (!result.equals("OK"))
            throw new ServerException(result);

    }


    public static void main(String[] args) {
        String ip;
        int port;
        try {
            ip = args[0];
            port = Integer.parseInt(args[1]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Errore: inserire ip e porta come argomenti");
            return;
        } catch (NumberFormatException e) {
            System.err.println("Errore: la porta deve essere un numero");
            return;
        }
        MainTest main;
        String answer;
        try {
            main = new MainTest(ip, port);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }
        do {
            int menuAnswer = main.menu();
            switch (menuAnswer) {
                case 1 -> {
                    try {
                        String kmeans = main.learningFromFile();
                        System.out.println(kmeans);
                    } catch (IOException | ClassNotFoundException e) {
                        System.out.println(e.getMessage());
                        return;
                    } catch (ServerException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 2 -> { // learning from db

                    while (true) {
                        try {
                            main.storeTableFromDb();
                            break; //esce fuori dal while
                        } catch (IOException | ClassNotFoundException e) {
                            System.out.println(e.getMessage());
                            return;
                        } catch (ServerException e) {
                            System.out.println(e.getMessage());
                            if (e.getMessage().equals("SI E' VERIFICATO UN ERRORE DURANTE L'INTERROGAZIONE AL DATABASE")) {
                                System.out.println("Premere invio per terminare l'esecuzione");
                                Keyboard.readString();
                                System.exit(0);
                            }
                        }
                    } //end while [viene fuori dal while con un db (in alternativa il programma termina)
                    do {
                        try {
                            String clusterSet = main.learningFromDbTable();
                            System.out.println(clusterSet);

                            main.storeClusterInFile();

                        } catch (ClassNotFoundException | IOException e) {
                            System.out.println(e.getMessage());
                            return;
                        } catch (ServerException e) {
                            System.out.println(e.getMessage());
                        }
                        do {
                            System.out.print("Vuoi ripetere l'esecuzione?(y/n)");
                            answer = Keyboard.readString();
                        }
                        while (!answer.equals("y") && !answer.equals("n"));
                    }
                    while (answer.equals("y"));
                } //fine case 2
                default -> System.out.println("Opzione non valida!");
            }
            do {
                System.out.print("Vuoi scegliere una nuova operazione da menu?(y/n)");
                answer = Keyboard.readString();
            }
            while (!answer.equals("y") && !answer.equals("n"));
        } while (answer.equals("y"));
    }
}



