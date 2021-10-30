package org.sge.graph;

import java.util.Collection;
import java.util.Objects;

class ArgumentChecker {
    public static void requireBelongsToGraph(HasGraph<?, ?> item, Graph<?, ?> graph, String nullMessage, String graphsNotMatchMessage){
        Objects.requireNonNull(item, nullMessage);
        if(item.graph() != graph){
            throw new IllegalArgumentException(graphsNotMatchMessage);
        }
    }
    public static void requireBelongsToGraph(Collection<? extends HasGraph<?, ?>> items, Graph<?, ?> graph, String nullMessage, String graphsNotMatchMessage){
        Objects.requireNonNull(items);
        items.forEach(item -> requireBelongsToGraph(item, graph, nullMessage, graphsNotMatchMessage));
    }
}
