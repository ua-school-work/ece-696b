package edu.arizona.josesosa;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    @Test
    void testFirstNameGetterAndSetter() {
        // Arrange
        Person person = new Person();
        String firstName = "John";
        
        // Act
        person.setFirstName(firstName);
        String retrievedFirstName = person.getFirstName();
        
        // Assert
        assertEquals(firstName, retrievedFirstName);
    }
    
    @Test
    void testLastNameGetterAndSetter() {
        // Arrange
        Person person = new Person();
        String lastName = "Doe";
        
        // Act
        person.setLastName(lastName);
        String retrievedLastName = person.getLastName();
        
        // Assert
        assertEquals(lastName, retrievedLastName);
    }
    
    @Test
    void testAgeGetterAndSetter() {
        // Arrange
        Person person = new Person();
        Integer age = 30;
        
        // Act
        person.setAge(age);
        Integer retrievedAge = person.getAge();
        
        // Assert
        assertEquals(age, retrievedAge);
    }
    
    @Test
    void testNullValues() {
        // Arrange
        Person person = new Person();
        
        // Assert initial state
        assertNull(person.getFirstName());
        assertNull(person.getLastName());
        assertNull(person.getAge());
        
        // Set null values explicitly
        person.setFirstName(null);
        person.setLastName(null);
        person.setAge(null);
        
        // Assert null values
        assertNull(person.getFirstName());
        assertNull(person.getLastName());
        assertNull(person.getAge());
    }
    
    @Test
    void testMultipleUpdates() {
        // Arrange
        Person person = new Person();
        
        // First update
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setAge(30);
        
        // Assert first update
        assertEquals("John", person.getFirstName());
        assertEquals("Doe", person.getLastName());
        assertEquals(30, person.getAge());
        
        // Second update
        person.setFirstName("Jane");
        person.setLastName("Smith");
        person.setAge(25);
        
        // Assert second update
        assertEquals("Jane", person.getFirstName());
        assertEquals("Smith", person.getLastName());
        assertEquals(25, person.getAge());
    }
}