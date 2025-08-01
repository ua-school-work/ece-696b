package edu.arizona.josesosa.structural.adapter.business;

import edu.arizona.josesosa.structural.adapter.model.Person;
import edu.arizona.josesosa.structural.adapter.model.adapter.PersonDS2ClassAdapter;
import edu.arizona.josesosa.structural.adapter.model.remote.PersonDS1;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the RemotePersonService class
 */
public class RemotePersonServiceTest {

    private RemotePersonService service;

    @BeforeEach
    public void setUp() {
        service = new RemotePersonService();
    }

    @Test
    public void testGetPersonListDS1() {
        // This test verifies that PersonDS1 objects can be loaded from XML
        List<PersonDS1> personDS1List = service.getPersonListDS1();
        
        // Verify the list is not null and contains elements
        assertNotNull(personDS1List);
        assertFalse(personDS1List.isEmpty());
        
        // Verify the first person's data
        PersonDS1 firstPerson = personDS1List.get(0);
        assertEquals(1L, firstPerson.getId());
        assertEquals("Bob Foo", firstPerson.getName());
        assertEquals(33.0, firstPerson.getAge());
        assertEquals(1000L, firstPerson.getSalary());
        assertEquals(2L, firstPerson.getPartner());
    }

    @Test
    public void testGetPersonListDS2() {
        // This test verifies that PersonDS2ClassAdapter objects can be loaded from XML
        List<PersonDS2ClassAdapter> personDS2List = service.getPersonListDS2();
        
        // Verify the list is not null and contains elements
        assertNotNull(personDS2List);
        assertFalse(personDS2List.isEmpty());
        
        // Verify the first person's data
        PersonDS2ClassAdapter firstPerson = personDS2List.get(0);
        assertEquals(1L, firstPerson.getId());
        assertEquals("Bauer", firstPerson.getfName());
        assertEquals("Chris", firstPerson.getlName());
        assertTrue(firstPerson.getMarried());
        
        // Verify adapter functionality
        // Yearly salary should be converted to monthly
        assertEquals(1001L, firstPerson.getSalary());
    }

    @Test
    public void testGetPersonList() {
        // This test verifies that both data sources are combined
        List<Person> personList = service.getPersonList();
        
        // Verify the list is not null and contains elements from both sources
        assertNotNull(personList);
        assertTrue(personList.size() >= 4); // At least 2 from each source
        
        // Verify that the list contains persons with expected IDs
        boolean foundId1 = false;
        boolean foundId2 = false;
        
        for (Person person : personList) {
            if (person.getId() == 1L) {
                foundId1 = true;
            } else if (person.getId() == 2L) {
                foundId2 = true;
            }
        }
        
        assertTrue(foundId1, "Person with ID 1 should be in the list");
        assertTrue(foundId2, "Person with ID 2 should be in the list");
    }

    @Test
    public void testConvertToPerson() {
        // This test verifies the private convertToPerson method indirectly
        // by checking that the Person objects in the list have the correct properties
        List<Person> personList = service.getPersonList();
        
        // Find a person with ID 1 from the first data source
        Person personFromDS1 = findPersonById(personList, 1L);
        assertNotNull(personFromDS1);
        
        // Verify properties were correctly converted
        assertEquals("Bob", personFromDS1.getfName());
        assertEquals("Foo", personFromDS1.getlName());
        assertEquals(33.0, personFromDS1.getAge());
        assertEquals(1000L, personFromDS1.getSalary());
        assertTrue(personFromDS1.getMarried());
    }
    
    private Person findPersonById(List<Person> personList, Long id) {
        for (Person person : personList) {
            if (person.getId().equals(id)) {
                return person;
            }
        }
        return null;
    }
}