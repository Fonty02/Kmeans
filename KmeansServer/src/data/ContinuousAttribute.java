package data;

/**
 * <h2>La classe ContinuousAttribute modella un attributo numerico e include i metodi per la “normalizzazione” del dominio dell'attributo nell'intervallo [0, 1]
 * al fine di rendere confrontabili attributi aventi domini diversi.</h2>
 */
class ContinuousAttribute extends Attribute {
    /**
     * <h4>Estremo superiore del dominio dell'attributo.</h4>
     */
    private final double max;
    /**
     * <h4>Estremo inferiore del dominio dell'attributo.</h4>
     */
    private final double min;

    /**
     * <h4>Costruttore dell'attributo numerico.</h4>
     * @param name Nome simbolico dell'attributo.
     * @param index Identificativo numerico dell'attributo all'interno del dataset.
     * @param min Estremo inferiore del dominio dell'attributo.
     * @param max Estremo superiore del dominio dell'attributo.
     */
    ContinuousAttribute(String name, int index, double min, double max) {
        super(name, index);
        this.min = min;
        this.max = max;
    }

    /**
     * <h4>Restituisce il valore scalato dell'attributo nell'intervallo [0, 1]</h4>
     * @param v Valore dell'attributo.
     * @return Il valore scalato dell'attributo.
     */
    double getScaledValue(double v) {
        return (v - this.min) / (this.max - this.min);
    }

}
