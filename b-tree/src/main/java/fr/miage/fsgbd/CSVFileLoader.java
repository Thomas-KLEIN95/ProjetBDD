package fr.miage.fsgbd;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;

public class CSVFileLoader {
    public CSVFileLoader() {
    }

    public BTreePlus loadCSV(String path, int u, Executable e) {
        String root = System.getProperty("user.dir");
        System.out.println(root);
        BTreePlus bArbre = null;
        try (CSVReader csvReader = new CSVReader(new FileReader(root+"/"+path))) {

            bArbre = new BTreePlus(u,e);
            String[] values = null;
            bArbre.setReadingCSV(true);
            if ((values = csvReader.readNext()) != null) {
                while ((values = csvReader.readNext()) != null) {
                    //records.add(Arrays.asList(values));
                    bArbre.addValeur(Integer.parseInt(values[0]));
                }
            }
            bArbre.setReadingCSV(false);
        } catch (CsvValidationException csvValidationException) {
            csvValidationException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        return bArbre;
    }
}
