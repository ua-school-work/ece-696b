package edu.arizona.josesosa.structural.adapter.business;

import edu.arizona.josesosa.structural.adapter.model.Person;

import java.util.List;

public interface IPersonService {
    List<Person> getPersonList();
}