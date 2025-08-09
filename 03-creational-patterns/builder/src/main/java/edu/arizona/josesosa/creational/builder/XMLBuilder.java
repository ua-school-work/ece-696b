package edu.arizona.josesosa.creational.builder;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Builder for creating XML files from Contact data using JAXB.
 */
public class XMLBuilder implements ContactBuilder {
    private String name;
    private String email;
    private String phone;
    private Contacts contacts;
    private boolean built = false;
    private String content;
    
    /**
     * Creates a new XML builder.
     */
    public XMLBuilder() {
        contacts = new Contacts();
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
                ContactData contact = new ContactData();
                contact.setName(name != null ? name : "");
                contact.setEmail(email != null ? email : "");
                contact.setPhone(phone != null ? phone : "");
                contacts.getContacts().add(contact);
            }
            
            try {
                JAXBContext context = JAXBContext.newInstance(Contacts.class);
                Marshaller marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                
                StringWriter writer = new StringWriter();
                marshaller.marshal(contacts, writer);
                content = writer.toString();
                
                built = true;
            } catch (JAXBException e) {
                throw new RuntimeException("Error building XML document: " + e.getMessage(), e);
            }
        }
        return this;
    }
    
    @Override
    public void save(String filePath) throws IOException {
        if (!built) {
            build();
        }
        
        try {
            JAXBContext context = JAXBContext.newInstance(Contacts.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            
            marshaller.marshal(contacts, new File(filePath));
        } catch (JAXBException e) {
            throw new IOException("Error saving XML document: " + e.getMessage(), e);
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
     * Adds a new contact to the XML document.
     * 
     * @param name the contact's name
     * @param email the contact's email
     * @param phone the contact's phone
     * @return this builder for method chaining
     */
    public XMLBuilder addContact(String name, String email, String phone) {
        ContactData contact = new ContactData();
        contact.setName(name != null ? name : "");
        contact.setEmail(email != null ? email : "");
        contact.setPhone(phone != null ? phone : "");
        contacts.getContacts().add(contact);
        return this;
    }
    
    /**
     * JAXB annotated class for a collection of contacts.
     */
    @XmlRootElement(name = "contacts")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Contacts {
        @XmlElement(name = "contact")
        private List<ContactData> contacts = new ArrayList<>();
        
        public List<ContactData> getContacts() {
            return contacts;
        }
        
        public void setContacts(List<ContactData> contacts) {
            this.contacts = contacts;
        }
    }
    
    /**
     * JAXB annotated class for contact data.
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ContactData {
        @XmlElement
        private String name;
        
        @XmlElement
        private String email;
        
        @XmlElement
        private String phone;
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public String getEmail() {
            return email;
        }
        
        public void setEmail(String email) {
            this.email = email;
        }
        
        public String getPhone() {
            return phone;
        }
        
        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}