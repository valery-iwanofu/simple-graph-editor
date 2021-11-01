package org.sge.graph;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

class ListenerHelper<T> {
    private final List<T> list = new ArrayList<>();
    private boolean inProcess;

    public ListenerHelper() {

    }

    public void add(T item){
        list.add(item);
    }

    public void remove(T item){
        if(inProcess){
            var index = list.indexOf(item);
            if(index != -1){
                list.set(index, null);
            }
        }
        else{
            list.remove(item);
        }
    }

    public void each(Consumer<T> consumer){
        if(inProcess){
            throw new IllegalStateException();
        }
        inProcess = true;
        //noinspection ForLoopReplaceableByForEach
        for (int i = 0; i < list.size(); i++) {
            T item = list.get(i);
            if (item != null) {
                try {
                    consumer.accept(item);
                } catch (Exception ignored) {

                }
            }
        }
        inProcess = false;
    }
}
