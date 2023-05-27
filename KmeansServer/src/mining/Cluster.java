package mining;

import data.Data;
import data.Tuple;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

class Cluster implements Serializable {
    private final Tuple centroid;

    private final Set<Integer> clusteredData;

    Cluster(Tuple centroid) {
        this.centroid = centroid;
        clusteredData = new HashSet<>();
    }

    Tuple getCentroid() {
        return centroid;
    }

    //determina il nuovo centroide
    //dato che lavoriamo con le stringhe è la "tupla" con i valori che si ripetono di piu
    void computeCentroid(Data data) {
        for (int i = 0; i < centroid.getLength(); i++) {
            centroid.get(i).update(data, clusteredData);
        }
    }

    //return true if the tuple is changing cluster
    boolean addData(int id) {
        return clusteredData.add(id);
    }

    //verifica se una transazione è clusterizzata nell'array corrente
    boolean contain(int id) {
        return clusteredData.contains(id);
    }

    //remove the tuple that has changed the cluster
    void removeTuple(int id) {
        clusteredData.remove(id);
    }

    public String toString() {
        String str = "Centroid=(";
        for (int i = 0; i < centroid.getLength(); i++)
            str += centroid.get(i);
        str += ")";
        return str;
    }

    public String toString(Data data) {
        String str = "Centroid=(";
        for (int i = 0; i < centroid.getLength(); i++)
            str += centroid.get(i) + " ";
        str += ")\nExamples:\n";
        for (int i : clusteredData) {
            str += "[";
            for (int j = 0; j < data.getNumberOfAttributes(); j++)
                str += data.getAttributeValue(i, j) + " ";
            str += "] dist=" + getCentroid().getDistance(data.getItemSet(i)) + "\n";
        }
        str += "AvgDistance=" + getCentroid().avgDistance(data, clusteredData) + "\n";
        return str;
    }

}
