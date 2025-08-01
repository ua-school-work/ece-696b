package edu.arizona.josesosa.structural.adapter.model;

/**
 * Interface for Person objects to be used with the adapter pattern.
 * This interface defines the common methods that all Person objects should implement.
 */
public interface IPerson {
    Long getId();
    String getfName();
    String getlName();
    Double getAge();
    Long getSalary();
    Boolean getMarried();
}