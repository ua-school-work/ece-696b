package edu.arizona.josesosa.structural.proxy;

import edu.arizona.josesosa.structural.proxy.exception.ForbiddenException;
import edu.arizona.josesosa.structural.proxy.remote.MyInterface;
import edu.arizona.josesosa.structural.proxy.remote.MyInterfaceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

import static org.junit.jupiter.api.Assertions.*;

class ProxyClassTest {

    private MyInterface proxiedInterface;
    private MyInterfaceImpl realImplementation;

    @BeforeEach
    void setUp() {
        realImplementation = new MyInterfaceImpl();
        proxiedInterface = (MyInterface) Proxy.newProxyInstance(
                MyInterface.class.getClassLoader(),
                new Class[]{MyInterface.class},
                new ProxyClass(realImplementation)
        );
    }

    @Test
    void testMethodWithoutParameters() {
        // When
        String result = proxiedInterface.method();
        
        // Then
        assertEquals("MyImpl", result, "Proxied method without parameters should return the same result as the real implementation");
    }

    @Test
    void testMethodWithAllowedParameter() {
        // Given
        String parameter = "Allowed";
        
        // When
        String result = proxiedInterface.method(parameter);
        
        // Then
        assertEquals("MyImpl(Allowed)", result, 
                "Proxied method with allowed parameter should return the same result as the real implementation");
    }

    @Test
    void testMethodWithSecretParameter() {
        // Given
        String parameter = "This is a Secret String";
        
        // Then
        assertThrows(ForbiddenException.class, () -> {
            // When
            proxiedInterface.method(parameter);
        }, "Proxied method with 'secret' in parameter should throw ForbiddenException");
    }

    @Test
    void testMethodWithSecretParameterCaseInsensitive() {
        // Given
        String parameter = "This is a SECRET string";
        
        // Then
        assertThrows(ForbiddenException.class, () -> {
            // When
            proxiedInterface.method(parameter);
        }, "Proxied method with 'SECRET' in parameter should throw ForbiddenException (case insensitive)");
    }
}