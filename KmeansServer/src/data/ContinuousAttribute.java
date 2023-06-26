package data;


/**
 * Tale classe modella un attributo continuo (numerico) e include i metodi per la “normalizzazione” del dominio dell'attributo nell'intervallo [0,1]
 * al fine di rendere confrontabili attributi aventi domini diversi
 */
class ContinuousAttribute extends Attribute {
    /**
     * Estremo superiore del dominio dell'attributo.
     */
    private final double max;
    /**
     * Estremo inferiore del dominio dell'attributo.
     */
    private final double min;

    /**
     * Costruttore della classe ContinuousAttribute.
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
     * Metodo che restituiscee il valore scalato dell'attributo.
     * @return Valore scalato dell'attributo.
     */
    double getScaledValue(double v) {
        return (v - this.min) / (this.max - this.min);
    }

}
