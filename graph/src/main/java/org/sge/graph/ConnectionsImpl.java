package org.sge.graph;

import org.jetbrains.annotations.NotNull;
import org.sge.graph.api.Connections;

import java.util.*;
import java.util.function.BiFunction;

class ConnectionsImpl<V, E> implements Connections<V, E>, EdgesCleanup<V, E>{
    final Map<V, Map<V, E>> verticesToEdgeMap;
    private final BiFunction<V, V, E> edgeCreator;

    final Map<E, EdgeInfo<V, E>> edgeInfoMap = new HashMap<>();

    ConnectionsImpl(Map<V, Map<V, E>> verticesToEdgeMap, BiFunction<V, V, E> edgeCreator) {
        this.verticesToEdgeMap = verticesToEdgeMap;
        this.edgeCreator = edgeCreator;
    }

    @Override
    public Optional<E> findConnection(@NotNull V a, @NotNull V b) {
        var map = verticesToEdgeMap.get(a);
        if(map == null){
            return Optional.empty();
        }
        var edge = map.get(b);
        return Optional.ofNullable(edge);
    }

    @Override
    public @NotNull E connect(@NotNull V a, @NotNull V b) {
        return findConnection(a, b).orElseGet(() -> {
            var edge = createEdge(a, b);

            var aMap = verticesToEdgeMap.computeIfAbsent(a, v -> new HashMap<>());
            var bMap = verticesToEdgeMap.computeIfAbsent(b, v -> new HashMap<>());

            aMap.put(b, edge);
            bMap.put(a, edge);

            edgeInfoMap.put(edge, new EdgeInfo<>(a, b, aMap, bMap));

            return edge;
        });
    }

    @Override
    public void disconnect(@NotNull E edge) {
        var edgeInfo = edgeInfoMap.get(edge);
        if(edgeInfo == null){
            throw new IllegalStateException("given edge is not in this container");
        }
        cleanupEdgeInfo(edgeInfo);

        edgeInfoMap.remove(edge);
    }

    @Override
    public Optional<E> disconnect(@NotNull V a, @NotNull V b) {
        return findConnection(a, b).stream().peek(this::disconnect).findAny();
    }

    @Override
    public void cleanupEdges(Map<V, E> map) {
        for (E edge : map.values()) {
            var edgeInfo = edgeInfoMap.remove(edge);

            cleanupEdgeInfo(edgeInfo);
        }
    }

    private void cleanupEdgeInfo(EdgeInfo<V, E> edgeInfo){
        var a = edgeInfo.a;
        var b = edgeInfo.b;

        var aMap = edgeInfo.aMap;
        var bMap = edgeInfo.bMap;

        aMap.remove(b);
        bMap.remove(a);
    }

    @Override
    public @NotNull E createEdge(@NotNull V a, @NotNull V b) {
        return edgeCreator.apply(a, b);
    }

    @NotNull
    @Override
    public Iterator<E> iterator() {
        return edgeInfoMap.keySet().iterator();
    }

    private record EdgeInfo<V, E>(V a, V b, Map<V, E> aMap, Map<V, E> bMap) {
    }
}
