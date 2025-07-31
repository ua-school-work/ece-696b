package edu.arizona.josesosa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonSearchTest {

    private PersonSearch personSearch;

    @BeforeEach
    void setUp() {
        personSearch = new PersonSearch();
    }

    @Test
    void testConstructor() {
        // Verify that the constructor initializes a Person instance
        assertNotNull(personSearch.getPerson());
    }

    @Test
    void testGetPerson() {
        // Verify that getPerson returns the person instance
        Person person = personSearch.getPerson();
        assertNotNull(person);
    }

    @Test
    void testSetPerson() {
        // Create a new Person instance
        Person newPerson = new Person();
        newPerson.setFirstName("John");
        newPerson.setLastName("Doe");
        newPerson.setAge(30);
        
        // Set the person instance
        personSearch.setPerson(newPerson);
        
        // Verify that getPerson returns the new person instance
        assertEquals(newPerson, personSearch.getPerson());
        assertEquals("John", personSearch.getPerson().getFirstName());
        assertEquals("Doe", personSearch.getPerson().getLastName());
        assertEquals(30, personSearch.getPerson().getAge());
    }

    @Test
    void testGetCriteria() {
        // This is a protected method, so I'll test it indirectly through the search method
        // Set some search criteria
        personSearch.getPerson().setFirstName("John");
        personSearch.getPerson().setLastName("Doe");
        personSearch.getPerson().setAge(30);
        
        // Call search to trigger getCriteria
        List<Person> result = personSearch.search();
        
        // I can't directly verify the criteria, but we can verify that search returns a non-null result
        assertNotNull(result);
    }

    @Test
    void testGetAllowedOrder() {
        // Test that valid order values are accepted
        personSearch.setOrderBy("FIRSTNAME");
        assertEquals("FIRSTNAME", personSearch.getOrderBy());
        
        personSearch.setOrderBy("LASTNAME");
        assertEquals("LASTNAME", personSearch.getOrderBy());
        
        personSearch.setOrderBy("AGE");
        assertEquals("AGE", personSearch.getOrderBy());
        
        // Test that invalid order values are handled gracefully
        personSearch.setOrderBy("INVALID");
        assertNull(personSearch.getOrderBy());
    }

    @Test
    void testSetOrderBy() {
        // Test setting valid order values
        personSearch.setOrderBy("FIRSTNAME");
        assertEquals("FIRSTNAME", personSearch.getOrderBy());
        
        personSearch.setOrderBy("LASTNAME");
        assertEquals("LASTNAME", personSearch.getOrderBy());
        
        personSearch.setOrderBy("AGE");
        assertEquals("AGE", personSearch.getOrderBy());
        
        // Test setting invalid order value
        personSearch.setOrderBy("INVALID");
        assertNull(personSearch.getOrderBy());
        
        // Test setting null order value
        personSearch.setOrderBy(null);
        assertNull(personSearch.getOrderBy());
    }

    @Test
    void testSearch() {
        // Set search criteria
        personSearch.getPerson().setLastName("%Bob%");
        personSearch.setOrderBy("FIRSTNAME");
        
        // Call search
        List<Person> result = personSearch.search();
        
        // Verify that search returns a non-null result
        assertNotNull(result);
        // The actual implementation returns an empty list
        assertTrue(result.isEmpty());
    }
}