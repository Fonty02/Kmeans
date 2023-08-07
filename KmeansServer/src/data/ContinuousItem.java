package data;

/**
 * La classe ContinuousItem rappresenta un item di tipo continuo di un dataset.
 * Un item continuo è una coppia (attributo continuo, valore continuo).
 */
class ContinuousItem extends Item {

    /**
     * Costruttore della classe ContinuousItem.
     * @param attribute Attributo continuo dell'item.
     * @param value Valore continuo dell'item.
     */
    ContinuousItem(Attribute attribute, Double value) {
        super(attribute, value);
    }

    /**
     * Restituisce la distanza tra l'item e l'oggetto passato come parametro.
     * @param a oggetto di cui calcolare la distanza
     * @return distanza tra l'item e l'oggetto passato come parametro
     */
    double distance(Object a) {
        return Math.abs(((ContinuousAttribute) getAttribute()).getScaledValue((Double) getValue()) - ((ContinuousAttribute) getAttribute()).getScaledValue((Double) a));
    }

}
