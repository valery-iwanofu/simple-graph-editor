package org.sge.graph;

import java.util.Collection;
import java.util.Collections;

public interface ContainerListener<T> {
    void added(int from, Collection<T> items);
    default void added(int index, T item){
        added(index, Collections.singleton(item));
    }

    void removed(int[] indices, Collection<T> items);
    default void removed(int index, T item){
        removed(new int[]{index}, Collections.singleton(item));
    }

    void cleared();
}
