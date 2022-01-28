package com.example.appengine.springboot;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

public class CSVHelper{

    private final List<String[]> data = new ArrayList<String[]>();

    public CSVHelper(CSVFile file){
        try{
            String folder = "src/main/resources/";
            String filePath = folder.concat(file.getFileName());
    
            FileReader filereader = new FileReader(filePath);
            CSVReader csvReader = new CSVReader(filereader);

            data.addAll(csvReader.readAll());

            csvReader.close();
        }
        catch(FileNotFoundException e)
        {
            System.err.println("Could not FIND file: " + file);
        }
        catch(IOException e)
        {
            System.err.println("Could not READ file: " + file);
        }
    }

    /**
     * Gets the contents of the "cell" from this objects CSV file.
     * Zero indexed. Returns null if not found.
     */
    public String getCell(int row, int column)
    {
        try{
            return null;
        }
        catch(Exception e)
        {
            return null;
        }
    }

    public enum CSVFile
    {
        EN("lang_en.csv"),
        ES("lang_es.csv");

        private String fileName;

        private CSVFile(String fileName)
        {
            this.fileName = fileName;
        }

        public String getFileName()
        {
            return fileName;
        }

        @Override
        public String toString()
        {
            return fileName;
        }
    }
}
