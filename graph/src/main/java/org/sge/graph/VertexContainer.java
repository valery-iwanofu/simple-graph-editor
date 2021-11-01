package org.sge.graph;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

public class VertexContainer<VD, ED> implements Iterable<Vertex<VD, ED>>{
    private final Graph<VD, ED> graph;

    private final ListenerHelper<ContainerListener<Vertex<VD, ED>>> listenerHelper = new ListenerHelper<>();
    private final IndexKeeper<Vertex<VD, ED>> indexKeeper = new IndexKeeper<>();

    public VertexContainer(Graph<VD, ED> graph) {
        this.graph = graph;
    }

    public void addListener(ContainerListener<Vertex<VD, ED>> listener){
        listenerHelper.add(listener);
    }
    public void removeListener(ContainerListener<Vertex<VD, ED>> listener){
        listenerHelper.remove(listener);
    }

    public Vertex<VD, ED> add(VD vertexData) {
        Vertex<VD, ED> vertex = createVertex(vertexData);
        var from = indexKeeper.add(vertex);
        var singletonVertex = Collections.singleton(vertex);
        listenerHelper.each(listener -> listener.added(from, singletonVertex));
        return vertex;
    }

    public Collection<Vertex<VD, ED>> addAll(Collection<VD> verticesData) {
        var vertices = verticesData.stream().map(this::createVertex).toList();
        var from = indexKeeper.addAll(vertices);
        listenerHelper.each(listener -> listener.added(from, vertices));

        return vertices;
    }

    private Vertex<VD, ED> createVertex(VD data){
        return new Vertex<>(graph, data);
    }

    public void remove(Vertex<VD, ED> vertex) {
        GraphAffiliationChecker.requireBelongsToGraph(
                vertex,
                graph,
                "vertex must be belongs to the graph"
        );
        var from = indexKeeper.remove(vertex);
        graph.connectionManager.disconnectAll(vertex.edges);
        vertex.resetGraph();

        listenerHelper.each(listener -> listener.removed(from, vertex));
    }

    public void removeAll(Collection<Vertex<VD, ED>> vertices) {
        GraphAffiliationChecker.requireBelongsToGraph(
                vertices,
                graph,
                "item must be belongs to the graph"
        );
        var indices = indexKeeper.removeAll(vertices);
        vertices.forEach(vertex -> graph.connectionManager.disconnectAll(vertex.edges));
        vertices.forEach(Vertex::resetGraph);

        listenerHelper.each(listener -> listener.removed(indices, vertices));
    }

    public void clear() {
        graph.connectionManager.clear();
        indexKeeper.forEach(Vertex::resetGraph);
        indexKeeper.clear();

        listenerHelper.each(ContainerListener::cleared);
    }

    public int size() {
        return indexKeeper.size();
    }

    @Override
    public Iterator<Vertex<VD, ED>> iterator() {
        return indexKeeper.iterator();
    }
}
