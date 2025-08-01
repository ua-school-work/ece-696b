package edu.arizona.josesosa.structural.adapter.business;

import edu.arizona.josesosa.structural.adapter.model.IPerson;
import edu.arizona.josesosa.structural.adapter.model.Person;
import edu.arizona.josesosa.structural.adapter.model.adapter.PersonDS1ObjectAdapter;
import edu.arizona.josesosa.structural.adapter.model.adapter.PersonDS2ClassAdapter;
import edu.arizona.josesosa.structural.adapter.model.remote.PersonDS1;
import org.exolab.castor.xml.Unmarshaller;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.stream.Collectors;

public class RemotePersonService implements IPersonService {

    private static final String PERSON_XML_DS1 = "person-ds1.xml";
    private static final String PERSON_XML_DS2 = "person-ds2.xml";

    @Override
    public List<Person> getPersonList() {
        List<Person> allPersons = new ArrayList<>();
        allPersons.addAll(getPersonsFromDataSource1());
        allPersons.addAll(getPersonsFromDataSource2());
        return allPersons;
    }

    private List<Person> getPersonsFromDataSource1() {
        List<PersonDS1> personDS1List = getPersonListDS1();
        if (personDS1List == null) {
            return Collections.emptyList();
        }
        return personDS1List.stream()
                .map(PersonDS1ObjectAdapter::new)
                .map(this::convertToPerson)
                .collect(Collectors.toList());
    }

    private List<Person> getPersonsFromDataSource2() {
        List<PersonDS2ClassAdapter> personDS2List = getPersonListDS2();
        if (personDS2List == null) {
            return Collections.emptyList();
        }
        return personDS2List.stream()
                .map(this::convertToPerson)
                .collect(Collectors.toList());
    }

    /**
     * Helper method to convert IPerson to Person
     */
    private Person convertToPerson(IPerson iPerson) {
        Person person = new Person();
        person.setId(iPerson.getId());
        person.setfName(iPerson.getfName());
        person.setlName(iPerson.getlName());
        person.setAge(iPerson.getAge());
        person.setSalary(iPerson.getSalary());
        person.setMarried(iPerson.getMarried());
        return person;
    }

    @SuppressWarnings("unchecked")
    public List<PersonDS1> getPersonListDS1() {
        try {
            FileReader reader = new FileReader(PERSON_XML_DS1);
            return (List<PersonDS1>) Unmarshaller.unmarshal(ArrayList.class,
                    reader);

        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace(System.err);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<PersonDS2ClassAdapter> getPersonListDS2() {
        try {
            FileReader reader = new FileReader(PERSON_XML_DS2);
            return (List<PersonDS2ClassAdapter>) Unmarshaller.unmarshal(ArrayList.class,
                    reader);

        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace(System.err);
        }
        return null;
    }

    public static void main(String[] args) {
        List<PersonDS1> list = new RemotePersonService().getPersonListDS1();
        List<PersonDS2ClassAdapter> list2 = new RemotePersonService().getPersonListDS2();
        System.out.println(list);
        System.out.println(list2);
    }
}