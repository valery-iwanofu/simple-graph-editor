package org.sge.app.graph;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.sge.graph.GraphImpl;
import org.sge.graph.api.Graph;

import java.util.Collection;
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
