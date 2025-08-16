package edu.arizona.josesosa.enterprise.model;

import java.util.Objects;
import java.util.UUID;

/**
 * Domain model representing a person record.
 */
public class Person {
    private final String id;
    private String name;
    private String email;
    private Integer age;

    /**
     * Construct a new person with a generated UUID identifier.
     */
    public Person(String name, String email, Integer age) {
        this(UUID.randomUUID().toString(), name, email, age);
    }

    /**
     * Construct a person with an explicit identifier.
     */
    public Person(String id, String name, String email, Integer age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
    }

    /** Unique identifier (immutable). */
    public String getId() {
        return id;
    }

    /** Person's display name. */
    public String getName() {
        return name;
    }

    /** Email address. */
    public String getEmail() {
        return email;
    }

    /** Age in years (nullable). */
    public Integer getAge() {
        return age;
    }

    /** Set the person's name. */
    public void setName(String name) {
        this.name = name;
    }

    /** Set the person's email. */
    public void setEmail(String email) {
        this.email = email;
    }

    /** Set the person's age. */
    public void setAge(Integer age) {
        this.age = age;
    }

    /** Equality is based solely on id. */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
