package edu.arizona.josesosa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BiMapTest {

    private BiMap<String, Integer> biMap;

    @BeforeEach
    void setUp() {
        biMap = new BiMap<>();
        biMap.put("one", 1);
        biMap.put("two", 2);
        biMap.put("three", 3);
    }

    @Test
    void testPut() {
        // Test adding a new key-value pair
        biMap.put("four", 4);
        assertEquals(4, biMap.getValue("four"));
        assertEquals("four", biMap.getKey(4));
        
        // Test overwriting an existing key
        biMap.put("one", 10);
        assertEquals(10, biMap.getValue("one"));
        assertEquals("one", biMap.getKey(10));

        assertEquals("one", biMap.getKey(1));
    }

    @Test
    void testGetValue() {
        // Test getting values for existing keys
        assertEquals(1, biMap.getValue("one"));
        assertEquals(2, biMap.getValue("two"));
        assertEquals(3, biMap.getValue("three"));
        
        // Test getting value for non-existent key
        assertNull(biMap.getValue("four"));
        assertNull(biMap.getValue(null));
    }

    @Test
    void testGetKey() {
        // Test getting keys for existing values
        assertEquals("one", biMap.getKey(1));
        assertEquals("two", biMap.getKey(2));
        assertEquals("three", biMap.getKey(3));
        
        // Test getting key for non-existent value
        assertNull(biMap.getKey(4));
        assertNull(biMap.getKey(null));
    }

    @Test
    void testBidirectionalMapping() {
        // Test that the mapping works in both directions
        String key = "test";
        Integer value = 42;
        
        biMap.put(key, value);
        
        assertEquals(value, biMap.getValue(key));
        assertEquals(key, biMap.getKey(value));
    }
    
    @Test
    void testWithNullValues() {
        // Test putting null values
        biMap.put("nullValue", null);
        assertNull(biMap.getValue("nullValue"));
        
        // Test putting null keys
        biMap.put(null, 999);
        assertEquals(999, biMap.getValue(null));
        assertNull(biMap.getKey(999));
    }
}