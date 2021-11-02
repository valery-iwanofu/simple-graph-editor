package org.sge.graph;

import java.util.Collection;
import java.util.Map;

interface EdgesCleanup<V, E> {
    void cleanupEdges(Map<V, E> map);
}
