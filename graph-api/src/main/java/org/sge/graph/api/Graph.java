package org.sge.graph.api;

import org.jetbrains.annotations.NotNull;

public interface Graph<V, E> {
    @NotNull Vertices<V, E> vertices();
    @NotNull Connections<V, E> connections();
}
