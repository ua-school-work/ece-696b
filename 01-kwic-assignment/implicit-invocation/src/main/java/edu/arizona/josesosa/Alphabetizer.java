package edu.arizona.josesosa;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;

class Alphabetizer implements PropertyChangeListener {

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // The source of the event is the instance of the Lines class
        Lines lines = (Lines) evt.getSource();

        // The event itself is not used, we just sort the entire list
        // every time a change is announced.
        lines.all().sort(String.CASE_INSENSITIVE_ORDER);
    }
}

