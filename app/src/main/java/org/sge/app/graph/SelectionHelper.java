package org.sge.app.graph;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;
import org.sge.graph.HasData;

import java.util.Collection;

abstract class SelectionList<D extends SelectableData, T extends HasData<D>>{
    protected final ObservableList<T> selected = FXCollections.observableArrayList();
    protected final ObservableList<T> selectedView = FXCollections.unmodifiableObservableList(selected);
    protected final ObservableList<T> items = FXCollections.observableArrayList();
    protected final ObservableList<T> itemsView = FXCollections.unmodifiableObservableList(items);

    protected void addAll(int index, @NotNull Collection<? extends T> items){
        for (T item : items) {
            var data = item.data();
        }
    }

    public void select(T item){
        var data = item.data();
        data.selected.set(true);
    }

    @UnmodifiableView
    public ObservableList<T> getSelected() {
        return selected;
    }
    @UnmodifiableView
    public ObservableList<T> getItems(){
        return itemsView;
    }
}
