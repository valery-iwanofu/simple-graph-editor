package org.sge.graph;

import java.util.Collection;

public interface HasIndex {
    int index();

    static int[] getSortedIndices(Collection<? extends HasIndex> items){
        return items.stream().mapToInt(HasIndex::index).sorted().toArray();
    }
}
