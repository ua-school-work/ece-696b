package edu.arizona.josesosa.patterns.structural.composite;

import lombok.Getter;

import java.util.Iterator;

@Getter
public abstract class Equipment {
    private String name;

    protected Equipment(String name) {}

    abstract int getPower();
    abstract int getPrice();
    abstract int getDiscountedPrice();

    abstract void add( Equipment equipment);
    abstract void remove( Equipment equipment);
    abstract Iterator<Equipment> getChildren();
}
