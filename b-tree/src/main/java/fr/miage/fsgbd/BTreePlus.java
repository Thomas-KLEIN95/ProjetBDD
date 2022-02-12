package fr.miage.fsgbd;

import javax.swing.tree.DefaultMutableTreeNode;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * @author Galli Gregory, Mopolo Moke Gabriel
 * @param <Type>
 */
public class BTreePlus<Type> implements java.io.Serializable {
    private Noeud<Type> racine;
    private HashMap<Integer, Integer> CSVLines;
    private Integer line;
    private boolean readingCSV;

    public BTreePlus(int u, Executable e) {
        racine = new Noeud<Type>(u, e, null);
        CSVLines = new HashMap<>();
        readingCSV = false;
    }

    public void afficheArbre() {
        racine.afficheNoeud(true, 0);
    }

    /**
     * Méthode récursive permettant de récupérer tous les noeuds
     *
     * @return DefaultMutableTreeNode
     */
    public DefaultMutableTreeNode bArbreToJTree() {
        return bArbreToJTree(racine);
    }

    private DefaultMutableTreeNode bArbreToJTree(Noeud<Type> root) {
        StringBuilder txt = new StringBuilder();
        for (Type key : root.keys)
            txt.append(key.toString()).append(" ");

        DefaultMutableTreeNode racine2 = new DefaultMutableTreeNode(txt.toString(), true);
        for (Noeud<Type> fil : root.fils)
            racine2.add(bArbreToJTree(fil));

        return racine2;
    }


    public boolean addValeur(Type valeur) {
        System.out.println("Ajout de la valeur : " + valeur.toString());
        if (racine.contient(valeur) == null) {
            Noeud<Type> newRacine = racine.addValeur(valeur);
            if(readingCSV == true){
                CSVLines.put((Integer) valeur, line);
                line++;
            }
            if (racine != newRacine)
                racine = newRacine;
            return true;
        }
        return false;
    }


    public void removeValeur(Type valeur) {
        System.out.println("Retrait de la valeur : " + valeur.toString());
        if (racine.contient(valeur) != null) {
            Noeud<Type> newRacine = racine.removeValeur(valeur, false);
            if (racine != newRacine)
                racine = newRacine;
        }
    }

    /**
     * Lire un fichier CSV avec HashMap
     * @param status
     */
    public void setReadingCSV(boolean status){
        readingCSV = status;
        if(status == true){
            line = 2;
            CSVLines.clear();
        }
    }

    /**
     * Obtenir le nombre de lignes totales du fichier CSV grâce à HashMap
     * @return nombre totale de lignes du fichier CSV
     */
    public Integer getNbLinesCSV(){
        return CSVLines.size();
    }

    /**
     * Obtenir la clé par la valeur
     * @param pos valeur recherchée
     * @return tableau contenant les clés associées aux valeurs
     */
    public Integer getKeyFromPos(Integer pos){
        ArrayList<Integer> keys = new ArrayList<Integer>(CSVLines.keySet());
        return keys.get(pos);
    }

    public Noeud<Type> getPremiereFeuille(){
        Noeud feuille = racine;
        while(!feuille.fils.isEmpty()){
            feuille = (Noeud) feuille.fils.get(0);
        }
        return feuille;
    }

    public boolean rechercheSeq(Integer valeur){
        return getPremiereFeuille().rechercheSeq(valeur);
    }

    public boolean rechecheIndex(Integer valeur){
        return racine.rechercheIndex(valeur);
    }
}
