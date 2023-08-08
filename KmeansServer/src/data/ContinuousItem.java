package data;

/**
 * <h2>La classe ContinuousItem rappresenta un item di tipo continuo di un dataset.</h2>
 * <p>Un item continuo è una coppia (attributo continuo, valore continuo).</p>
 */
class ContinuousItem extends Item {

    /**
     * <h4>Costruttore dell'item continuo.</h4>
     * @param attribute Attributo continuo dell'item.
     * @param value Valore continuo dell'item.
     */
    ContinuousItem(Attribute attribute, Double value) {
        super(attribute, value);
    }

    /**
     * <h4>Restituisce la distanza tra l'item e l'oggetto passato come parametro.</h4>
     * @param a oggetto di cui calcolare la distanza
     * @return La distanza tra l'item e l'oggetto passato come parametro
     */
    double distance(Object a) {
        return Math.abs(((ContinuousAttribute) getAttribute()).getScaledValue((Double) getValue()) - ((ContinuousAttribute) getAttribute()).getScaledValue((Double) a));
    }

}
