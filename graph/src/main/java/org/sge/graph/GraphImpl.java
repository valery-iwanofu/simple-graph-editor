package org.sge.graph;

import org.jetbrains.annotations.NotNull;
import org.sge.graph.api.Connections;
import org.sge.graph.api.Graph;
import org.sge.graph.api.Vertices;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class GraphImpl<V, E> implements Graph<V, E> {
    private final VerticesImpl<V, E> vertices;
    private final ConnectionsImpl<V, E> connections;

    public GraphImpl(BiFunction<V, V, E> edgeCreator) {
        var edgeMap = new HashMap<V, Map<V, E>>();
        connections = new ConnectionsImpl<>(edgeMap, edgeCreator);
        vertices = new VerticesImpl<>(connections, edgeMap);
    }

    @Override
    public @NotNull Vertices<V, E> vertices() {
        return vertices;
    }

    @Override
    public @NotNull Connections<V, E> connections() {
        return connections;
    }
}
