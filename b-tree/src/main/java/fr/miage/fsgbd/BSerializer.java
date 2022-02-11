package fr.miage.fsgbd;

import java.io.*;
	
	public class BSerializer<Type>
	{
		/**
		 * Méthode pour sauvegarder un arbre dans un fichier
		 * @param arbre le btree a sauvegardé
		 * @param path le nom du fichier sous lequel on veut sauvegarder l'arbre
		 */
	  public BSerializer (BTreePlus<Type> arbre, String path) 
	  {	    
	    try {
	      FileOutputStream fichier = new FileOutputStream(path);
	      ObjectOutputStream oos = new ObjectOutputStream(fichier);
	      oos.writeObject(arbre);
	      oos.flush();
	      oos.close();
	    }
	    catch (java.io.IOException e) {
	      e.printStackTrace();
	    }
	  }
	}