package org.sge.graph;

class Item<VD, ED, D> extends IndexedItem implements HasGraph<VD, ED>{
    Graph<VD, ED> graph;
    final D data;

    Item(Graph<VD, ED> graph, D data) {
        this.data = data;
        this.graph = graph;
    }

    @Override
    public final Graph<VD, ED> graph() {
        return graph;
    }

    public final D data() {
        return data;
    }

    final void resetGraph(){
        graph = null;
    }
}
