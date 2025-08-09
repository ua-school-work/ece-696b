package edu.arizona.josesosa.creational.builder;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

/**
 * CSV file importer for Contact objects.
 * Processes one line at a time from a CSV file.
 */
public class ContactCSVImporter implements Contact.Importer {
    private static final String[] HEADERS = {"name", "email", "phone"};
    
    private final String csvFilePath;
    private CSVParser csvParser;
    private Iterator<CSVRecord> recordIterator;
    private CSVRecord currentRecord;
    
    /**
     * Creates a new CSV importer for the specified file.
     * 
     * @param csvFilePath path to the CSV file
     */
    public ContactCSVImporter(String csvFilePath) {
        this.csvFilePath = csvFilePath;
    }
    
    @Override
    public void open() {
        try {
            Reader reader = new FileReader(csvFilePath);
            csvParser = CSVFormat.DEFAULT
                    .builder()
                    .setHeader(HEADERS)
                    .setSkipHeaderRecord(true)
                    .build()
                    .parse(reader);
            
            recordIterator = csvParser.iterator();
            if (recordIterator.hasNext()) {
                currentRecord = recordIterator.next();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error opening CSV file: " + e.getMessage(), e);
        }
    }
    
    @Override
    public String provideName() {
        return currentRecord != null ? currentRecord.get("name") : "";
    }
    
    @Override
    public String provideEmail() {
        return currentRecord != null ? currentRecord.get("email") : "";
    }
    
    @Override
    public String providePhone() {
        return currentRecord != null ? currentRecord.get("phone") : "";
    }
    
    @Override
    public void close() {
        try {
            if (csvParser != null) {
                csvParser.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error closing CSV file: " + e.getMessage(), e);
        }
    }
    
    /**
     * Checks if there are more records in the CSV file.
     * 
     * @return true if there are more records, false otherwise
     */
    public boolean hasNext() {
        return recordIterator != null && recordIterator.hasNext();
    }
    
    /**
     * Moves to the next record in the CSV file.
     * 
     * @return true if moved to the next record, false if no more records
     */
    public boolean next() {
        if (hasNext()) {
            currentRecord = recordIterator.next();
            return true;
        }
        return false;
    }
}