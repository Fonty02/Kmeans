package mining;

import data.Data;
import data.OutOfRangeSampleSize;
import data.Tuple;
import java.io.Serializable;


/**
 * <h2>La classe ClusterSet modella l'insieme di cluster.</h2>
 * <p>L'insieme di cluster è rappresentato da un array di {@link Cluster} e da un indice che indica il numero di cluster presenti.</p>
 * @see Cluster
 */
public class ClusterSet implements Serializable {

    /**
     * <h4>Array di Cluster.</h4>
     * @see Cluster
     */
    private final Cluster[] C;

    /**
     * <h4>Indice che indica il numero di cluster presenti.</h4>
     */
    private int i = 0;


    /**
     * <h4>Costruisce un insieme di cluster con il numero di cluster specificato.</h4>
     * @param k il numero di cluster
     * @throws OutOfRangeSampleSize se il numero di cluster è minore di 1
     * @see NegativeArraySizeException
     */
    ClusterSet(int k) throws OutOfRangeSampleSize {
        try {
            C = new Cluster[k];
        } catch (NegativeArraySizeException e) {
            throw new OutOfRangeSampleSize("Numero di cluster non valido, deve essere maggiore di 0");
        }
    }


    /**
     * <h4>Aggiunge un cluster all'insieme di cluster e incrementa l'indice.</h4>
     * @param c il cluster da aggiungere
     */
    private void add(Cluster c) {
        C[i] = c;
        i++;
    }

    /**
     * <h4>Inizializza i centroidi dei cluster con k tuple casuali del dataset, una per cluster.</h4>
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
     * <h4>Restituisce il cluster più vicino alla tupla specificata.</h4>
     * @param tuple la tupla
     * @return Il cluster più vicino alla tupla
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
     * <h4>Restituisce il cluster che contiene la tupla specificata.</h4>
     * <p>
     * In caso di tupla che non appartiene a nessun cluster, restituisce <code>null</code>.</p>
     * @param id l'indice della tupla
     * @return Il cluster che contiene la tupla
     */
    Cluster currentCluster(int id) {
        for (Cluster cluster : C) {
            if (cluster.contain(id))
                return cluster;
        }
        return null;
    }

    /**
     * <h4>Calcola i nuovi centroidi dei cluster.</h4>
     * @param data il dataset su cui calcolare i nuovi centroidi dei cluster
     * @see Data
     */
    void updateCentroids(Data data) {
        for (Cluster cluster : C) {
            cluster.computeCentroid(data);
        }
    }

    /**
     * <h4>Restituisce la stringa che rappresenta l'insieme di cluster.</h4>
     * @return La stringa che rappresenta l'insieme di cluster
     */
    public String toString() {
        String str = "";
        for (Cluster cluster : C) {
            str += cluster.toString() + "\n";
        }
        return str;
    }

    /**
     * <h4>Restituisce la stringa che rappresenta le informazioni sull'insieme di cluster relative al dataset specificato.</h4>
     * @param data il dataset
     * @return La stringa che rappresenta le informazioni sull'insieme di cluster relative al dataset specificato
     * @see Data
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
