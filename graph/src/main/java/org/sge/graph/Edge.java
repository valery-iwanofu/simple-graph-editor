package org.sge.graph;

public final class Edge<VD, ED> extends Item<VD, ED, ED>{
    final Vertex<VD, ED> a;
    final Vertex<VD, ED> b;

    Edge(Graph<VD, ED> graph, Vertex<VD, ED> a, Vertex<VD, ED> b, ED data) {
        super(graph, data);
        this.a = a;
        this.b = b;
    }

    public Vertex<VD, ED> a() {
        return a;
    }

    public Vertex<VD, ED> b() {
        return b;
    }
}
