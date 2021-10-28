package org.sge.graph;

public class Graph<VD, ED>{
    final VertexContainer<VD, ED> vertices = new VertexContainer<>(this);
    final ConnectionManager<VD, ED> connectionManager = new ConnectionManager<>(this);


    public VertexContainer<VD, ED> vertices() {
        return vertices;
    }

    public ConnectionManager<VD, ED> connectionManager() {
        return connectionManager;
    }
}
