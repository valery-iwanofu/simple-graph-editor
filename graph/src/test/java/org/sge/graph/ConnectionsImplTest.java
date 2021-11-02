package org.sge.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ConnectionsImplTest {
    @Mock
    private EdgeCreator edgeCreator;

    @BeforeEach
    void setupEdgeCreator(){
        Mockito.when(edgeCreator.apply(Mockito.any(Vertex.class), Mockito.any(Vertex.class))).thenCallRealMethod();
    }

    @Test
    void testConnect_Simple() {
        var connections = new ConnectionsImpl<>(new HashMap<>(), edgeCreator);

        Vertex a = new Vertex(0);
        Vertex b = new Vertex(1);

        var edge = connections.connect(a, b);
        assertNotNull(edge);
        var iterator = connections.iterator();
        assertTrue(iterator.hasNext());
        assertSame(edge, iterator.next());

        Mockito.verify(edgeCreator).apply(a, b);
    }

    @Test
    void testDisconnect_Simple() {
        var connections = new ConnectionsImpl<>(new HashMap<>(), Mockito.mock(EdgeCreator.class));
        Vertex a = new Vertex(0);
        Vertex b = new Vertex(1);

        var edge = connections.connect(a, b);
        connections.disconnect(edge);

        var iterator = connections.iterator();
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
