package edu.arizona.josesosa.enterprise.dao;

import java.util.List;
import java.util.Optional;

/**
 * Repository abstraction for Person persistence.
 */
public interface Repository<T> {
    /**
     * Retrieve an immutable snapshot of all entities.
     * @return list of all stored entities
     */
    List<T> findAll();

    /**
     * Find an entity by its identifier.
     * @param id unique identifier
     * @return optional entity if present
     */
    Optional<T> findById(String id);

    /**
     * Persist a new entity.
     * @param p entity to save
     * @return the saved entity (possibly with generated id)
     */
    T save(T p);

    /**
     * Update an existing entity.
     * @param p entity containing updated fields (must include id)
     */
    void update(T p);

    /**
     * Delete an entity by identifier.
     * @param id unique identifier
     */
    void delete(String id);
}
