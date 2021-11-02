package org.sge.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GraphImplTest {
    @Mock
    private EdgeCreator edgeCreator;

    @BeforeEach
    void setupEdgeCreator(){
        Mockito.when(edgeCreator.apply(Mockito.any(Vertex.class), Mockito.any(Vertex.class))).thenCallRealMethod();
    }

    @Test
    void testRemoveVerticesAlsoRemoveEdges() {
        GraphImpl<Vertex, Edge> graph = new GraphImpl<>(edgeCreator);

        Vertex a = new Vertex(0);
        Vertex b = new Vertex(1);
        graph.vertices().addAll(List.of(a, b));

        graph.connections().connect(a, b);
        graph.vertices().remove(a);

        var iterator = graph.connections().iterator();
        assertFalse(iterator.hasNext());
    }

    private record Vertex(int id){}
    private record Edge(Vertex a, Vertex b){}

    private interface EdgeCreator extends BiFunction<Vertex, Vertex, Edge> {
        @Override
        default Edge apply(Vertex a, Vertex b){
            return new Edge(a, b);
        }
    }
}
