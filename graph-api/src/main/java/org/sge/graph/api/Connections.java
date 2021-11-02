package org.sge.graph.api;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface Connections<V, E> extends Iterable<E> {
    Optional<E> findConnection(@NotNull V a, @NotNull V b);

    /**
     * @throws IllegalStateException if a or b not in vertices container
     *
     * @return new edge if a and b are not connected before, existing edge otherwise
     */
    @NotNull E connect(@NotNull V a, @NotNull V b);

    /**
     * @throws IllegalStateException if edge not in this container
     */
    void disconnect(@NotNull E edge);

    /**
     * @throws IllegalStateException if a or b not in vertices container
     *
     * @return edge if a and b are connected, empty optional otherwise
     */
    Optional<E> disconnect(@NotNull V a, @NotNull V b);

    @NotNull E createEdge(@NotNull V a, @NotNull V b);

    /**
     * @throws IllegalStateException if a or b not in vertices container
     */
    default void toggleConnection(@NotNull V a, @NotNull V b){
        findConnection(a, b).ifPresentOrElse(
                this::disconnect,
                () -> connect(a, b)
        );
    }
}
