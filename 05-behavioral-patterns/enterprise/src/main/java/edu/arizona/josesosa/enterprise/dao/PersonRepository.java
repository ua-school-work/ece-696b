package edu.arizona.josesosa.enterprise.dao;

import edu.arizona.josesosa.enterprise.model.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * In-memory DAO for Person entities.
 */
public class PersonRepository implements Repository<Person> {
    private final List<Person> people = new ArrayList<>();

    /**
     * Initialize repository with sample data.
     */
    public PersonRepository() {
        people.add(new Person("Jane Doe", "jane@example.com", 30));
        people.add(new Person("John Smith", "john@example.com", 25));
    }

    /**
     * {@inheritDoc}
     */
    public synchronized List<Person> findAll() {
        return List.copyOf(people);
    }

    /**
     * {@inheritDoc}
     */
    public synchronized Optional<Person> findById(String id) {
        return people.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    /**
     * {@inheritDoc}
     */
    public synchronized Person save(Person p) {
        people.add(p);
        return p;
    }

    /**
     * {@inheritDoc}
     */
    public synchronized void update(Person updated) {
        findById(updated.getId()).ifPresent(p -> {
            p.setName(updated.getName());
            p.setEmail(updated.getEmail());
            p.setAge(updated.getAge());
        });
    }

    /**
     * {@inheritDoc}
     */
    public synchronized void delete(String id) {
        findById(id).ifPresent(people::remove);
    }
}
