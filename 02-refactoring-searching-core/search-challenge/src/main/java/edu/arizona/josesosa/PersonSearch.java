package edu.arizona.josesosa;

import java.util.Arrays;
import java.util.List;


public class PersonSearch extends GenericSearch<Person> {


    // Restriction for Search
    private static final String[] RESTRICTIONS = {
            "lower(" + PersonSortCriteria.FIRSTNAME.getProperty() + ") like concat(lower(#{personSearch.person.firstName}),'%')",
            "lower(" + PersonSortCriteria.LASTNAME.getProperty() + ") like concat(lower(#{personSearch.person.lastName}),'%')",
            "lower(" + PersonSortCriteria.AGE.getProperty() + ") like concat(lower(#{personSearch.person.age}),'%')",
    };

    // instance of a person for search criteria data
    private Person person = new Person();

    private enum PersonSortCriteria implements SortCriteria {
        FIRSTNAME("person.firstName"),
        LASTNAME("person.lastName"),
        AGE("person.age");

        private final String property;

        PersonSortCriteria(String prop) {
            this.property = prop;
        }

        @Override
        public String getProperty() {
            return property;
        }
    }

    public PersonSearch() {
        super(Person.class);
    }

    @Override
    protected SortCriteria[] getSortCriteria() {
        return PersonSortCriteria.values();
    }

    @Override
    // Restriction for Search
    protected List<String> getCriteria() {
        return Arrays.asList(RESTRICTIONS);
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

}
