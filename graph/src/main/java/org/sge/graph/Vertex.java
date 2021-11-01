package org.sge.graph;

import java.util.*;

public final class Vertex<VD, ED> extends Item<VD, ED, VD>{
    final Map<Vertex<VD, ED>, Edge<VD, ED>> edgeMap = new HashMap<>();
    final Set<Edge<VD, ED>> edges = new HashSet<>();

    Vertex(Graph<VD, ED> graph, VD data) {
        super(graph, data);
    }

    public Set<Edge<VD, ED>> edges() {
        return Collections.unmodifiableSet(edges);
    }
}
