package edu.arizona.josesosa;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the search functionality.
 * These tests focus on how the search components work together.
 */
class SearchIntegrationTest {

    @Test
    void testPersonSearchWithDifferentParameters() {
        // Create a PersonSearch instance
        PersonSearch personSearch = new PersonSearch();
        
        // Test with firstName parameter
        personSearch.getPerson().setFirstName("%John%");
        personSearch.setOrderBy("LASTNAME");
        List<Person> result1 = personSearch.search();
        assertNotNull(result1);
        
        // Test with lastName parameter
        personSearch = new PersonSearch(); // Reset
        personSearch.getPerson().setLastName("%Doe%");
        personSearch.setOrderBy("FIRSTNAME");
        List<Person> result2 = personSearch.search();
        assertNotNull(result2);
        
        // Test with age parameter
        personSearch = new PersonSearch(); // Reset
        personSearch.getPerson().setAge(30);
        personSearch.setOrderBy("AGE");
        List<Person> result3 = personSearch.search();
        assertNotNull(result3);
        
        // Test with multiple parameters
        personSearch = new PersonSearch(); // Reset
        personSearch.getPerson().setFirstName("%John%");
        personSearch.getPerson().setLastName("%Doe%");
        personSearch.getPerson().setAge(30);
        personSearch.setOrderBy("LASTNAME");
        List<Person> result4 = personSearch.search();
        assertNotNull(result4);
    }
    
    @Test
    void testCarSearchWithDifferentParameters() {
        // Create a CarSearch instance
        CarSearch carSearch = new CarSearch();
        
        // Test with brand parameter
        carSearch.getCar().setBrand("%Toyota%");
        carSearch.setOrderBy("YEAR");
        List<Car> result1 = carSearch.search();
        assertNotNull(result1);
        
        // Test with year parameter
        carSearch = new CarSearch(); // Reset
        carSearch.getCar().setYear(2023);
        carSearch.setOrderBy("BRAND");
        List<Car> result2 = carSearch.search();
        assertNotNull(result2);
        
        // Test with multiple parameters
        carSearch = new CarSearch(); // Reset
        carSearch.getCar().setBrand("%Ford%");
        carSearch.getCar().setYear(2022);
        carSearch.setOrderBy("BRAND");
        List<Car> result3 = carSearch.search();
        assertNotNull(result3);
    }
    
    @Test
    void testSearchWithEdgeCases() {
        // Test with null values in Person
        PersonSearch personSearch = new PersonSearch();
        personSearch.getPerson().setFirstName(null);
        personSearch.getPerson().setLastName(null);
        personSearch.getPerson().setAge(null);
        personSearch.setOrderBy(null);
        List<Person> result1 = personSearch.search();
        assertNotNull(result1);
        
        // Test with null values in Car
        CarSearch carSearch = new CarSearch();
        carSearch.getCar().setBrand(null);
        carSearch.getCar().setYear(null);
        carSearch.setOrderBy(null);
        List<Car> result2 = carSearch.search();
        assertNotNull(result2);
        
        // Test with empty string values in Person
        personSearch = new PersonSearch();
        personSearch.getPerson().setFirstName("");
        personSearch.getPerson().setLastName("");
        personSearch.setOrderBy("FIRSTNAME");
        List<Person> result3 = personSearch.search();
        assertNotNull(result3);
        
        // Test with empty string values in Car
        carSearch = new CarSearch();
        carSearch.getCar().setBrand("");
        carSearch.setOrderBy("BRAND");
        List<Car> result4 = carSearch.search();
        assertNotNull(result4);
        
        // Test with special characters in search parameters
        personSearch = new PersonSearch();
        personSearch.getPerson().setFirstName("%J%o%h%n%");
        personSearch.setOrderBy("FIRSTNAME");
        List<Person> result5 = personSearch.search();
        assertNotNull(result5);
        
        carSearch = new CarSearch();
        carSearch.getCar().setBrand("%F%o%r%d%");
        carSearch.setOrderBy("BRAND");
        List<Car> result6 = carSearch.search();
        assertNotNull(result6);
    }
    
    @Test
    void testSearchWithInvalidOrderBy() {
        // Test with invalid orderBy in PersonSearch
        PersonSearch personSearch = new PersonSearch();
        personSearch.getPerson().setFirstName("%John%");
        personSearch.setOrderBy("INVALID_ORDER");
        List<Person> result1 = personSearch.search();
        assertNotNull(result1);
        
        // Test with invalid orderBy in CarSearch
        CarSearch carSearch = new CarSearch();
        carSearch.getCar().setBrand("%Toyota%");
        carSearch.setOrderBy("INVALID_ORDER");
        List<Car> result2 = carSearch.search();
        assertNotNull(result2);
    }
    
    @Test
    void testMainScenario() {
        // Test the scenario from the Main class
        PersonSearch personSearch = new PersonSearch();
        personSearch.setOrderBy("FIRSTNAME");
        personSearch.getPerson().setLastName("%Bob%");
        List<Person> personResult = personSearch.search();
        assertNotNull(personResult);
        
        CarSearch carSearch = new CarSearch();
        carSearch.setOrderBy("YEAR");
        carSearch.getCar().setBrand("%Ford%");
        List<Car> carResult = carSearch.search();
        assertNotNull(carResult);
    }
}