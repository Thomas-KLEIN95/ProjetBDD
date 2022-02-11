package fr.miage.fsgbd;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class CSVSearchStats {
    BTreePlus bTree;
    Float searchTime;
    float minTimeSeq, maxTimeSeq, avgTimeSeq, minTimeIndex, maxTimeIndex, avgTimeIndex;

    public CSVSearchStats(BTreePlus bTree){
        this.bTree = bTree;
    }

    public void seqSearch(Integer value){
        //début timer
        //appel recherche sur btree
        //fin timer
        //calcul stats
    }

    public void indexSearch(Integer value){

    }

    /**
     * Générer plusieurs clés contenus dans le fichier puis rechercher (séquentiellement et par index)  ces clés dans btree
     * @param nb nombre de clés à générer
     */
    public void searchMany(Integer nb){
        ArrayList<Integer> keyLines = new ArrayList<>();
        Integer keyLine, key;
        Random rand = new Random();

        while(keyLines.size()<nb) {
            keyLine = rand.nextInt(bTree.getNbLinesCSV()-1)+2;
            if(!keyLines.contains(keyLine)){
                keyLines.add(keyLine);
            }
        }

        for(int i = 0 ; i < nb ; i++){
            key = bTree.getKeyFromPos(keyLines.get(i));
            seqSearch(key);
            indexSearch(key);
        }
    }
}
