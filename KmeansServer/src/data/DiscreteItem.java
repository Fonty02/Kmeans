package data;

/**
 * <h2>La classe DiscreteItem rappresenta un item discreto di un dataset.</h2>
 * <p>Un item discreto è una coppia (attributo discreto, valore discreto).</p>
 */
class DiscreteItem extends Item {

    /**
     * <h4>Costruttore dell'item discreto.</h4>
     * @param attribute Attributo discreto dell'item.
     * @param value Valore discreto dell'item.
     */
    DiscreteItem(DiscreteAttribute attribute, String value) {
        super(attribute, value);
    }

    /**
     * <h4>Restituisce la distanza tra l'item e l'oggetto passato come parametro.</h4>
     * @param a oggetto di cui calcolare la distanza
     * @return La distanza tra l'item e l'oggetto passato come parametro
     */
    double distance(Object a) {
        return getValue().equals(a) ? 0 : 1;
    }

}
