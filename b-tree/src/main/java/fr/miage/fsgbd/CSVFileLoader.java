package fr.miage.fsgbd;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;

public class CSVFileLoader {

    /**
     * Constructeur vide
     */
    public CSVFileLoader() {
    }

    /**
     * méthode pour charger un fichier CSV
     * @param path nom du fichier CSV
     * @param u numéro du noeud
     * @param e executable
     * @return un bTree
     */
    public BTreePlus loadCSV(String path, int u, Executable e) {

        String root = System.getProperty("user.dir"); //chemin pour accéder au fichier CSV
        System.out.println(root); // affichage du chemin
        BTreePlus bArbre = null; // initialisation d'un arbre btree+

        try (CSVReader csvReader = new CSVReader(new FileReader(root+"/"+path))) {

            bArbre = new BTreePlus(u,e); // creation d'une btree+
            String[] values = null; //création d'un tableau

            bArbre.setReadingCSV(true); //lecture d'un fichier CSV activé

            if ((values = csvReader.readNext()) != null) { //pas lecture de la première ligne car nom des colonnes
                while ((values = csvReader.readNext()) != null) { //lecture de chaque ligne jusqu'à la fin du fichier CSV
                    bArbre.addValeur(Integer.parseInt(values[0])); //ajout des valeurs de la première colonne du CSV (id) dans l'arbre
                }
            }

            bArbre.setReadingCSV(false); //lecture d'un fichier CSV activé

        } catch (CsvValidationException csvValidationException) {
            csvValidationException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        return bArbre; // retounre un arbre bTreePlus
    }
}
