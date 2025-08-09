package edu.arizona.josesosa.creational.builder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Contact builders.
 */
class ContactBuilderTest {
    
    private static final String CSV_FILE = "src/test/resources/contacts.csv";
    private List<Contact> contacts;
    
    @TempDir
    Path tempDir;
    
    @BeforeEach
    void setUp() {
        contacts = Contact.fromCSV(CSV_FILE);
        assertFalse(contacts.isEmpty(), "Contacts should not be empty");
        assertEquals(5, contacts.size(), "Should have 5 contacts");
    }
    
    @Test
    void testCSVImporter() {
        // Verify the first contact
        Contact firstContact = contacts.get(0);
        assertNotNull(firstContact);
        assertEquals("John Doe john.doe@example.com 555-123-4567", firstContact.toString());
        
        // Verify the last contact
        Contact lastContact = contacts.get(contacts.size() - 1);
        assertNotNull(lastContact);
        assertEquals("Charlie Brown charlie.brown@example.com 555-321-6547", lastContact.toString());
    }
    
    @Test
    void testExcelBuilder() throws IOException {
        // Create output file
        Path excelFile = tempDir.resolve("contacts.xlsx");
        
        // Export first contact
        ExcelBuilder excelBuilder = new ExcelBuilder();
        contacts.get(0).export(excelBuilder);
        
        // Add remaining contacts
        for (int i = 1; i < contacts.size(); i++) {
            Contact contact = contacts.get(i);
            excelBuilder.addContact(
                contact.toString().split(" ")[0] + " " + contact.toString().split(" ")[1], 
                contact.toString().split(" ")[2], 
                contact.toString().split(" ")[3]
            );
        }
        
        // Build and save
        excelBuilder.build().save(excelFile.toString());
        
        // Verify file exists and is not empty
        assertTrue(Files.exists(excelFile));
        assertTrue(Files.size(excelFile) > 0);
        
        System.out.println("Excel file created at: " + excelFile.toAbsolutePath());
    }
    
    @Test
    void testPDFBuilder() throws IOException {
        // Create output file
        Path pdfFile = tempDir.resolve("contacts.pdf");
        
        // Export first contact
        PDFBuilder pdfBuilder = new PDFBuilder();
        contacts.get(0).export(pdfBuilder);
        
        // Add remaining contacts
        for (int i = 1; i < contacts.size(); i++) {
            Contact contact = contacts.get(i);
            pdfBuilder.addContact(
                contact.toString().split(" ")[0] + " " + contact.toString().split(" ")[1], 
                contact.toString().split(" ")[2], 
                contact.toString().split(" ")[3]
            );
        }
        
        // Build and save
        pdfBuilder.build().save(pdfFile.toString());
        
        // Verify file exists and is not empty
        assertTrue(Files.exists(pdfFile));
        assertTrue(Files.size(pdfFile) > 0);
        
        System.out.println("PDF file created at: " + pdfFile.toAbsolutePath());
    }
    
    @Test
    void testRTFBuilder() throws IOException {
        // Create output file
        Path rtfFile = tempDir.resolve("contacts.rtf");
        
        // Export first contact
        RTFBuilder rtfBuilder = new RTFBuilder();
        contacts.get(0).export(rtfBuilder);
        
        // Add remaining contacts
        for (int i = 1; i < contacts.size(); i++) {
            Contact contact = contacts.get(i);
            rtfBuilder.addContact(
                contact.toString().split(" ")[0] + " " + contact.toString().split(" ")[1], 
                contact.toString().split(" ")[2], 
                contact.toString().split(" ")[3]
            );
        }
        
        // Build and save
        rtfBuilder.build().save(rtfFile.toString());
        
        // Verify file exists and is not empty
        assertTrue(Files.exists(rtfFile));
        assertTrue(Files.size(rtfFile) > 0);
        
        System.out.println("RTF file created at: " + rtfFile.toAbsolutePath());
    }
    
    @Test
    void testXMLBuilder() throws IOException {
        // Create output file
        Path xmlFile = tempDir.resolve("contacts.xml");
        
        // Export first contact
        XMLBuilder xmlBuilder = new XMLBuilder();
        contacts.get(0).export(xmlBuilder);
        
        // Add remaining contacts
        for (int i = 1; i < contacts.size(); i++) {
            Contact contact = contacts.get(i);
            xmlBuilder.addContact(
                contact.toString().split(" ")[0] + " " + contact.toString().split(" ")[1], 
                contact.toString().split(" ")[2], 
                contact.toString().split(" ")[3]
            );
        }
        
        // Build and save
        xmlBuilder.build().save(xmlFile.toString());
        
        // Verify file exists and is not empty
        assertTrue(Files.exists(xmlFile));
        assertTrue(Files.size(xmlFile) > 0);
        
        // Verify content
        String content = Files.readString(xmlFile);
        assertTrue(content.contains("<name>John Doe</name>"));
        assertTrue(content.contains("<email>john.doe@example.com</email>"));
        
        System.out.println("XML file created at: " + xmlFile.toAbsolutePath());
        
        // Save as oracle file
        Path oracleDir = Paths.get("src/test/resources/oracle");
        Files.createDirectories(oracleDir);
        Files.copy(xmlFile, oracleDir.resolve("contacts.xml"), 
                  java.nio.file.StandardCopyOption.REPLACE_EXISTING);
    }
    
    @Test
    void testHTMLBuilder() throws IOException {
        // Create output file
        Path htmlFile = tempDir.resolve("contacts.html");
        
        // Export first contact
        HTMLBuilder htmlBuilder = new HTMLBuilder();
        contacts.get(0).export(htmlBuilder);
        
        // Add remaining contacts
        for (int i = 1; i < contacts.size(); i++) {
            Contact contact = contacts.get(i);
            htmlBuilder.addContact(
                contact.toString().split(" ")[0] + " " + contact.toString().split(" ")[1], 
                contact.toString().split(" ")[2], 
                contact.toString().split(" ")[3]
            );
        }
        
        // Build and save
        htmlBuilder.build().save(htmlFile.toString());
        
        // Verify file exists and is not empty
        assertTrue(Files.exists(htmlFile));
        assertTrue(Files.size(htmlFile) > 0);
        
        // Verify content
        String content = Files.readString(htmlFile);
        assertTrue(content.contains("John Doe"));
        assertTrue(content.contains("john.doe@example.com"));
        
        System.out.println("HTML file created at: " + htmlFile.toAbsolutePath());
        
        // Save as oracle file
        Path oracleDir = Paths.get("src/test/resources/oracle");
        Files.createDirectories(oracleDir);
        Files.copy(htmlFile, oracleDir.resolve("contacts.html"), 
                  java.nio.file.StandardCopyOption.REPLACE_EXISTING);
    }
    
    @Test
    void testContactExportAndBuild() throws IOException {
        Contact contact = contacts.get(0);
        
        // Test exportAndBuild
        HTMLBuilder htmlBuilder = new HTMLBuilder();
        ContactBuilder builtBuilder = contact.exportAndBuild(htmlBuilder);
        assertNotNull(builtBuilder);
        
        // Test exportAndSave
        Path htmlFile = tempDir.resolve("single-contact.html");
        contact.exportAndSave(new HTMLBuilder(), htmlFile.toString());
        
        // Verify file exists and is not empty
        assertTrue(Files.exists(htmlFile));
        assertTrue(Files.size(htmlFile) > 0);
    }
}