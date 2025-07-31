package edu.arizona.josesosa;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {

    @Test
    void testBrandGetterAndSetter() {
        // Arrange
        Car car = new Car();
        String brand = "Toyota";
        
        // Act
        car.setBrand(brand);
        String retrievedBrand = car.getBrand();
        
        // Assert
        assertEquals(brand, retrievedBrand);
    }
    
    @Test
    void testYearGetterAndSetter() {
        // Arrange
        Car car = new Car();
        Integer year = 2023;
        
        // Act
        car.setYear(year);
        Integer retrievedYear = car.getYear();
        
        // Assert
        assertEquals(year, retrievedYear);
    }
    
    @Test
    void testNullValues() {
        // Arrange
        Car car = new Car();
        
        // Assert initial state
        assertNull(car.getBrand());
        assertNull(car.getYear());
        
        // Set null values explicitly
        car.setBrand(null);
        car.setYear(null);
        
        // Assert null values
        assertNull(car.getBrand());
        assertNull(car.getYear());
    }
    
    @Test
    void testMultipleUpdates() {
        // Arrange
        Car car = new Car();
        
        // First update
        car.setBrand("Honda");
        car.setYear(2020);
        
        // Assert first update
        assertEquals("Honda", car.getBrand());
        assertEquals(2020, car.getYear());
        
        // Second update
        car.setBrand("Ford");
        car.setYear(2022);
        
        // Assert a second update
        assertEquals("Ford", car.getBrand());
        assertEquals(2022, car.getYear());
    }
}