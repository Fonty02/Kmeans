package keyboardinput;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Keyboard è la classe che gestisce l'input da tastiera.
 * In particolare la classe permette di leggere le seguenti tipologie di input
 * <ul>
 *     <li>Stringhe</li>
 *     <li>Interi</li>
 * </ul>
 */
public class Keyboard {

	/**
	 * Costruttore privato per evitare che venga istanziata la classe.
	 */
	private Keyboard() {
	}

	/**
     * in è l'oggetto che permette di leggere l'input da tastiera.
     */
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    /**
     * readString è il metodo che permette di leggere una stringa da tastiera.
     * <p>
     * In caso di errore nella lettura dell'input viene restituito <code>null</code> e viene stampato un messaggio di errore.
     *
     * @return la <code>stringa</code> letta da tastiera.
     */
    public static String readString() {
        String str;
        try {
            str = in.readLine();
        } catch (Exception exception) {
            System.err.println("ERRORE NELLA LETTURA DELLA STRINGA");
            str = null;
        }
        return str;
    }

    /**
     * readInt è il metodo che permette di leggere un intero da tastiera.
     * <p>
     * Nel caso in cui la conversione a intero non avviene con successo viene stampato un messaggio di errore e
     * viene chiesto di reinserire il valore.
     * Nel caso in cui la lettura dell'input da tastiera non avviene con successo viene restituito -1 e viene stampato un messaggio di errore.
     *
     * @return l'<code>intero</code> letto da tastiera.
     */

    public static int readInt() {
        int value = 0;
        String str;
        while (true) {
            try {
                str = in.readLine();
                value = Integer.parseInt(str);
                return value;
            } catch (NumberFormatException e) {
                System.out.println("LETTO VALORE NON INTERO, REINSERIRE");
            } catch (Exception exception) {
                System.err.println("Errore nella lettura del numero. Restituito -1 di default");
                return -1;
            }
        }
    }
}
