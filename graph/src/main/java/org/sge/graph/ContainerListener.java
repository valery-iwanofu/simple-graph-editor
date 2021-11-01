package org.sge.graph;

import java.util.Collection;

public interface ContainerListener<T> {
    void added(int from, Collection<T> items);
    void removed(int index, T items);
    void removed(int[] indices, Collection<T> items);
    void cleared();
}
