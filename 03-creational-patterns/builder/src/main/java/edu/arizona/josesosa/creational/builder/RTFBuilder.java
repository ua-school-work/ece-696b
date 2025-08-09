package edu.arizona.josesosa.creational.builder;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Builder for creating RTF files from Contact data using Apache FOP.
 */
public class RTFBuilder implements ContactBuilder {
    private String name;
    private String email;
    private String phone;
    private List<Contact> contacts;
    private boolean built = false;
    private String content;
    
    /**
     * Creates a new RTF builder.
     */
    public RTFBuilder() {
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
            
            // Create XSL-FO content
            StringBuilder fo = new StringBuilder();
            fo.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            fo.append("<fo:root xmlns:fo=\"http://www.w3.org/1999/XSL/Format\">\n");
            fo.append("  <fo:layout-master-set>\n");
            fo.append("    <fo:simple-page-master master-name=\"A4\" page-height=\"297mm\" page-width=\"210mm\"");
            fo.append(" margin-top=\"1cm\" margin-bottom=\"1cm\" margin-left=\"2cm\" margin-right=\"2cm\">\n");
            fo.append("      <fo:region-body margin-top=\"1cm\"/>\n");
            fo.append("    </fo:simple-page-master>\n");
            fo.append("  </fo:layout-master-set>\n");
            fo.append("  <fo:page-sequence master-reference=\"A4\">\n");
            fo.append("    <fo:flow flow-name=\"xsl-region-body\">\n");
            fo.append("      <fo:block font-size=\"16pt\" font-weight=\"bold\" space-after=\"5mm\">Contact Information</fo:block>\n");
            
            // Table header
            fo.append("      <fo:table border-collapse=\"separate\" width=\"100%\" table-layout=\"fixed\" space-after=\"5mm\">\n");
            fo.append("        <fo:table-column column-width=\"proportional-column-width(1)\"/>\n");
            fo.append("        <fo:table-column column-width=\"proportional-column-width(1)\"/>\n");
            fo.append("        <fo:table-column column-width=\"proportional-column-width(1)\"/>\n");
            fo.append("        <fo:table-header>\n");
            fo.append("          <fo:table-row font-weight=\"bold\">\n");
            fo.append("            <fo:table-cell border=\"1pt solid black\" padding=\"2pt\">\n");
            fo.append("              <fo:block>Name</fo:block>\n");
            fo.append("            </fo:table-cell>\n");
            fo.append("            <fo:table-cell border=\"1pt solid black\" padding=\"2pt\">\n");
            fo.append("              <fo:block>Email</fo:block>\n");
            fo.append("            </fo:table-cell>\n");
            fo.append("            <fo:table-cell border=\"1pt solid black\" padding=\"2pt\">\n");
            fo.append("              <fo:block>Phone</fo:block>\n");
            fo.append("            </fo:table-cell>\n");
            fo.append("          </fo:table-row>\n");
            fo.append("        </fo:table-header>\n");
            
            // Table body
            fo.append("        <fo:table-body>\n");
            for (Contact contact : contacts) {
                fo.append("          <fo:table-row>\n");
                fo.append("            <fo:table-cell border=\"1pt solid black\" padding=\"2pt\">\n");
                fo.append("              <fo:block>").append(contact.name != null ? contact.name : "").append("</fo:block>\n");
                fo.append("            </fo:table-cell>\n");
                fo.append("            <fo:table-cell border=\"1pt solid black\" padding=\"2pt\">\n");
                fo.append("              <fo:block>").append(contact.email != null ? contact.email : "").append("</fo:block>\n");
                fo.append("            </fo:table-cell>\n");
                fo.append("            <fo:table-cell border=\"1pt solid black\" padding=\"2pt\">\n");
                fo.append("              <fo:block>").append(contact.phone != null ? contact.phone : "").append("</fo:block>\n");
                fo.append("            </fo:table-cell>\n");
                fo.append("          </fo:table-row>\n");
            }
            fo.append("        </fo:table-body>\n");
            fo.append("      </fo:table>\n");
            fo.append("    </fo:flow>\n");
            fo.append("  </fo:page-sequence>\n");
            fo.append("</fo:root>");
            
            content = fo.toString();
            built = true;
        }
        return this;
    }
    
    @Override
    public void save(String filePath) throws IOException {
        if (!built) {
            build();
        }
        
        try {
            // Setup FOP factory
            FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
            
            // Setup output
            try (FileOutputStream out = new FileOutputStream(filePath)) {
                // Setup FOP with RTF output format
                Fop fop = fopFactory.newFop(MimeConstants.MIME_RTF, out);
                
                // Setup XSLT
                TransformerFactory factory = TransformerFactory.newInstance();
                Transformer transformer = factory.newTransformer();
                
                // Setup input
                StreamSource source = new StreamSource(new StringReader(content));
                
                // Setup output
                Result result = new SAXResult(fop.getDefaultHandler());
                
                // Transform XSL-FO to RTF
                transformer.transform(source, result);
            }
        } catch (FOPException | TransformerException e) {
            throw new IOException("Error creating RTF document: " + e.getMessage(), e);
        }
    }
    
    @Override
    public String getContent() {
        if (!built) {
            build();
        }
        return content;
    }
    
    /**
     * Adds a new contact to the RTF document.
     * 
     * @param name the contact's name
     * @param email the contact's email
     * @param phone the contact's phone
     * @return this builder for method chaining
     */
    public RTFBuilder addContact(String name, String email, String phone) {
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