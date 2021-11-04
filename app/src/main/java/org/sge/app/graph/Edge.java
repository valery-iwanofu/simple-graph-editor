package org.sge.app.graph;

public class Edge extends Selectable {
    private final Vertex a;
    private final Vertex b;

    public Edge(Vertex a, Vertex b) {
        this.a = a;
        this.b = b;
    }

    public Vertex a() {
        return a;
    }
    public Vertex b() {
        return b;
    }
}
