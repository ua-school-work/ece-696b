package edu.arizona.josesosa.creational.factory;

import edu.arizona.josesosa.creational.abstractfactory.AbstractFactory;
import edu.arizona.josesosa.creational.abstractfactory.EbayTestFactory;

public class EbayUserTest extends UserTest {

    @Override
    protected AbstractFactory makeFactory() {
        return new EbayTestFactory();
    }
}
