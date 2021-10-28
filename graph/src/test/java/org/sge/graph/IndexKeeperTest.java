package org.sge.graph;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IndexKeeperTest {
    @Test
    void test__IndicesCorrect__After_Add__Using_Add(){
        IndexKeeper<Item> items = new IndexKeeper<>();
        var first = new Item("First");
        var second = new Item("Second");

        items.add(first);
        assertEquals(0, first.index);
        items.add(second);
        assertEquals(1, second.index);
    }

    @Test
    void test__IndicesCorrect__After_Add__Using_AddAll(){
        IndexKeeper<Item> items = new IndexKeeper<>();
        var first = new Item("First");
        var second = new Item("Second");

        items.addAll(List.of(first, second));
        assertEquals(0, first.index);
        assertEquals(1, second.index);
    }

    @Test
    void test__IndicesCorrect__After_Remove__Using_Remove() {
        IndexKeeper<Item> items = new IndexKeeper<>();
        var first = new Item("First");
        var second = new Item("Second");
        var third = new Item("Third");
        items.addAll(List.of(first, second, third));

        items.remove(second);
        assertEquals(1, third.index);

        items.remove(first);
        assertEquals(0, third.index);
    }

    @Test
    void test__IndicesCorrect__After_Remove__Using_RemoveAll(){
        IndexKeeper<Item> items = new IndexKeeper<>();
        var first = new Item("First");
        var second = new Item("Second");
        var third = new Item("Third");
        var fourth = new Item("Fourth");
        items.addAll(List.of(first, second, third, fourth));

        items.removeAll(List.of(second, third));
        assertEquals(0, first.index);
        assertEquals(1, fourth.index);
    }

    private static final class Item extends IndexedItem{
        public final String value;

        public Item(String value) {
            this.value = value;
        }
    }
}
