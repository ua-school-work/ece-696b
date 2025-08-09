package edu.arizona.josesosa.creational.builder;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Builder for creating PDF files from Contact data using Apache PDFBox.
 */
public class PDFBuilder implements ContactBuilder {
    private String name;
    private String email;
    private String phone;
    private PDDocument document;
    private List<Contact> contacts;
    private boolean built = false;
    
    /**
     * Creates a new PDF builder.
     */
    public PDFBuilder() {
        document = new PDDocument();
        contacts = new ArrayList<>();
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
            
            try {
                PDPage page = new PDPage(PDRectangle.A4);
                document.addPage(page);
                
                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    // Set up the page
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    
                    // Title
                    contentStream.beginText();
                    contentStream.newLineAtOffset(50, 750);
                    contentStream.showText("Contact Information");
                    contentStream.endText();
                    
                    // Headers
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(50, 720);
                    contentStream.showText("Name");
                    contentStream.newLineAtOffset(150, 0);
                    contentStream.showText("Email");
                    contentStream.newLineAtOffset(150, 0);
                    contentStream.showText("Phone");
                    contentStream.endText();
                    
                    // Data
                    contentStream.setFont(PDType1Font.HELVETICA, 10);
                    float y = 700;
                    for (Contact contact : contacts) {
                        contentStream.beginText();
                        contentStream.newLineAtOffset(50, y);
                        contentStream.showText(contact.name != null ? contact.name : "");
                        contentStream.newLineAtOffset(150, 0);
                        contentStream.showText(contact.email != null ? contact.email : "");
                        contentStream.newLineAtOffset(150, 0);
                        contentStream.showText(contact.phone != null ? contact.phone : "");
                        contentStream.endText();
                        y -= 20;
                    }
                }
                
                built = true;
            } catch (IOException e) {
                throw new RuntimeException("Error building PDF document: " + e.getMessage(), e);
            }
        }
        return this;
    }
    
    @Override
    public void save(String filePath) throws IOException {
        if (!built) {
            build();
        }
        
        document.save(filePath);
    }
    
    @Override
    public String getContent() {
        if (!built) {
            build();
        }
        return "PDF document with contact data";
    }
    
    /**
     * Adds a new contact to the PDF document.
     * 
     * @param name the contact's name
     * @param email the contact's email
     * @param phone the contact's phone
     * @return this builder for method chaining
     */
    public PDFBuilder addContact(String name, String email, String phone) {
        contacts.add(new Contact(name, email, phone));
        return this;
    }
    
    /**
     * Closes the document and releases resources.
     * 
     * @throws IOException if an I/O error occurs
     */
    public void close() throws IOException {
        if (document != null) {
            document.close();
        }
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