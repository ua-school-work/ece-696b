package edu.arizona.josesosa.creational.builder;

import java.io.IOException;

/**
 * Common interface for all Contact builders.
 * Extends the Contact.Exporter interface and adds methods for building and saving the output.
 */
public interface ContactBuilder extends Contact.Exporter {
    
    /**
     * Builds the final output representation.
     * 
     * @return the builder instance for method chaining
     */
    ContactBuilder build();
    
    /**
     * Saves the built output to a file.
     * 
     * @param filePath path where the file should be saved
     * @throws IOException if an I/O error occurs
     */
    void save(String filePath) throws IOException;
    
    /**
     * Gets the content as a string representation.
     * 
     * @return string representation of the built content
     */
    String getContent();
}