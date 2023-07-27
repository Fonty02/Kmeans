package mining;

import data.Data;
import data.OutOfRangeSampleSize;

import java.io.*;

/**
 * La classe KMeansMiner modella l'algoritmo K-means.
 */
public class KMeansMiner {

    /**
     * L'insieme di cluster.
     */
    private ClusterSet C;

    /**
     * Inizializza il clusterSet su cui eseguire il K-means.
     * @param k il numero di cluster da creare nel clusterSet
     * @throws OutOfRangeSampleSize se il numero di cluster specificato è minore di 1
     */

    public KMeansMiner(int k) throws OutOfRangeSampleSize {
        C = new ClusterSet(k);
    }

    /**
     * Carica il clusterSet su cui è stato eseguito il K-means da un file.
     * @param fileName il nome del file da cui caricare il clusterSet
     * @throws IOException se si verifica un errore di I/O
     * @throws ClassNotFoundException se si verifica un errore di caricamento della classe
     */
    public KMeansMiner(String fileName) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileName)));
        C = (ClusterSet) in.readObject();
        in.close();
    }

    /**
     * Restituisce il clusterSet su cui è stato eseguito il K-means.
     * @return il clusterSet su cui è stato eseguito il K-means
     */
    public ClusterSet getC() {
        return C;
    }

    /**
     * Esegue l'algoritmo di clustering K-means.
     * @param data il dataset
     * @return il numero di iterazioni eseguite
     * @throws OutOfRangeSampleSize se il numero di tuple del dataset è minore del numero di cluster
     */
    public int kmeans(Data data) throws OutOfRangeSampleSize {
        int numberOfIterations = 0;
        //STEP 1. Scelta casuale di centroidi per k clusters
        C.initializeCentroids(data);
        boolean changedCluster;
        do {
            numberOfIterations++;
            //STEP 2. Assegnazione di ciascuna riga della matrice in data al cluster avente centroide più vicino all'esempio
            changedCluster = false;
            for (int i = 0; i < data.getNumberOfExamples(); i++) {
                Cluster nearestCluster = C.nearestCluster(data.getItemSet(i));
                Cluster oldCluster = C.currentCluster(i);
                boolean currentChange = nearestCluster.addData(i);
                if (currentChange)
                    changedCluster = true;
                //rimuovo la tupla dal vecchio cluster
                if (currentChange && oldCluster != null)
                    //il nodo va rimosso dal suo vecchio cluster
                    oldCluster.removeTuple(i);
            }
            //STEP 3. Calcolo dei nuovi centroidi per ciascun cluster
            C.updateCentroids(data);
        }
        while (changedCluster);
        return numberOfIterations;
    }

    /**
     * Salva il clusterSet su cui è stato eseguito il K-means su un file.
     * @param fileName il nome del file su cui salvare il clusterSet
     * @throws IOException se si verifica un errore di I/O
     */
    public void salva(String fileName) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
        out.writeObject(C);
        out.close();
    }

}

