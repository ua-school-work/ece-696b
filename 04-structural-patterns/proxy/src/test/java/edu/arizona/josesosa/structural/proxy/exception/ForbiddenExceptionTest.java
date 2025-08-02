package edu.arizona.josesosa.structural.proxy.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ForbiddenExceptionTest {

    @Test
    void testConstructorWithMessage() {
        // Given
        String message = "Custom message";
        
        // When
        ForbiddenException exception = new ForbiddenException(message);
        
        // Then
        assertEquals(message, exception.getMessage(), 
                "Exception should contain the message provided in constructor");
    }

    @Test
    void testDefaultConstructor() {
        // When
        ForbiddenException exception = new ForbiddenException();
        
        // Then
        assertEquals("Censored", exception.getMessage(), 
                "Default constructor should set message to 'Censored'");
    }
}