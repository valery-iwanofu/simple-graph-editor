package org.sge.app.graph;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;

public class SelectableData {
    final ReadOnlyBooleanWrapper selected = new ReadOnlyBooleanWrapper();

    public boolean isSelected() {
        return selected.get();
    }
    public ReadOnlyBooleanProperty selectedProperty() {
        return selected.getReadOnlyProperty();
    }
}
