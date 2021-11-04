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

//    /**
//     * @throws IllegalStateException if a or b not in vertices container
//     *
//     * @return new edge if connection not existence before, otherwise an empty optional
//     */
//    default Optional<E> toggleConnection(@NotNull V a, @NotNull V b){
//        return findConnection(a, b).<E>flatMap(e -> {
//            disconnect(e);
//            return Optional.empty();
//        }).or(() -> Optional.of(connect(a, b)));
//    }
}
