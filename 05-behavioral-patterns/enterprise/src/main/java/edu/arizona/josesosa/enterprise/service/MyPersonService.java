package edu.arizona.josesosa.enterprise.service;

import edu.arizona.josesosa.enterprise.dao.Repository;
import edu.arizona.josesosa.enterprise.model.Person;

import java.util.List;
import java.util.Optional;

/**
 * PersonService layer delegating persistence to PersonRepository and owning validation
 */
public class MyPersonService implements PersonService {
    private final Repository<Person> dao;

    /**
     * Create a service backed by a repository abstraction.
     * @param dao repository for persistence
     */
    public MyPersonService(Repository<Person> dao) {
        this.dao = dao;
    }

    /** {@inheritDoc} */
    public synchronized List<Person> findAll() {
        return dao.findAll();
    }

    /** {@inheritDoc} */
    public synchronized Optional<Person> findById(String id) {
        return dao.findById(id);
    }

    /** {@inheritDoc} */
    public synchronized Person add(String name, String email, Integer age) {
        validateNameEmail(name, email);
        Person p = new Person(name, email, age);
        return dao.save(p);
    }

    /** {@inheritDoc} */
    public synchronized void update(String id, String name, String email, Integer age) {
        if (isBlank(id)) return;
        validateNameEmail(name, email);
        Person updated = new Person(name, email, age);
        try {
            java.lang.reflect.Field f = Person.class.getDeclaredField("id");
            f.setAccessible(true);
            f.set(updated, id);
        } catch (Exception ignored) {
        }
        dao.update(updated);
    }

    /** {@inheritDoc} */
    public synchronized void delete(String id) {
        if (!isBlank(id)) dao.delete(id);
    }

    /** Validate required fields and throw IllegalArgumentException if missing. */
    private static void validateNameEmail(String name, String email) {
        if (isBlank(name) || isBlank(email)) {
            throw new IllegalArgumentException("Name and email are required");
        }
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
