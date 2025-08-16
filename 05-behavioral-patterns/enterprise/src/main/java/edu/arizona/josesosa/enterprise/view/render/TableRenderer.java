package edu.arizona.josesosa.enterprise.view.render;

import edu.arizona.josesosa.enterprise.model.Person;

import java.util.List;

/**
 * Port interface for rendering a table of people.
 */
public interface TableRenderer {
    String render(List<Person> people);
}
