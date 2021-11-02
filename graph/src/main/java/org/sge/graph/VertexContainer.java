package org.sge.graph;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

public class VertexContainer<VD, ED> implements Iterable<Vertex<VD, ED>>{
    private final Graph<VD, ED> graph;

    private final Container<Vertex<VD, ED>> container = new Container<>();

    public VertexContainer(Graph<VD, ED> graph) {
        this.graph = graph;
    }

    public void addListener(ContainerListener<Vertex<VD, ED>> listener){
        container.listenerHelper.add(listener);
    }
    public void removeListener(ContainerListener<Vertex<VD, ED>> listener){
        container.listenerHelper.add(listener);
    }

    public Vertex<VD, ED> add(VD vertexData) {
        Vertex<VD, ED> vertex = createVertex(vertexData);
        container.add(vertex);
        return vertex;
    }

    public Collection<Vertex<VD, ED>> addAll(Collection<VD> verticesData) {
        var vertices = verticesData.stream().map(this::createVertex).toList();
        container.addAll(vertices);

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
        graph.connectionManager.disconnectAll(vertex.edges);
        vertex.resetGraph();

        container.remove(vertex);
    }

    public void removeAll(Collection<Vertex<VD, ED>> vertices) {
        GraphAffiliationChecker.requireBelongsToGraph(
                vertices,
                graph,
                "item must be belongs to the graph"
        );
        vertices.forEach(vertex -> graph.connectionManager.disconnectAll(vertex.edges));
        vertices.forEach(Vertex::resetGraph);

        container.removeAll(vertices);
    }

    public void clear() {
        var indexKeeper = container.indexKeeper;
        graph.connectionManager.clear();
        indexKeeper.forEach(Vertex::resetGraph);
        indexKeeper.clear();

        container.clear();
    }

    public int count() {
        return container.indexKeeper.size();
    }

    @Override
    public Iterator<Vertex<VD, ED>> iterator() {
        return container.indexKeeper.iterator();
    }
}
