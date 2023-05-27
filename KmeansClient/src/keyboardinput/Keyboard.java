package keyboardinput;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Keyboard {
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	// -----------------------------------------------------------------
	// Returns a string read from standard input.
	// -----------------------------------------------------------------
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

	public static int readInt() {
		int value = 0;
		String str;
		while(true) {
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
