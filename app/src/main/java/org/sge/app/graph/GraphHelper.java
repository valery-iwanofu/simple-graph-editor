package org.sge.app.graph;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.sge.graph.GraphImpl;
import org.sge.graph.api.Graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

public class GraphHelper {
    private final Graph<Vertex, Edge> graph;
    private final ObservableList<Vertex> vertices = FXCollections.observableArrayList();
    private final ObservableList<Vertex> verticesView = FXCollections.unmodifiableObservableList(vertices);

    private final ObservableList<Edge> edges = FXCollections.observableArrayList();
    private final ObservableList<Edge> edgesView = FXCollections.unmodifiableObservableList(edges);

    public GraphHelper(Graph<Vertex, Edge> graph) {
        this.graph = graph;

        vertices.addAll(StreamSupport.stream(graph.vertices().spliterator(), true).toList());
        edges.addAll(StreamSupport.stream(graph.connections().spliterator(), true).toList());

        // graph.vertices().addListener(new VerticesListener());
        // graph.connections().addListener(new EdgeListener());
    }

    public void add(Vertex vertex){
        graph.vertices().add(vertex);
        vertices.add(vertex);
    }
    public void remove(Vertex vertex){
        var vertexEdges = graph.vertices().edgesFor(vertex);

        graph.vertices().remove(vertex);

        edges.removeAll(vertexEdges);
        vertices.remove(vertex);
    }
    public void removeAll(List<Vertex> vertices) {
        // why in this order? because removeAll in graph.vertices can produce an exception
        List<Edge> edgesToRemove = new ArrayList<>();
        for (Vertex vertex : vertices) {
            var vertexEdges = graph.vertices().edgesFor(vertex);
            edgesToRemove.addAll(vertexEdges);
        }

        graph.vertices().removeAll(vertices);

        this.edges.removeAll(edgesToRemove);
        this.vertices.removeAll(vertices);
    }

    public void connect(Vertex a, Vertex b){
        var edge = graph.connections().connect(a, b);
        edges.add(edge);
    }

    public void disconnect(Vertex a, Vertex b){
        disconnect(graph.connections().findConnection(a, b).orElseThrow());
    }
    public void disconnect(Edge edge){
        graph.connections().disconnect(edge);
        edges.remove(edge);
    }

    public void toggleConnection(Vertex a, Vertex b){
        var connection = graph.connections().findConnection(a, b);
        connection.ifPresentOrElse(this::disconnect, () -> connect(a, b));
    }

//    private class VerticesListener implements ContainerListener<Vertex> {
//        @Override
//        public void added(int from, Collection<Vertex> items) {
//            vertices.addAll(from, items);
//        }
//
//        @Override
//        public void removed(int index, Vertex vertex) {
//            vertices.remove(index);
//        }
//
//        @Override
//        public void removed(int[] indices, Collection<Vertex> items) {
//            for (int offset = 0; offset < indices.length; offset++) {
//                int index = indices[offset] - offset;
//                vertices.remove(index);
//            }
//        }
//
//        @Override
//        public void cleared() {
//            vertices.clear();
//        }
//    }
//
//    private class EdgeListener implements ContainerListener<Edge> {
//        @Override
//        public void added(int from, Collection<Edge> items) {
//            edges.addAll(from, items);
//        }
//
//        @Override
//        public void removed(int index, Edge vertex) {
//            edges.remove(index);
//        }
//
//        @Override
//        public void removed(int[] indices, Collection<Edge> items) {
//            for (int offset = 0; offset < indices.length; offset++) {
//                int index = indices[offset] - offset;
//                edges.remove(index);
//            }
//        }
//
//        @Override
//        public void cleared() {
//            edges.clear();
//        }
//    }

    public Graph<Vertex, Edge> graph() {
        return graph;
    }

    public ObservableList<Vertex> vertices(){
        return verticesView;
    }

    public ObservableList<Edge> edges(){
        return edgesView;
    }
}
