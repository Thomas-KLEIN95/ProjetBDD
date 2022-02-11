package fr.miage.fsgbd;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FichierReader {

    public FichierReader() {
        //hashmap a faire
    }

    //List<List<String>> records = new ArrayList<List<String>>();
    BTreePlus bArbre;

    public BTreePlus lireCSV(String path, int u, Executable e) {
        String root = System.getProperty("user.dir");
        System.out.println(root);

        try (CSVReader csvReader = new CSVReader(new FileReader(root+"/"+path));) {
            bArbre = new BTreePlus(u,e);
            String[] values = null;
            if ((values = csvReader.readNext()) != null) {
                while ((values = csvReader.readNext()) != null) {
                    //records.add(Arrays.asList(values));
                    bArbre.addValeur(Integer.parseInt(values[0]));
                }
            }
        } catch (CsvValidationException csvValidationException) {
            csvValidationException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        return bArbre;
    }

}


