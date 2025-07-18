package edu.arizona.josesosa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CarSearchTest {

    private CarSearch carSearch;

    @BeforeEach
    void setUp() {
        carSearch = new CarSearch();
    }

    @Test
    void testConstructor() {
        // Verify that the constructor initializes a Car instance
        assertNotNull(carSearch.getCar());
    }

    @Test
    void testGetCar() {
        // Verify that getCar returns the car instance
        Car car = carSearch.getCar();
        assertNotNull(car);
    }

    @Test
    void testSetCar() {
        // Create a new Car instance
        Car newCar = new Car();
        newCar.setBrand("Tesla");
        newCar.setYear(2023);
        
        // Set the car instance
        carSearch.setCar(newCar);
        
        // Verify that getCar returns the new car instance
        assertEquals(newCar, carSearch.getCar());
        assertEquals("Tesla", carSearch.getCar().getBrand());
        assertEquals(2023, carSearch.getCar().getYear());
    }

    @Test
    void testGetCriteria() {
        // This is a protected method, so I'll test it indirectly through the search method
        // Set some search criteria
        carSearch.getCar().setBrand("Ford");
        carSearch.getCar().setYear(2020);
        
        // Call search to trigger getCriteria
        List<Car> result = carSearch.search();
        
        // We can't directly verify the criteria, but we can verify that search returns a non-null result
        assertNotNull(result);
    }

    @Test
    void testGetAllowedOrder() {
        // Test that valid order values are accepted
        carSearch.setOrderBy("BRAND");
        assertEquals("BRAND", carSearch.getOrderBy());
        
        carSearch.setOrderBy("YEAR");
        assertEquals("YEAR", carSearch.getOrderBy());
        
        // Test that invalid order values are handled gracefully
        carSearch.setOrderBy("INVALID");
        assertNull(carSearch.getOrderBy());
    }

    @Test
    void testSetOrderBy() {
        // Test setting valid order values
        carSearch.setOrderBy("BRAND");
        assertEquals("BRAND", carSearch.getOrderBy());
        
        carSearch.setOrderBy("YEAR");
        assertEquals("YEAR", carSearch.getOrderBy());
        
        // Test setting invalid order value
        carSearch.setOrderBy("INVALID");
        assertNull(carSearch.getOrderBy());
        
        // Test setting null order value
        carSearch.setOrderBy(null);
        assertNull(carSearch.getOrderBy());
    }

    @Test
    void testSearch() {
        // Set search criteria
        carSearch.getCar().setBrand("%Ford%");
        carSearch.setOrderBy("YEAR");
        
        // Call search
        List<Car> result = carSearch.search();
        
        // Verify that search returns a non-null result
        assertNotNull(result);
        // The actual implementation returns an empty list
        assertTrue(result.isEmpty());
    }
}