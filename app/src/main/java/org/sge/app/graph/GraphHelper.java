package org.sge.app.graph;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.sge.graph.Graph;
import org.sge.graph.Vertex;

import java.util.Collection;

public class GraphHelper {
    private final Graph<VertexData, EdgeData> graph;

    public GraphHelper(Graph<VertexData, EdgeData> graph) {
        this.graph = graph;
    }

    public Graph<VertexData, EdgeData> graph() {
        return graph;
    }

    private final ObservableList<Vertex<VertexData, EdgeData>> vertices = FXCollections.observableArrayList();
    private final ObservableList<Vertex<VertexData, EdgeData>> verticesView = FXCollections.unmodifiableObservableList(vertices);

    public ObservableList<Vertex<VertexData, EdgeData>> vertices(){
        return verticesView;
    }

    public void addVertex(double x, double y){
        var vertex = graph.vertices().add(new VertexData(x, y));
        vertices.add(vertex);
    }
    public void removeVertices(Collection<Vertex<VertexData, EdgeData>> vertices){
        var indices = graph.vertices().removeAll(vertices);
        int i = 0;
        for (Vertex<VertexData, EdgeData> vertex : vertices) {
            this.vertices.remove(indices[i] - i);
            i++;
        }
    }
    public void clearVertices(){
        graph.vertices().clear();
        vertices.clear();
    }
}
