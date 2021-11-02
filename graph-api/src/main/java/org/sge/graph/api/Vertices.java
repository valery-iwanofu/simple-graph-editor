package org.sge.graph.api;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Collection;
import java.util.Set;

public interface Vertices<V, E> extends Iterable<V>{
    /**
     * @throws IllegalStateException if vertex is already in this container
     */
    void add(@NotNull V vertex);
    /**
     * @throws IllegalStateException if one of given vertices already in this container
     */
    void addAll(@NotNull Collection<? extends V> vertices);

    /**
     * @throws IllegalStateException if vertex is not in this container
     */
    void remove(@NotNull V vertex);
    /**
     * @throws IllegalStateException if one of given vertices not in this container
     */
    void removeAll(@NotNull Collection<? extends V> vertices);

    boolean contains(@NotNull V vertex);

    /**
     * @throws IllegalStateException if vertex is not in this container
     */
    @UnmodifiableView Collection<E> edgesFor(@NotNull V vertex);
}
