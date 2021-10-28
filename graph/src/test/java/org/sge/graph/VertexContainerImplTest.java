package org.sge.graph;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VertexContainerImplTest {
    @Test
    void test__Add(){
        Graph<Object, Object> graph = new Graph<>();
        Vertex<Object, Object> first = graph.vertices().add("First");
        Vertex<Object, Object> second = graph.vertices().add("Second");

        assertEquals(0, first.index());
        assertEquals("First", first.data());
        assertEquals(graph, first.graph());

        assertEquals(1, second.index());
        assertEquals("Second", second.data());
        assertEquals(graph, second.graph());
    }

    @Test
    void testRemoveShouldThrowWhenUsingVertexFromOtherGraph(){
        assertThrows(IllegalArgumentException.class, () -> {
            Graph<Object, Object> mainGraph = new Graph<>();
            Graph<Object, Object> otherGraph = new Graph<>();

            var vertexInMainGraph = mainGraph.vertices.add(null);
            otherGraph.vertices().remove(vertexInMainGraph);
        });
    }
}
