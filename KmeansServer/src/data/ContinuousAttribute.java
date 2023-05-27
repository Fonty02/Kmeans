package data;

//Tale classe modella un attributo continuo (numerico) e include i metodi per la  “normalizzazione” del dominio dellattributo nellintervallo [0,1]
//al fine da rendere confrontabili attributi aventi domini diversi
class ContinuousAttribute extends Attribute {
    private final double max;
    private final double min;

    ContinuousAttribute(String name, int index, double min, double max) {
        super(name, index);
        this.min = min;
        this.max = max;
    }

    double getScaledValue(double v) {
        return (v - this.min) / (this.max - this.min);
    }

}
