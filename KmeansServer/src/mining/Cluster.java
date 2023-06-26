package mining;

import data.Data;
import data.Tuple;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * La classe Cluster modella il cluster di un algoritmo di clustering.
 * Il cluster è rappresentato da un centroide e da un insieme di indici di transazioni.
 */
class Cluster implements Serializable {

    /**
     * Il centroide del cluster.
     */
    private final Tuple centroid;

    /**
     * L'insieme di indici di transazioni che appartengono al cluster.
     */
    private final Set<Integer> clusteredData;

    /**
     * Costruisce un cluster con il centroide specificato.
     * @param centroid il centroide del cluster
     */
    Cluster(Tuple centroid) {
        this.centroid = centroid;
        clusteredData = new HashSet<>();
    }

    /**
     * Restituisce il centroide del cluster.
     * @return <code>centroid</code> il centroide del cluster
     */
    Tuple getCentroid() {
        return centroid;
    }

    /**
     * Calcola il nuovo centroide del cluster.
     * @param data il dataset su cui calcolare il centroide del cluster
     */
    void computeCentroid(Data data) {
        for (int i = 0; i < centroid.getLength(); i++) {
            centroid.get(i).update(data, clusteredData);
        }
    }

    /**
     * Aggiunge una transazione all'insieme di transazioni che appartengono al cluster.
     * @param id l'indice della transazione da aggiungere
     * @return <code>true</code> se la transazione la tupla cambia cluster, <code>false</code> altrimenti
     */
    boolean addData(int id) {
        return clusteredData.add(id);
    }

    /**
     * Verifica se una transazione è clusterizzata nell'array corrente.
     * @param id l'indice della transazione da verificare
     * @return <code>true</code> se la transazione è clusterizzata, <code>false</code> altrimenti
     */
    boolean contain(int id) {
        return clusteredData.contains(id);
    }

    /**
     * Rimuove una transazione dall'insieme di transazioni che appartengono al cluster.
     * @param id l'indice della transazione da rimuovere
     */
    void removeTuple(int id) {
        clusteredData.remove(id);
    }

    /**
     * Restituisce la stringa che rappresenta il cluster.
     * @return <code>str</code> la stringa che rappresenta il cluster
     */
    public String toString() {
        String str = "Centroid=(";
        for (int i = 0; i < centroid.getLength(); i++)
            str += centroid.get(i) + (i==centroid.getLength()-1?"":" ");
        str += ")";
        return str;
    }

    /**
     * Restituisce la stringa che rappresenta le informazioni sul cluster in relazione al dataset.
     * @param data il dataset
     * @return <code>str</code> la stringa che rappresenta il cluster
     */
    public String toString(Data data) {
        String str = "Centroid=(";
        for (int i = 0; i < centroid.getLength(); i++)
            str += centroid.get(i) + (i==centroid.getLength()-1?"":" ");
        str += ")\nExamples:\n";
        for (int i : clusteredData) {
            str += "[";
            for (int j = 0; j < data.getNumberOfAttributes(); j++)
                str += data.getAttributeValue(i, j) + (j==data.getNumberOfAttributes()-1?"":" ");
            str += "] dist=" + getCentroid().getDistance(data.getItemSet(i)) + "\n";
        }
        str += "AvgDistance=" + getCentroid().avgDistance(data, clusteredData) + "\n";
        return str;
    }

}
