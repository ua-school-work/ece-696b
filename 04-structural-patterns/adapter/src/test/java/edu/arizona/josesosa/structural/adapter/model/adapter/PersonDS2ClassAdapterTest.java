package edu.arizona.josesosa.structural.adapter.model.adapter;

import edu.arizona.josesosa.structural.adapter.model.IPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the PersonDS2ClassAdapter class
 */
public class PersonDS2ClassAdapterTest {

    private PersonDS2ClassAdapter adapter;
    private Date birthDate;
    private int expectedAge;

    @BeforeEach
    public void setUp() {
        // Create the adapter
        adapter = new PersonDS2ClassAdapter();
        
        // Set test data
        adapter.setId(2L);
        adapter.setfName("Jane");
        adapter.setlName("Smith");
        adapter.setMarried(true);
        adapter.setSalaryYear(60000L);
        
        // Calculate a birth date that will result in a predictable age
        Calendar calendar = Calendar.getInstance();
        expectedAge = 30;
        calendar.add(Calendar.YEAR, -expectedAge);
        birthDate = calendar.getTime();
        adapter.setBorn(birthDate);
    }

    @Test
    public void testGetId() {
        assertEquals(2L, adapter.getId());
    }

    @Test
    public void testGetFirstName() {
        assertEquals("Jane", adapter.getfName());
    }

    @Test
    public void testGetLastName() {
        assertEquals("Smith", adapter.getlName());
    }

    @Test
    public void testGetAge() {
        // The age calculation should return the expected age
        assertEquals((double) expectedAge, adapter.getAge());
    }

    @Test
    public void testGetSalary() {
        // Yearly salary should be converted to monthly (divided by 12)
        assertEquals(5000L, adapter.getSalary());
    }

    @Test
    public void testGetMarried() {
        assertTrue(adapter.getMarried());
    }

    @Test
    public void testNullBirthDate() {
        PersonDS2ClassAdapter adapter = new PersonDS2ClassAdapter();
        adapter.setBorn(null);
        
        assertEquals(0.0, adapter.getAge());
    }

    @Test
    public void testNullSalary() {
        PersonDS2ClassAdapter adapter = new PersonDS2ClassAdapter();
        adapter.setSalaryYear(null);
        
        assertEquals(0L, adapter.getSalary());
    }

    @Test
    public void testBirthdayNotYetOccurredThisYear() {
        // Create a birth date where the birthday hasn't occurred yet this year
        Calendar today = Calendar.getInstance();
        Calendar birthCal = Calendar.getInstance();
        
        // Set birth date to be 25 years ago
        birthCal.setTime(today.getTime());
        birthCal.add(Calendar.YEAR, -25);
        
        // If today is January, set birth month to December to ensure birthday hasn't occurred
        if (today.get(Calendar.MONTH) == Calendar.JANUARY) {
            birthCal.set(Calendar.MONTH, Calendar.DECEMBER);
        } else {
            // Otherwise set birth month to one month after current month
            birthCal.set(Calendar.MONTH, today.get(Calendar.MONTH) + 1);
        }
        
        PersonDS2ClassAdapter adapter = new PersonDS2ClassAdapter();
        adapter.setBorn(birthCal.getTime());
        
        // Age should be 24 (25 - 1) because birthday hasn't occurred yet this year
        assertEquals(24.0, adapter.getAge());
    }
}