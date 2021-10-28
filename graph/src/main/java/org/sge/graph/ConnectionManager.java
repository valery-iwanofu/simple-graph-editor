package org.sge.graph;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

public class ConnectionManager<VD, ED> {
    private final IndexKeeper<Edge<VD, ED>> indexKeeper = new IndexKeeper<>();

    private final Graph<VD, ED> graph;

    public ConnectionManager(Graph<VD, ED> graph) {
        this.graph = graph;
    }

    public boolean isConnected(Vertex<VD, ED> a, Vertex<VD, ED> b) {
        // we check arguments inside findConnection
        return findConnection(a, b).isPresent();
    }

    public Optional<Edge<VD, ED>> findConnection(Vertex<VD, ED> a, Vertex<VD, ED> b) {
        ArgumentChecker.requireBelongsToGraph(a, graph, "a can not be null", "a must be belongs to the graph");
        ArgumentChecker.requireBelongsToGraph(b, graph, "b can not be null", "b must be belongs to the graph");
        return Optional.ofNullable(a.edgeMap.get(b));
    }

    public Optional<Edge<VD, ED>> connect(Vertex<VD, ED> a, Vertex<VD, ED> b, ED edgeData) {
        // we check arguments inside isConnected
        if(isConnected(a, b)){
            return Optional.empty();
        }

        var edge = new Edge<VD, ED>(graph, a, b, edgeData);
        indexKeeper.add(edge);

        a.edgeMap.put(b, edge);
        a.edges.add(edge);

        b.edgeMap.put(a, edge);
        b.edges.add(edge);

        return Optional.of(edge);
    }

    public void disconnect(Edge<VD, ED> edge) {
        ArgumentChecker.requireBelongsToGraph(edge, graph, "edge can not be null", "edge must be belongs to the graph");

        indexKeeper.remove(edge);
        cleanupEdge(edge);
    }

    public void disconnectAll(Collection<Edge<VD, ED>> edges) {
        ArgumentChecker.requireBelongsToGraph(edges, graph, "edge can not be null", "edge must be belongs to the graph");

        indexKeeper.removeAll(edges);
        edges.forEach(this::cleanupEdge);
    }

    private void cleanupEdge(Edge<VD, ED> edge){
        var a = edge.a;
        var b = edge.b;

        a.edgeMap.remove(b);
        a.edges.remove(edge);

        b.edgeMap.remove(a);
        b.edges.remove(edge);

        edge.resetGraph();
    }

    void clear(){
        for (Edge<VD, ED> edge : indexKeeper) {
            cleanupEdge(edge);
        }
        indexKeeper.clear();
    }

    public int size() {
        return indexKeeper.size();
    }

    public Iterator<Edge<VD, ED>> iterator() {
        return indexKeeper.iterator();
    }

    public Graph<VD, ED> graph() {
        return graph;
    }
}
