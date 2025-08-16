package edu.arizona.josesosa.enterprise.service;

import edu.arizona.josesosa.enterprise.model.Person;

import java.util.List;
import java.util.Optional;

/**
 * Abstraction for person data operations (DIP).
 */
public interface PersonService {
    /**
     * Retrieve all persons.
     * @return immutable list of persons
     */
    List<Person> findAll();

    /**
     * Find a person by id.
     * @param id unique identifier
     * @return optional person
     */
    Optional<Person> findById(String id);

    /**
     * Add a new person.
     * @param name person name (required)
     * @param email email (required)
     * @param age optional age
     * @return the created person
     */
    Person add(String name, String email, Integer age);

    /**
     * Update an existing person.
     * @param id existing person id
     * @param name new name
     * @param email new email
     * @param age new age
     */
    void update(String id, String name, String email, Integer age);

    /**
     * Delete a person by id.
     * @param id unique identifier
     */
    void delete(String id);
}
