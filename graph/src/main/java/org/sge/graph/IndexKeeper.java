package org.sge.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

final class IndexKeeper<T extends IndexedItem> implements Iterable<T> {
    private final List<T> list = new ArrayList<>();

    public int add(T item){
        var index = list.size();
        list.add(item);
        item.index = index;

        return index;
    }

    public int addAll(Collection<T> items){
        var startIndex = list.size();
        list.addAll(items);
        updateIndexFrom(startIndex);

        return startIndex;
    }

    public int remove(T item){
        var beginIndex = item.index;
        list.remove(beginIndex);
        updateIndexFrom(beginIndex);

        return beginIndex;
    }

    public int[] removeAll(Collection<T> items){
        if(items.isEmpty()){
            return new int[0];
        }
        var indices = items.stream().mapToInt(value -> value.index).sorted().toArray();
        int offset = 0;
        for (int i = 0; i < indices.length; i++) {
            list.remove(i - offset);
            offset++;
        }
        var updateFrom = indices[0]; // min index
        updateIndexFrom(updateFrom);

        return indices;
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
