package mining;

import data.Data;
import data.Tuple;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * <h2>La classe Cluster modella il cluster di un algoritmo di clustering.</h2>
 * <p>
 * Il cluster è rappresentato da un centroide e da un insieme di indici di transazioni.</p>
 */
class Cluster implements Serializable {

    /**
     * <h4>Centroide del cluster.</h4>
     */
    private final Tuple centroid;

    /**
     * <h4>Insieme di indici di transazioni che appartengono al cluster.</h4>
     */
    private final Set<Integer> clusteredData;

    /**
     * <h4>Costruisce un cluster con il centroide specificato.</h4>
     * @param centroid il centroide del cluster
     * @see Tuple
     */
    Cluster(Tuple centroid) {
        this.centroid = centroid;
        clusteredData = new HashSet<>();
    }

    /**
     * <h4>Restituisce il centroide del cluster.</h4>
     * @return Il centroide del cluster
     * @see Tuple
     */
    Tuple getCentroid() {
        return centroid;
    }

    /**
     * <h4>Calcola il nuovo centroide del cluster.</h4>
     * @param data il dataset su cui calcolare il centroide del cluster
     * @see Data
     */
    void computeCentroid(Data data) {
        for (int i = 0; i < centroid.getLength(); i++) {
            centroid.get(i).update(data, clusteredData);
        }
    }

    /**
     * <h4>Aggiunge una transazione all'insieme di transazioni che appartengono al cluster.</h4>
     * @param id l'indice della transazione da aggiungere
     * @return <code>true</code> se la transazione la tupla cambia cluster, <code>false</code> altrimenti
     */
    boolean addData(int id) {
        return clusteredData.add(id);
    }

    /**
     * <h4>Verifica se una transazione è presente nel cluster.</h4>
     * @param id l'indice della transazione da verificare
     * @return <code>true</code> se la transazione è presente, <code>false</code> altrimenti
     */
    boolean contain(int id) {
        return clusteredData.contains(id);
    }

    /**
     * <h4>Rimuove una transazione dall'insieme di transazioni che appartengono al cluster.</h4>
     * @param id l'indice della transazione da rimuovere
     */
    void removeTuple(int id) {
        clusteredData.remove(id);
    }

    /**
     * <h4>Restituisce la stringa che rappresenta il cluster.</h4>
     * @return La stringa che rappresenta il cluster
     */
    public String toString() {
        String str = "Centroid=(";
        for (int i = 0; i < centroid.getLength(); i++)
            str += centroid.get(i) + (i==centroid.getLength()-1?"":" ");
        str += ")";
        return str;
    }

    /**
     * <h4>Restituisce la stringa che rappresenta le informazioni sul cluster in relazione al dataset.</h4>
     * @param data il dataset
     * @return La stringa che rappresenta il cluster
     * @see Data
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
