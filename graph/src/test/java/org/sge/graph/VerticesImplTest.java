package org.sge.graph;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sge.graph.api.Vertices;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class VerticesImplTest {
    @Mock
    private EdgesCleanup<Vertex, Object> cleanupEdge;

    @Test
    void testAdd_Simple() {
        var vertices = new VerticesImpl<>(cleanupEdge, new HashMap<>());
        var vertex = new Vertex(0);
        vertices.add(vertex);

        var iterator = vertices.iterator();
        assertTrue(iterator.hasNext());
        assertSame(vertex, iterator.next());
    }

    @Test
    void testAddAll_Simple() {
        var vertices = new VerticesImpl<>(cleanupEdge, new HashMap<>());
        var a = new Vertex(0);
        var b = new Vertex(1);
        vertices.addAll(List.of(a,b ));

        var iterator = vertices.iterator();

        assertTrue(iterator.hasNext());
        assertSame(a, iterator.next());

        assertTrue(iterator.hasNext());
        assertSame(b, iterator.next());

        assertFalse(iterator.hasNext());
    }

    @Test
    void testRemove_Simple() {
        var vertex = new Vertex(0);
        var vertices = new VerticesImpl<>(cleanupEdge, new HashMap<>());
        vertices.add(vertex);
        vertices.remove(vertex);

        var iterator = vertices.iterator();
        assertFalse(iterator.hasNext());
    }

    @Test
    void testRemoveAll_Simple() {
        var a = new Vertex(0);
        var b = new Vertex(1);
        var c = new Vertex(2);
        var vertices = new VerticesImpl<>(cleanupEdge, new HashMap<>());
        vertices.addAll(List.of(a, b, c));
        vertices.removeAll(List.of(a, c));

        var iterator = vertices.iterator();
        assertTrue(iterator.hasNext());
        assertSame(b, iterator.next());

        assertFalse(iterator.hasNext());
    }

    private record Vertex(int id){}
    //private record Edge(){}
}
