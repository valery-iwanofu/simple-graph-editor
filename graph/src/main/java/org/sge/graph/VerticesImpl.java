package org.sge.graph;

import org.jetbrains.annotations.NotNull;
import org.sge.graph.api.Vertices;

import java.util.*;

class VerticesImpl<V, E> implements Vertices<V, E> {
    private final EdgesCleanup<V, E> edgesCleanup;
    //private final Set<V> vertices = new HashSet<>();
    final Map<V, Map<V, E>> verticesToEdgeMap;

    VerticesImpl(EdgesCleanup<V, E> edgesCleanup, Map<V, Map<V, E>> verticesToEdgeMap) {
        this.edgesCleanup = edgesCleanup;
        this.verticesToEdgeMap = verticesToEdgeMap;
    }

    @Override
    public void add(@NotNull V vertex) {
        if(verticesToEdgeMap.containsKey(vertex)){
            throw new IllegalStateException("given vertex already in this container");
        }
        verticesToEdgeMap.put(vertex, new HashMap<>());
    }

    @Override
    public void addAll(@NotNull Collection<? extends V> vertices) {
        for (V vertex : vertices) {
            if(verticesToEdgeMap.containsKey(vertex)){
                throw new IllegalStateException("one of given vertices already in this container");
            }
        }

        vertices.forEach(vertex -> verticesToEdgeMap.put(vertex, new HashMap<>()));
    }

    @Override
    public void remove(@NotNull V vertex) {
        var map = verticesToEdgeMap.remove(vertex);
        if(map == null){
            throw new IllegalStateException("given vertex are not in this container");
        }
        edgesCleanup.cleanupEdges(map);
    }

    @Override
    public void removeAll(@NotNull Collection<? extends V> vertices) {
        for (V vertex : vertices) {
            if(!this.verticesToEdgeMap.containsKey(vertex)){
                throw new IllegalStateException("one of given vertex are not in this container");
            }
        }
        for (V vertex : vertices) {
            var remove = verticesToEdgeMap.remove(vertex);
            // if vertices contains duplicates
            if(remove != null){
                edgesCleanup.cleanupEdges(remove);
            }
        }
    }

    @Override
    public boolean contains(@NotNull V vertex) {
        return verticesToEdgeMap.containsKey(vertex);
    }

    @Override
    public Collection<E> edgesFor(@NotNull V vertex) {
        var edgeMap = verticesToEdgeMap.remove(vertex);
        if(edgeMap == null){
            throw new IllegalStateException("given vertex are not in this container");
        }
        return Collections.unmodifiableCollection(edgeMap.values());
    }

    @NotNull
    @Override
    public Iterator<V> iterator() {
        return verticesToEdgeMap.keySet().iterator();
    }
}
