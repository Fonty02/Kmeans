package data;

import java.io.Serializable;
import java.util.Set;

abstract public class Item implements Serializable {
    Attribute attribute;
    Object value;

    //attributo valore. ES: data.Attribute: PlayTennis(4,index) - yes/no
    Item(Attribute attribute, Object value) {
        this.attribute = attribute;
        this.value = value;
    }

    Object getValue() {
        return this.value;
    }

    public String toString() {
        return this.value.toString();
    }

    abstract double distance(Object a);


    //Modifica il membro value, assegnandogli il valore restituito da data.computePrototype(clusteredData,attribute);
    public void update(Data data, Set<Integer> clusteredData) {
        this.value = data.computePrototype(clusteredData, this.attribute);
    }

}
