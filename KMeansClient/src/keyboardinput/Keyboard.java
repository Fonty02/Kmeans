package keyboardinput;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * <h2> La classe Keyboard gestisce l'input da tastiera. </h2>
 * <p>
 * In particolare la classe permette di leggere le seguenti tipologie di input
 * <ul>
 *     <li>Stringhe</li>
 *     <li>Interi</li>
 * </ul>
 */
public class Keyboard {

    /**
     * <h4>Permette di leggere l'input da tastiera.</h4>
     */
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	/**
	 * <h4> Costruttore. </h4>
	 */
	private Keyboard() {
	}

    /**
     * <h4> Permette di leggere una stringa da tastiera. </h4>
     * <p>
     * In caso di errore nella lettura dell'input viene restituito <code>null</code> e viene stampato un messaggio di errore.
     *
     * @return La stringa letta da tastiera (o <code>null</code> in caso di errore).
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
     * <h4> Permette di leggere un intero da tastiera. </h4>
     * <p>
     * Nel caso in cui la conversione a intero non avviene con successo viene stampato un messaggio di errore e
     * viene chiesto di reinserire il valore.
     * Nel caso in cui la lettura dell'input da tastiera non avviene con successo viene restituito -1 e viene stampato un messaggio di errore.
     *
     * @return L'intero letto da tastiera (-1 in caso di errore).
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
