package fr.miage.fsgbd;

import java.io.*;

public class BDeserializer<Type> {

	/**
	 * Méthode pour récupérer un arbre déjà créé
	 * @param path nom du fichier
	 * @return un btree
	 */
    public BTreePlus<Type> getArbre(String path) {
        BTreePlus<Type> arbre = null;
        try {

            FileInputStream fichier = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fichier);
            arbre = (BTreePlus<Type>) ois.readObject();

        } catch (java.io.IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return arbre;
    }

}

