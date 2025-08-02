package edu.arizona.josesosa.structural.proxy.remote;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyInterfaceImplTest {

    private MyInterfaceImpl myInterface;

    @BeforeEach
    void setUp() {
        myInterface = new MyInterfaceImpl();
    }

    @Test
    void testMethodWithoutParameters() {
        // When
        String result = myInterface.method();
        
        // Then
        assertEquals("MyImpl", result, "Method without parameters should return 'MyImpl'");
    }

    @Test
    void testMethodWithParameter() {
        // Given
        String parameter = "TestParam";
        
        // When
        String result = myInterface.method(parameter);
        
        // Then
        assertEquals("MyImpl(TestParam)", result, 
                "Method with parameter should return 'MyImpl(parameter)'");
    }

    @Test
    void testMethodWithEmptyParameter() {
        // Given
        String parameter = "";
        
        // When
        String result = myInterface.method(parameter);
        
        // Then
        assertEquals("MyImpl()", result, 
                "Method with empty parameter should return 'MyImpl()'");
    }
}