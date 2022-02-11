package fr.miage.fsgbd;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import org.apache.commons.lang3.time.StopWatch;
import java.util.concurrent.TimeUnit;

/**
 * classe permettant de faire les recherches séquentielles et par index ,
 * ainsi que les statistiques (temps minimum, temps maximum, temps moyen) par type de recherche
 */

public class CSVSearchStats {
    BTreePlus bTree;
    Float searchTime;
    float minTimeSeq, maxTimeSeq, avgTimeSeq, minTimeIndex, maxTimeIndex, avgTimeIndex;

    /**
     * Constructeur
     * @param bTree l'arbre sur lequel on effectue les statistiques
     */
    public CSVSearchStats(BTreePlus bTree){
        this.bTree = bTree;
    }

    /**
     * Recherche séquentielle dans l'arbre : de feuille en feuille
     * @param value la valeur recherchée de manière séquencielle
     */
    public void seqSearch(Integer value){

        //appel recherche sequentielle sur btree

    }

    /**
     * Recherche par index dans l'arbre : de noeud père en noeud fils
     * @param value la valeur recherchée par indexation
     */
    public void indexSearch(Integer value){

        //appel recherche par index sur btree

    }

    /**
     * Générer plusieurs clés contenus dans le fichier puis rechercher (séquentiellement et par index)  ces clés dans btree
     * @param nb nombre de clés à générer
     */
    public void searchMany(Integer nb){
        //variables pour stats pour recherches séquentielles
        long tempsMinSeq = 0, tempsMaxSeq = 0, tempsMoyenSeq = 0;
        //variables pour stats pour recherches par index
        long tempsMinIndex = 0, tempsMaxIndex = 0, tempsMoyenIndex = 0;

        long temps = 0; //temps d'une recherche
        StopWatch stopWatch = new StopWatch(); // creation d'un timer

        ArrayList<Integer> keyLines = new ArrayList<>();
        Integer keyLine, key;
        Random rand = new Random(); // creation de l'aléatoire

        while(keyLines.size()<nb) {
            keyLine = rand.nextInt(bTree.getNbLinesCSV()-1)+2;
            if(!keyLines.contains(keyLine)){
                keyLines.add(keyLine);
            }
        }

        for(int i = 0 ; i < nb ; i++){
            key = bTree.getKeyFromPos(keyLines.get(i)); //obtenir la clé d'une position

            /** recherche séquentielle */
            stopWatch.start(); //début timer
            seqSearch(key); //recherche séquentielle
            stopWatch.stop(); //fin timer

            temps = stopWatch.getTime(TimeUnit.MILLISECONDS); //sauvegarde du temps

            if (tempsMaxSeq < temps){ // si le temps max est plus petit que le temps actuel
                tempsMaxSeq = temps; // le temps actuel devient le temps max

            } else if (tempsMinSeq > temps) { // si le temps min est plus grand que le temps actuel
                tempsMinSeq = temps; // le temps actuel devient le temps min
            }

            tempsMoyenSeq = temps + tempsMoyenSeq; //total des temps recherches
            tempsMoyenSeq = tempsMoyenSeq / nb; // calcul du temps moyen


            stopWatch.reset(); //reset du timer pour la recherche suivante


            /** recherche par index */
            stopWatch.start(); //début timer
            indexSearch(key); //recherche par index
            stopWatch.stop(); //fin timer

            temps = stopWatch.getTime(TimeUnit.MILLISECONDS);

            if (tempsMaxIndex < temps){ // si le temps max est plus petit que le temps actuel
                tempsMaxIndex = temps; // le temps actuel devient le temps max

            } else if (tempsMinIndex > temps) { // si le temps min est plus grand que le temps actuel
                tempsMinIndex = temps; // le temps actuel devient le temps min
            }

            tempsMoyenIndex = temps + tempsMoyenIndex; //total des temps recherches
            tempsMoyenIndex = tempsMoyenIndex / nb; // calcul du temps moyen

            stopWatch.reset(); //reset du timer pour la recherche suivante

        }

        System.out.println("Pour une recherche séquentielle, le temps minimum est de " + tempsMinSeq + " millisecondes, " +
                "le temps maximum est de " + tempsMaxSeq + " millisecondes, " +
                "et le temps moyen est de " + tempsMoyenSeq) ;

        System.out.println("Pour une recherche par index, le temps minimum est de " + tempsMinIndex + " millisecondes, " +
                "le temps maximum est de " + tempsMaxIndex + " millisecondes, " +
                "et le temps moyen est de " + tempsMoyenIndex) ;

    }
}
