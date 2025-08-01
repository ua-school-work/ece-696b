package edu.arizona.josesosa.structural.adapter.model.adapter;

import edu.arizona.josesosa.structural.adapter.model.IPerson;
import edu.arizona.josesosa.structural.adapter.model.remote.PersonDS1;

/**
 * Object Adapter for PersonDS1
 * This adapter converts PersonDS1 to the IPerson interface
 */
public class PersonDS1ObjectAdapter implements IPerson {
    private final PersonDS1 adaptee;

    public PersonDS1ObjectAdapter(PersonDS1 adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public Long getId() {
        return adaptee.getId();
    }

    @Override
    public String getfName() {
        return getFirstNameFromAdaptee();
    }

    @Override
    public String getlName() {
        return getLastNameFromAdaptee();
    }

    @Override
    public Double getAge() {
        return adaptee.getAge();
    }

    @Override
    public Long getSalary() {
        return adaptee.getSalary();
    }

    @Override
    public Boolean getMarried() {
        return adapteeHasPartner();
    }

    private String getFirstNameFromAdaptee() {
        String fullName = adaptee.getName();
        if (isInvalidFullName(fullName)) {
            return fullName;
        }
        return fullName.substring(0, getIndexOfSpace(fullName));
    }

    private String getLastNameFromAdaptee() {
        String fullName = adaptee.getName();
        if (isInvalidFullName(fullName)) {
            return "";
        }
        return fullName.substring(getIndexOfSpace(fullName) + 1);
    }

    private boolean isInvalidFullName(String fullName) {
        return fullName == null || !fullName.contains(" ");
    }

    private int getIndexOfSpace(String fullName) {
        return fullName.indexOf(" ");
    }

    private boolean adapteeHasPartner() {
        Long partner = adaptee.getPartner();
        return partner != null && partner > 0;
    }
}