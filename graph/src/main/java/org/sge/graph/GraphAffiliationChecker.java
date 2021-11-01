package org.sge.graph;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Objects;

public final class GraphAffiliationChecker {
    private GraphAffiliationChecker(){}

    public static void requireBelongsToGraph(@NotNull HasGraph<?, ?> item, Graph<?, ?> graph, String graphsNotMatchMessage){
        if(item.graph() != graph){
            throw new IllegalArgumentException(graphsNotMatchMessage);
        }
    }
    public static void requireBelongsToGraph(@NotNull HasGraph<?, ?> item, Graph<?, ?> graph){
        requireBelongsToGraph(item, graph, "item must be belongs to the graph");
    }

    public static void requireBelongsToGraph(@NotNull Collection<? extends HasGraph<?, ?>> items, Graph<?, ?> graph, String graphsNotMatchMessage){
        items.forEach(item -> requireBelongsToGraph(item, graph, graphsNotMatchMessage));
    }
    public static void requireBelongsToGraph(@NotNull Collection<? extends HasGraph<?, ?>> items, Graph<?, ?> graph){
        requireBelongsToGraph(items, graph, "item must be belongs to the graph");
    }
}
