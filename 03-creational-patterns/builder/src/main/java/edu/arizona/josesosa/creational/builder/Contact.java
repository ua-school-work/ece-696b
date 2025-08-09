package edu.arizona.josesosa.creational.builder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Contact class that uses the Builder pattern for importing and exporting contact data.
 */
public class Contact {
  private Email email;
  private Name name;
  private Phone phone;

  /**
   * Interface for exporting contact data.
   */
  public interface Exporter {
    void addName(String name);
    void addEmail(String email);
    void addPhone(String phone);
  }

  /**
   * Interface for importing contact data.
   */
  public interface Importer {
    String provideName();
    String provideEmail();
    String providePhone();
    void open();
    void close();
  }

  /**
   * Constructs a Contact from the importer provided.
   * 
   * @param importer the importer to use
   */
  public Contact(Importer importer) {
    importer.open();
    this.name = new Name(importer.provideName());
    this.email = new Email(importer.provideEmail());
    this.phone = new Phone(importer.providePhone());
    importer.close();
  }

  /**
   * Exports this contact to the exporter provided as argument.
   * 
   * @param exporter the exporter to use
   */
  public void export(Exporter exporter) {
    exporter.addName(name.toString());
    exporter.addEmail(email.toString());
    exporter.addPhone(phone.toString());
  }
  
  /**
   * Exports this contact to the builder provided and builds the output.
   * 
   * @param builder the builder to use
   * @return the builder after building
   */
  public ContactBuilder exportAndBuild(ContactBuilder builder) {
    export(builder);
    return builder.build();
  }
  
  /**
   * Exports this contact to the builder provided, builds the output, and saves it to a file.
   * 
   * @param builder the builder to use
   * @param filePath the path where the file should be saved
   * @throws IOException if an I/O error occurs
   */
  public void exportAndSave(ContactBuilder builder, String filePath) throws IOException {
    export(builder);
    builder.build().save(filePath);
  }
  
  /**
   * Creates a list of contacts from a CSV file.
   * 
   * @param csvFilePath the path to the CSV file
   * @return a list of contacts
   */
  public static List<Contact> fromCSV(String csvFilePath) {
    List<Contact> contacts = new ArrayList<>();
    
    try {
      // Create a custom importer for each contact to ensure proper state
      // First contact
      ContactCSVImporter firstImporter = new ContactCSVImporter(csvFilePath);
      firstImporter.open();
      contacts.add(new Contact(firstImporter));
      firstImporter.close();
      
      // For remaining contacts, we need to create new importers
      // This is because the Contact constructor calls open() and close()
      // which would reset the iterator position
      org.apache.commons.csv.CSVParser parser = org.apache.commons.csv.CSVFormat.DEFAULT
          .builder()
          .setHeader("name", "email", "phone")
          .setSkipHeaderRecord(true)
          .build()
          .parse(new java.io.FileReader(csvFilePath));
      
      // Skip the first record since we already processed it
      java.util.Iterator<org.apache.commons.csv.CSVRecord> iterator = parser.iterator();
      if (iterator.hasNext()) {
        iterator.next(); // Skip first record
      }
      
      // Process remaining records
      while (iterator.hasNext()) {
        org.apache.commons.csv.CSVRecord record = iterator.next();
        contacts.add(new Contact(new StaticImporter(
            record.get("name"),
            record.get("email"),
            record.get("phone")
        )));
      }
      
      parser.close();
    } catch (Exception e) {
      throw new RuntimeException("Error importing contacts from CSV: " + e.getMessage(), e);
    }
    
    return contacts;
  }
  
  /**
   * A simple static importer that returns predefined values.
   * Used for creating contacts from CSV records.
   */
  private static class StaticImporter implements Importer {
    private final String name;
    private final String email;
    private final String phone;
    
    public StaticImporter(String name, String email, String phone) {
      this.name = name;
      this.email = email;
      this.phone = phone;
    }
    
    @Override
    public String provideName() {
      return name;
    }
    
    @Override
    public String provideEmail() {
      return email;
    }
    
    @Override
    public String providePhone() {
      return phone;
    }
    
    @Override
    public void open() {
      // No-op
    }
    
    @Override
    public void close() {
      // No-op
    }
  }

  @Override
  public String toString() {
    return String.format("%s %s %s", name, email, phone); 
  }

  private static class Name {
    private String name;
    public Name(String name) {
      this.name = name;
    }
    public String toString() { return name; }
  }
  
  private static class Email {
    private String email;
    public Email(String email) {
      this.email = email;
    }
    public String toString() { return email; }
  }
  
  private static class Phone {
    private String phone;
    public Phone(String phone) {
      this.phone = phone;
    }
    public String toString() { return phone; }
  }
}
