package mining;

import data.Data;
import data.OutOfRangeSampleSize;
import data.Tuple;

import java.io.Serializable;

public class ClusterSet implements Serializable {
    private final Cluster[] C;
    private int i = 0;

    ClusterSet(int k) throws OutOfRangeSampleSize {
        try {
            C = new Cluster[k];
        } catch (NegativeArraySizeException e) {
            throw new OutOfRangeSampleSize("Numero di cluster non valido, deve essere maggiore di 0");
        }
    }

    private void add(Cluster c) {
        C[i] = c;
        i++;
    }

    void initializeCentroids(Data data) throws OutOfRangeSampleSize {
        //indici casuali
        int[] centroidIndexes = data.sampling(C.length);
        for (int centroidIndex : centroidIndexes) {
            //prende la tuple dalla riga centroidIndexs[i]
            Tuple centroidI = data.getItemSet(centroidIndex);
            add(new Cluster(centroidI)); //diventa il nuovo centroide del cluster i
        }
    }

    //data la tupla tuple calcola la distanza da ogni cluster (quindi dal suo centroide) e restituisce il piu vicino
    Cluster nearestCluster(Tuple tuple) {
        double min = tuple.getDistance(C[0].getCentroid());
        Cluster c = C[0];
        double tmp;
        for (int i = 1; i < C.length; i++) {
            tmp = tuple.getDistance(C[i].getCentroid());
            if (tmp < min) {
                min = tmp;
                c = C[i];
            }
        }
        return c;
    }

    Cluster currentCluster(int id) {
        for (Cluster cluster : C) {
            if (cluster.contain(id))
                return cluster;
        }
        return null;
    }

    void updateCentroids(Data data) {
        for (Cluster cluster : C) {
            cluster.computeCentroid(data);
        }
    }

    public String toString() {
        String str = "";
        for (Cluster cluster : C) {
            str += cluster.toString() + "\n";
        }
        return str;
    }

    public String toString(Data data) {
        String str = "";
        for (int i = 0; i < C.length; i++) {
            if (C[i] != null)
                str += i + ":" + C[i].toString(data) + "\n";
        }
        return str;
    }

}
