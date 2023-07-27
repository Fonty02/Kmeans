package mining;

import data.Data;
import data.OutOfRangeSampleSize;
import data.Tuple;
import java.io.Serializable;


/**
 * La classe ClusterSet modella l'insieme di cluster.
 * L'insieme di cluster è rappresentato da un array di cluster e da un indice che indica il numero di cluster presenti.
 */
public class ClusterSet implements Serializable {

    /**
     * L'array di cluster.
     */
    private final Cluster[] C;

    /**
     * L'indice che indica il numero di cluster presenti.
     */
    private int i = 0;


    /**
     * Costruisce un insieme di cluster con il numero di cluster specificato.
     * @param k il numero di cluster
     * @throws OutOfRangeSampleSize se il numero di cluster è minore di 1
     */
    ClusterSet(int k) throws OutOfRangeSampleSize {
        try {
            C = new Cluster[k];
        } catch (NegativeArraySizeException e) {
            throw new OutOfRangeSampleSize("Numero di cluster non valido, deve essere maggiore di 0");
        }
    }


    /**
     * Aggiunge un cluster all'insieme di cluster e incrementa l'indice.
     * @param c il cluster da aggiungere
     */
    private void add(Cluster c) {
        C[i] = c;
        i++;
    }

    /**
     * Inizializza i centroidi dei cluster con k tuple casuali del dataset, una per cluster.
     * @param data il dataset
     * @throws OutOfRangeSampleSize se il numero di tuple del dataset è minore del numero di cluster
     */
    void initializeCentroids(Data data) throws OutOfRangeSampleSize {
        int[] centroidIndexes = data.sampling(C.length);
        for (int centroidIndex : centroidIndexes) {
            Tuple centroidI = data.getItemSet(centroidIndex);
            add(new Cluster(centroidI));
        }
    }
    /**
     * Restituisce il cluster più vicino alla tupla specificata.
     * @param tuple la tupla
     * @return <code>c</code> il cluster più vicino alla tupla
     */
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

    /**
     * Restituisce il cluster che contiene la tupla specificata.
     * In caso di tupla che non appartiene a nessun cluster, restituisce <code>null</code>.
     * @param id l'indice della tupla
     * @return <code>c</code> il cluster che contiene la tupla
     */
    Cluster currentCluster(int id) {
        for (Cluster cluster : C) {
            if (cluster.contain(id))
                return cluster;
        }
        return null;
    }

    /**
     * Calcola i nuovi centroidi dei cluster.
     * @param data il dataset su cui calcolare i nuovi centroidi dei cluster
     */
    void updateCentroids(Data data) {
        for (Cluster cluster : C) {
            cluster.computeCentroid(data);
        }
    }

    /**
     * Restituisce la stringa che rappresenta l'insieme di cluster.
     * @return la stringa che rappresenta l'insieme di cluster
     */
    public String toString() {
        String str = "";
        for (Cluster cluster : C) {
            str += cluster.toString() + "\n";
        }
        return str;
    }

    /**
     * Restituisce la stringa che rappresenta le informazioni sull'insieme di cluster relative al dataset specificato.
     * @param data il dataset
     * @return la stringa che rappresenta le informazioni sull'insieme di cluster relative al dataset specificato
     */
    public String toString(Data data) {
        String str = "";
        for (int i = 0; i < C.length; i++) {
            if (C[i] != null)
                str += i + ":" + C[i].toString(data) + "\n";
        }
        return str;
    }
}
