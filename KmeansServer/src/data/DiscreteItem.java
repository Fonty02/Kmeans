package data;

/**
 * La classe DiscreteItem rappresenta un item di tipo discreto di un dataset.
 * Un item discreto è una coppia (attributo discreto, valore discreto).
 */
class DiscreteItem extends Item {

    /**
     * Costruttore della classe DiscreteItem.
     * @param attribute Attributo discreto dell'item.
     * @param value Valore discreto dell'item.
     */
    DiscreteItem(DiscreteAttribute attribute, String value) {
        super(attribute, value);
    }

    /**
     * Restituisce la distanza tra l'item e l'oggetto passato come parametro.
     * @param a oggetto di cui calcolare la distanza
     * @return distanza tra l'item e l'oggetto passato come parametro
     */
    double distance(Object a) {
        return getValue().equals(a) ? 0 : 1;
    }

}
