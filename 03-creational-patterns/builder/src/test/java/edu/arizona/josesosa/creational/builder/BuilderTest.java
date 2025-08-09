package edu.arizona.josesosa.creational.builder;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Demonstrates the use of the Builder pattern for importing and exporting contact data.
 */
class BuilderTest {

    private static final String CSV_FILE = "src/test/resources/contacts.csv";
    private static final String OUTPUT_DIR = "target/output";

    // Commented out to avoid GUI popup during automated tests
    // Uncomment to run manually
    // @Test
    void testOriginalImplementation() {
        // Original implementation
        Contact c = new Contact(new ContactSwingImporter());
        System.out.println("Contact: " + c);
        
        ContactHTMLExporter html = new ContactHTMLExporter();
        c.export(html);
        System.out.println("HTML:");
        System.out.println(html);
        
        ContactCSVExporter csv = new ContactCSVExporter();
        c.export(csv);
        System.out.println("CSV:\n" + csv);
        
        System.out.println("Starting gui display...");
        GUIExporter gui = new GUIExporter();
        c.export(gui);
        new SwingDisplay(gui.getJPanel());
    }
    
    @Test
    void testImprovedImplementation() throws IOException {
        // Create output directory
        Path outputDir = Paths.get(OUTPUT_DIR);
        Files.createDirectories(outputDir);
        
        // Import contacts from CSV
        List<Contact> contacts = Contact.fromCSV(CSV_FILE);
        System.out.println("Imported " + contacts.size() + " contacts from CSV");
        
        // Export to various formats
        Contact firstContact = contacts.get(0);
        System.out.println("First contact: " + firstContact);
        
        // Export to Excel
        ExcelBuilder excelBuilder = new ExcelBuilder();
        for (Contact contact : contacts) {
            contact.export(excelBuilder);
            excelBuilder.build();
        }
        excelBuilder.save(outputDir.resolve("contacts.xlsx").toString());
        System.out.println("Exported to Excel: " + outputDir.resolve("contacts.xlsx"));
        
        // Export to PDF
        PDFBuilder pdfBuilder = new PDFBuilder();
        for (Contact contact : contacts) {
            contact.export(pdfBuilder);
        }
        pdfBuilder.build().save(outputDir.resolve("contacts.pdf").toString());
        System.out.println("Exported to PDF: " + outputDir.resolve("contacts.pdf"));
        
        // Export to RTF
        RTFBuilder rtfBuilder = new RTFBuilder();
        for (Contact contact : contacts) {
            contact.export(rtfBuilder);
        }
        rtfBuilder.build().save(outputDir.resolve("contacts.rtf").toString());
        System.out.println("Exported to RTF: " + outputDir.resolve("contacts.rtf"));
        
        // Export to XML
        XMLBuilder xmlBuilder = new XMLBuilder();
        for (Contact contact : contacts) {
            contact.export(xmlBuilder);
        }
        xmlBuilder.build().save(outputDir.resolve("contacts.xml").toString());
        System.out.println("Exported to XML: " + outputDir.resolve("contacts.xml"));
        
        // Export to HTML
        HTMLBuilder htmlBuilder = new HTMLBuilder();
        for (Contact contact : contacts) {
            contact.export(htmlBuilder);
        }
        htmlBuilder.build().save(outputDir.resolve("contacts.html").toString());
        System.out.println("Exported to HTML: " + outputDir.resolve("contacts.html"));
        
        // Using convenience methods
        firstContact.exportAndSave(new HTMLBuilder(), outputDir.resolve("single-contact.html").toString());
        System.out.println("Exported single contact to HTML: " + outputDir.resolve("single-contact.html"));
    }
}
