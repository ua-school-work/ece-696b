package edu.arizona.josesosa;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

class Lines {

    private final List<String> lines = new ArrayList<>();
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    void insert(String line) {
        lines.add(line);
        // The "line.inserted" property name helps listeners distinguish this event.
        // The old value is null, and the new value is the event payload.
        support.firePropertyChange("line.inserted", null, new LinesEvent(line));
    }

    void delete(int index) {
        String line = lines.remove(index);
        // Notify listeners that a line was deleted.
        // The old value is the event payload, and the new value is null.
        support.firePropertyChange("line.deleted", new LinesEvent(line), null);
    }

    String get(int index) {
        return lines.get(index);
    }

    List<String> all() {
        return lines;
    }
}

