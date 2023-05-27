package data;

import java.io.Serializable;

abstract class Attribute implements Serializable {
    private final String name;
    private final int index;

    Attribute(String name, int index) {
        this.name = name;  //nome simbolico
        this.index = index; //identificativo (posizione) numerica all'interno del dataset

        //esempio: playTennis - 4 (nome attributo e posizione nel dataset)
    }

    String getName() {
        return this.name;
    }

    int getIndex() {
        return this.index;
    }

    public String toString() {
        return this.name;
    }

}