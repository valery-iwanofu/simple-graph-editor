package org.sge.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

final class IndexKeeper<T extends IndexedItem> implements Iterable<T> {
    private final List<T> list = new ArrayList<>();

    public void add(T item){
        var index = list.size();
        list.add(item);
        item.index = index;
    }

    public void addAll(Collection<T> items){
        var startIndex = list.size();
        list.addAll(items);
        updateIndexFrom(startIndex);
    }

    public void remove(T item){
        var beginIndex = item.index;
        list.remove(beginIndex);
        updateIndexFrom(beginIndex);
    }

    public void removeAll(Collection<T> items){
        if(items.isEmpty()){
            return;
        }
        var indices = items.stream().mapToInt(value -> value.index).sorted().toArray();
        int offset = 0;
        for (int i = 0; i < indices.length; i++) {
            list.remove(i - offset);
            offset++;
        }
        var updateFrom = indices[0]; // min index
        updateIndexFrom(updateFrom);
    }

    private void updateIndexFrom(int beginIndex) {
        var iterator = list.listIterator(beginIndex);
        while (iterator.hasNext()) {
            var nextIndex = iterator.nextIndex();
            var next = iterator.next();
            next.index = nextIndex;
        }
    }

    public void clear() {
        list.clear();
    }

    public int size(){
        return list.size();
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }
}
