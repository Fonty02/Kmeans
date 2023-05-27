package data;

class DiscreteItem extends Item {
    DiscreteItem(DiscreteAttribute attribute, String value) {
        super(attribute, value);
    }

    //distanza 1 se oggetti diversi (stringhe nel caso), 0 se uguali
    double distance(Object a) {
        return getValue().equals(a) ? 0 : 1;
    }

}
