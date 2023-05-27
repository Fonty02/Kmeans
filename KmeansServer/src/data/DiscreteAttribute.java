package data;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

class DiscreteAttribute extends Attribute implements Iterable<String> {
    private final TreeSet<String> values; //possibili valori del dominio dellattributo. ES: per playTennis abbiamo yes o no

    DiscreteAttribute(String name, int index, String[] values) {
        super(name, index);
        this.values = new TreeSet<String>();
        Collections.addAll(this.values, values);
    }

    public Iterator<String> iterator() {
        return this.values.iterator();
    }

    //restituisce quante volte v compare nel dataset
    int frequency(Data data, Set<Integer> idList, String v) {
        int count = 0;
        for (int i : idList) {
            if (data.getAttributeValue(i, this.getIndex()).equals(v))
                count++;
        }
        return count;
    }

}
