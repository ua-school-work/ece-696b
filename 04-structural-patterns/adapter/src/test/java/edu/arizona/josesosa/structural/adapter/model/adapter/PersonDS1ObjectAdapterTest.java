package edu.arizona.josesosa.structural.adapter.model.adapter;

import edu.arizona.josesosa.structural.adapter.model.IPerson;
import edu.arizona.josesosa.structural.adapter.model.remote.PersonDS1;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the PersonDS1ObjectAdapter class
 */
public class PersonDS1ObjectAdapterTest {

    private PersonDS1 personDS1;
    private IPerson adapter;

    @BeforeEach
    public void setUp() {
        // Create a PersonDS1 instance with test data
        personDS1 = new PersonDS1();
        personDS1.setId(1L);
        personDS1.setName("John Doe");
        personDS1.setAge(35.5);
        personDS1.setSalary(2000L);
        personDS1.setPartner(2L);

        // Create the adapter
        adapter = new PersonDS1ObjectAdapter(personDS1);
    }

    @Test
    public void testGetId() {
        assertEquals(1L, adapter.getId());
    }

    @Test
    public void testGetFirstName() {
        assertEquals("John", adapter.getfName());
    }

    @Test
    public void testGetLastName() {
        assertEquals("Doe", adapter.getlName());
    }

    @Test
    public void testGetAge() {
        assertEquals(35.5, adapter.getAge());
    }

    @Test
    public void testGetSalary() {
        assertEquals(2000L, adapter.getSalary());
    }

    @Test
    public void testGetMarried() {
        assertTrue(adapter.getMarried());
    }

    @Test
    public void testNameWithoutSpace() {
        PersonDS1 person = new PersonDS1();
        person.setName("SingleName");
        IPerson adapter = new PersonDS1ObjectAdapter(person);
        
        assertEquals("SingleName", adapter.getfName());
        assertEquals("", adapter.getlName());
    }

    @Test
    public void testNullName() {
        PersonDS1 person = new PersonDS1();
        person.setName(null);
        IPerson adapter = new PersonDS1ObjectAdapter(person);
        
        assertNull(adapter.getfName());
        assertEquals("", adapter.getlName());
    }

    @Test
    public void testNoPartner() {
        PersonDS1 person = new PersonDS1();
        person.setPartner(null);
        IPerson adapter = new PersonDS1ObjectAdapter(person);
        
        assertFalse(adapter.getMarried());
    }

    @Test
    public void testZeroPartner() {
        PersonDS1 person = new PersonDS1();
        person.setPartner(0L);
        IPerson adapter = new PersonDS1ObjectAdapter(person);
        
        assertFalse(adapter.getMarried());
    }
}