import keyboardinput.Keyboard;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * <h2> MainTest è la classe che funge da client per il server di clustering. </h2>
 * <p>
 * Il client si connette al server e gli invia delle richieste.
 * In particolare il client può:
 * <ul>
 *     <li>Caricare una tabella da un database</li>
 *     <li>Caricare una tabella da un file</li>
 *     <li>Chiedere al server di eseguire il clustering</li>
 *     <li>Chiedere al server di salvare il clustering su file</li>
 * </ul>
 */
class MainTest {

    /**
     * out è il canale di output verso il server. Viene usato per inviare messaggi al server.
     */
    private final ObjectOutputStream out;
    /**
     * in è il canale di input dal server. Viene usato per ricevere messaggi dal server.
     */
    private final ObjectInputStream in;


    /**
     * <h4> Costruttore di MainTest. </h4>
     * <p>
     * Il costruttore si connette al server e inizializza i canali di input e output.
     * @param ip   l'indirizzo ip/dns del server
     * @param port la porta sulla quale il processo server è in ascolto
     * @throws IOException se si verifica un errore di I/O
     */
    private MainTest(String ip, int port) throws IOException {
        InetAddress addr = InetAddress.getByName(ip);
        System.out.println("addr = " + addr);
        Socket socket = new Socket(addr, port);
        System.out.println(socket);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    /**
     * <h4> Il metodo menu permette di scegliere una opzione tra quelle disponibili. </h4>
     * <p>
     * Il metodo stampa a video le opzioni disponibili e chiede all'utente di inserire un numero.
     * Il metodo controlla che il numero inserito sia valido e in caso contrario richiede all'utente di inserire un nuovo numero.
     * Le opzioni sono:
     * <ul>
     *  <li>Caricare una tabella da un database</li>
     *  <li>Caricare una tabella da un file</li>
     * </ul>
     * @return <code>answer</code>, ovvero la risposta dell'utente
     */
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

    /**
     * <h4> Il metodo learningFromFile permette di caricare un clustering da un file. </h4>
     * <p>
     * Il metodo chiede all'utente di inserire il nome del file da cui caricare il clustering.
     * Il nome del file è costituito da:
     * <ul>
     *     <li>Nome del database</li>
     *     <li>Nome della tabella</li>
     *     <li>Numero di cluster</li>
     * </ul>
     * Il metodo invia al server il nome del file e riceve dal server un messaggio di conferma.
     * Se il messaggio di conferma è "OK" il metodo riceve dal server il clustering e lo stampa a video,
     * altrimenti lancia un'eccezione di tipo ServerException.
     *
     * @return <code>result</code>, ovvero il clustering caricato dal file
     * @throws ServerException        se il server invia un messaggio di errore
     * @throws IOException            se si verifica un errore di I/O
     * @throws ClassNotFoundException se si verifica un errore di classe
     */
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

    /**
     * <h4> Il metodo storeTableFromDb permette di inviare al server la richiesta per la connessione al database. </h4>
     * <p>
     * Il metodo chiede all'utente di inserire i dati necessari per connettersi al database. L'utente può utilizzare
     * dei valori di default oppure scegliere di inserire i dati manualmente.
     * I dati sono:
     * <ul>
     *     <li>Indirizzo IPv4/DNS del database</li>
     *     <li>Porta del Database</li>
     *     <li>Nome del Database</li>
     *     <li>Nome della Tabella</li>
     *     <li>Nome utente con il quale accedere al database</li>
     *     <li>Password per accedere al database</li>
     * </ul>
     * Il metodo invia al server i dati inseriti e riceve dal server un messaggio di conferma.
     * Se il messaggio di conferma non è "OK" il metodo lancia un'eccezione di tipo ServerException.
     * @throws ServerException        se il server invia un messaggio di errore
     * @throws IOException            se si verifica un errore di I/O
     * @throws ClassNotFoundException se si verifica un errore di classe
     */
    private void storeTableFromDb() throws ServerException, IOException, ClassNotFoundException {
        out.writeObject(0);
        boolean def = false;
        do {
            System.out.print("Vuoi usare dei valori di default per il database? (y/n)");
            String answer = Keyboard.readString();
            if (answer.equals("y")) {
                def = true;
                break;
            } else if (answer.equals("n")) {
                break;
            }
        }
        while (true);
        if (def) {
            out.writeObject("localhost");
            out.writeObject(3306);
            out.writeObject("MapDB");
            out.writeObject("playtennis");
            out.writeObject("MapUser");
            out.writeObject("map");
        } else {
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

    /**
     * <h4> Il metodo learningFromDbTable permette di richiedere al server di creare un clustering. </h4>
     * <p>
     * Il cluster viene creato sulla tabella scelta in <code>storeTableFromDb</code>.
     * Il metodo chiede all'utente di inserire il numero di cluster da creare e il server risponde con un messaggio di conferma.
     * Se il messaggio di conferma è "OK" allora viene stampato a video il numero di cluster creati e viene restituito il clustering.
     * Se il messaggio di conferma non è "OK" il metodo lancia un'eccezione di tipo ServerException.
     *
     * @return <code>result</code>, ovvero il clustering caricato dal database
     * @throws ServerException        se il server invia un messaggio di errore
     * @throws IOException            se si verifica un errore di I/O
     * @throws ClassNotFoundException se si verifica un errore di classe
     */
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

    /**
     * <h4> Il metodo storeClusterInFile permette di richiedere al server di salvare il clustering su file. </h4>
     * <p>
     * Il metodo chiede al server di salvare il clustering appena creato su file e il server risponde con un messaggio di conferma.
     * Se il messaggio di conferma non è "OK" il metodo lancia un'eccezione di tipo ServerException.
     *
     * @throws ServerException        se il server invia un messaggio di errore
     * @throws IOException            se si verifica un errore di I/O
     * @throws ClassNotFoundException se si verifica un errore di classe
     */
    private void storeClusterInFile() throws ServerException, IOException, ClassNotFoundException {
        out.writeObject(2);
        String result = (String) in.readObject();
        if (!result.equals("OK"))
            throw new ServerException(result);
    }

    /**
     * <h4> Il metodo main permette di avviare il client. </h4>
     * <p>
     * Il metodo prende in input da linea di comando l'indirizzo ip e la porta del server a cui connettersi.
     * Viene creato un oggetto di tipo MainTest e viene gestita l'interazione con l'utente tramite il metodo menu
     * e la comunicazione con il server tramite i metodi:
     *  <ul>
     *      <li><code>storeTableFromDb</code></li>
     *      <li><code>learningFromDbTable</code></li>
     *      <li><code>storeClusterInFile</code></li>
     *      <li><code>learningFromFile</code></li>
     * </ul>
     * Il metodo termina quando l'utente sceglie di uscire dal programma o quando si verifica un errore che non può essere gestito.
     *
     * @param args indirizzo ip/dns e porta del server a cui connettersi
     * @see #storeTableFromDb()
     * @see #learningFromDbTable()
     * @see #storeClusterInFile()
     * @see #learningFromFile()
     */
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
                case 2 -> {

                    while (true) {
                        try {
                            main.storeTableFromDb();
                            break;
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
                    }
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
                }
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



