package edu.arizona.josesosa.creational.builder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Builder for creating HTML files from Contact data using Jsoup.
 */
public class HTMLBuilder implements ContactBuilder {
    private String name;
    private String email;
    private String phone;
    private List<Contact> contacts;
    private Document document;
    private boolean built = false;
    
    /**
     * Creates a new HTML builder.
     */
    public HTMLBuilder() {
        contacts = new ArrayList<>();
        document = Jsoup.parse("<html><head><title>Contacts</title></head><body></body></html>");
        document.head().appendElement("style").text(
                "table { border-collapse: collapse; width: 100%; }" +
                "th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }" +
                "th { background-color: #f2f2f2; }" +
                "tr:nth-child(even) { background-color: #f9f9f9; }" +
                "h1 { color: #333; }"
        );
    }
    
    @Override
    public void addName(String name) {
        this.name = name;
    }
    
    @Override
    public void addEmail(String email) {
        this.email = email;
    }
    
    @Override
    public void addPhone(String phone) {
        this.phone = phone;
    }
    
    @Override
    public ContactBuilder build() {
        if (!built) {
            // Store current contact data
            if (name != null || email != null || phone != null) {
                contacts.add(new Contact(name, email, phone));
            }
            
            Element body = document.body();
            body.appendElement("h1").text("Contact Information");
            
            Element table = body.appendElement("table");
            Element headerRow = table.appendElement("tr");
            headerRow.appendElement("th").text("Name");
            headerRow.appendElement("th").text("Email");
            headerRow.appendElement("th").text("Phone");
            
            for (Contact contact : contacts) {
                Element row = table.appendElement("tr");
                row.appendElement("td").text(contact.name != null ? contact.name : "");
                row.appendElement("td").text(contact.email != null ? contact.email : "");
                row.appendElement("td").text(contact.phone != null ? contact.phone : "");
            }
            
            built = true;
        }
        return this;
    }
    
    @Override
    public void save(String filePath) throws IOException {
        if (!built) {
            build();
        }
        
        try (FileWriter writer = new FileWriter(new File(filePath))) {
            writer.write(document.outerHtml());
        }
    }
    
    @Override
    public String getContent() {
        if (!built) {
            build();
        }
        return document.outerHtml();
    }
    
    /**
     * Adds a new contact to the HTML document.
     * 
     * @param name the contact's name
     * @param email the contact's email
     * @param phone the contact's phone
     * @return this builder for method chaining
     */
    public HTMLBuilder addContact(String name, String email, String phone) {
        contacts.add(new Contact(name, email, phone));
        return this;
    }
    
    /**
     * Simple contact data holder for internal use.
     */
    private static class Contact {
        private final String name;
        private final String email;
        private final String phone;
        
        public Contact(String name, String email, String phone) {
            this.name = name;
            this.email = email;
            this.phone = phone;
        }
    }
}