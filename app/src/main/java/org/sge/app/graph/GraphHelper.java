package org.sge.app.graph;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collection;
import java.util.stream.StreamSupport;

public class GraphHelper {
    private final Graph<VertexData, EdgeData> graph;
    private final ObservableList<Vertex<VertexData, EdgeData>> vertices = FXCollections.observableArrayList();
    private final ObservableList<Vertex<VertexData, EdgeData>> verticesView = FXCollections.unmodifiableObservableList(vertices);

    private final ObservableList<Edge<VertexData, EdgeData>> edges = FXCollections.observableArrayList();
    private final ObservableList<Edge<VertexData, EdgeData>> edgesView = FXCollections.unmodifiableObservableList(edges);

    public GraphHelper(Graph<VertexData, EdgeData> graph) {
        this.graph = graph;

        vertices.addAll(StreamSupport.stream(graph.vertices().spliterator(), true).toList());
        edges.addAll(StreamSupport.stream(graph.connections().spliterator(), true).toList());

        graph.vertices().addListener(new VerticesListener());
        graph.connections().addListener(new EdgeListener());
    }

    private class VerticesListener implements ContainerListener<Vertex<VertexData, EdgeData>> {
        @Override
        public void added(int from, Collection<Vertex<VertexData, EdgeData>> items) {
            vertices.addAll(from, items);
        }

        @Override
        public void removed(int index, Vertex<VertexData, EdgeData> vertex) {
            vertices.remove(index);
        }

        @Override
        public void removed(int[] indices, Collection<Vertex<VertexData, EdgeData>> items) {
            for (int offset = 0; offset < indices.length; offset++) {
                int index = indices[offset] - offset;
                vertices.remove(index);
            }
        }

        @Override
        public void cleared() {
            vertices.clear();
        }
    }

    private class EdgeListener implements ContainerListener<Edge<VertexData, EdgeData>> {
        @Override
        public void added(int from, Collection<Edge<VertexData, EdgeData>> items) {
            edges.addAll(from, items);
        }

        @Override
        public void removed(int index, Edge<VertexData, EdgeData> vertex) {
            edges.remove(index);
        }

        @Override
        public void removed(int[] indices, Collection<Edge<VertexData, EdgeData>> items) {
            for (int offset = 0; offset < indices.length; offset++) {
                int index = indices[offset] - offset;
                edges.remove(index);
            }
        }

        @Override
        public void cleared() {
            edges.clear();
        }
    }

    public Graph<VertexData, EdgeData> graph() {
        return graph;
    }

    public ObservableList<Vertex<VertexData, EdgeData>> vertices(){
        return verticesView;
    }

    public ObservableList<Edge<VertexData, EdgeData>> edges(){
        return edgesView;
    }
}
