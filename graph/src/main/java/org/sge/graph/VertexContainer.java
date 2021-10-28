package org.sge.graph;

import java.util.Collection;
import java.util.Iterator;

public class VertexContainer<VD, ED>{
    private final IndexKeeper<Vertex<VD, ED>> indexKeeper = new IndexKeeper<>();

    private final Graph<VD, ED> graph;

    public VertexContainer(Graph<VD, ED> graph) {
        this.graph = graph;
    }

    public Vertex<VD, ED> add(VD vertexData) {
        Vertex<VD, ED> vertex = createVertex(vertexData);
        indexKeeper.add(vertex);
        return vertex;
    }

    public Collection<Vertex<VD, ED>> addAll(Collection<VD> verticesData) {
        var vertices = verticesData.stream().map(this::createVertex).toList();
        indexKeeper.addAll(vertices);
        return vertices;
    }

    private Vertex<VD, ED> createVertex(VD data){
        return new Vertex<>(graph, data);
    }

    public void remove(Vertex<VD, ED> vertex) {
        ArgumentChecker.requireBelongsToGraph(
                vertex,
                graph,
                "item can not be null",
                "vertex must be belongs to the graph"
        );
        indexKeeper.remove(vertex);
        graph.connectionManager.disconnectAll(vertex.edges);
        vertex.resetGraph();
    }

    public void removeAll(Collection<Vertex<VD, ED>> vertices) {
        ArgumentChecker.requireBelongsToGraph(
                vertices,
                graph,
                "item can not be null",
                "item must be belongs to the graph"
        );
        indexKeeper.removeAll(vertices);
        vertices.forEach(vertex -> graph.connectionManager.disconnectAll(vertex.edges));
        vertices.forEach(Vertex::resetGraph);
    }

    public void clear() {
        graph.connectionManager.clear();
        indexKeeper.forEach(Vertex::resetGraph);
        indexKeeper.clear();
    }

    public int size() {
        return indexKeeper.size();
    }

    public Iterator<Vertex<VD, ED>> iterator() {
        return indexKeeper.iterator();
    }
}
