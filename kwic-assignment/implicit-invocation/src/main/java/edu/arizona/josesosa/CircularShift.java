package edu.arizona.josesosa;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

class CircularShift implements PropertyChangeListener {

    private final Lines shifts;

    CircularShift(Lines shifts) {
        this.shifts = shifts;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // We only care about events where a line was inserted
        if (!"line.inserted".equals(evt.getPropertyName())) {
            return;
        }

        LinesEvent event = (LinesEvent) evt.getNewValue();

        List<String> result = new LinkedList<>();
        List<String> words = new ArrayList<>(Arrays.asList(event.line().split(" ")));
        for (int i = 0; i < words.size(); ++i) {
            // This performs the circular shift
            words.addFirst(words.removeLast());
            result.add(arrToString(words));
        }

        for (String shift : result) {
            shifts.insert(shift);
        }
    }

    private String arrToString(List<String> arr) {
        return String.join(" ", arr);
    }
}