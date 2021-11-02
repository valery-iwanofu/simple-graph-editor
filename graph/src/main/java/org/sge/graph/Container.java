package org.sge.graph;

import java.util.Collection;

class Container<T extends IndexedItem> {
    final ListenerHelper<ContainerListener<T>> listenerHelper = new ListenerHelper<>();
    final IndexKeeper<T> indexKeeper = new IndexKeeper<>();


    protected void add(T item){
        var index = indexKeeper.add(item);
        listenerHelper.each(listener -> listener.added(index, item));
    }
    public void addAll(Collection<T> items){
        var from = indexKeeper.addAll(items);
        listenerHelper.each(listener -> listener.added(from, items));
    }

    public void remove(T item){
        var index = indexKeeper.remove(item);
        listenerHelper.each(listener -> listener.removed(index, item));
    }
    public void removeAll(Collection<T> items){
        var index = indexKeeper.removeAll(items);
        listenerHelper.each(listener -> listener.removed(index, items));
    }

    public void clear(){
        indexKeeper.clear();

        listenerHelper.each(ContainerListener::cleared);
    }
}
