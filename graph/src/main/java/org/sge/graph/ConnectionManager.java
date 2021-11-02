package org.sge.graph;

import java.util.*;

public class ConnectionManager<VD, ED> implements Iterable<Edge<VD, ED>>{
    private final Container<Edge<VD, ED>> container = new Container<>();

    private final Graph<VD, ED> graph;

    public ConnectionManager(Graph<VD, ED> graph) {
        this.graph = graph;
    }

    public void addListener(ContainerListener<Edge<VD, ED>> listener){
        container.listenerHelper.add(listener);
    }
    public void removeListener(ContainerListener<Edge<VD, ED>> listener){
        container.listenerHelper.add(listener);
    }

    public boolean isConnected(Vertex<VD, ED> a, Vertex<VD, ED> b) {
        // we check arguments inside findConnection
        return findConnection(a, b).isPresent();
    }

    public Optional<Edge<VD, ED>> findConnection(Vertex<VD, ED> a, Vertex<VD, ED> b) {
        GraphAffiliationChecker.requireBelongsToGraph(a, graph, "a must be belongs to the graph");
        GraphAffiliationChecker.requireBelongsToGraph(b, graph, "b must be belongs to the graph");
        return Optional.ofNullable(a.edgeMap.get(b));
    }

    public Optional<Edge<VD, ED>> connect(Vertex<VD, ED> a, Vertex<VD, ED> b, ED edgeData) {
        // we check arguments inside isConnected
        if(isConnected(a, b)){
            return Optional.empty();
        }

        var edge = new Edge<>(graph, a, b, edgeData);

        a.edgeMap.put(b, edge);
        a.edges.add(edge);

        b.edgeMap.put(a, edge);
        b.edges.add(edge);

        container.add(edge);

        return Optional.of(edge);
    }

    public void disconnect(Edge<VD, ED> edge) {
        GraphAffiliationChecker.requireBelongsToGraph(edge, graph, "edge must be belongs to the graph");

        cleanupEdge(edge);

        container.remove(edge);
    }

    public void disconnectAll(Collection<Edge<VD, ED>> edges) {
        GraphAffiliationChecker.requireBelongsToGraph(edges, graph, "edge must be belongs to the graph");

        edges.forEach(this::cleanupEdge);

        container.removeAll(edges);
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
        for (Edge<VD, ED> edge : this) {
            cleanupEdge(edge);
        }

        container.clear();
    }

    public int size() {
        return container.indexKeeper.size();
    }

    @Override
    public Iterator<Edge<VD, ED>> iterator() {
        return container.indexKeeper.iterator();
    }

    public Graph<VD, ED> graph() {
        return graph;
    }
}
