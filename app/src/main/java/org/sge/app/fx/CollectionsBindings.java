package org.sge.app.fx;

import javafx.beans.WeakListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public final class CollectionsBindings {
    private CollectionsBindings(){

    }

    public static <T, R> ListChangeListener<T> bindContentWithConverter(ObservableList<T> source, List<R> target, Function<T, R> converter){
        var contentBinding = new ListContentBinding<>(target, converter);
        if(target instanceof ObservableList<R> targetObservableList){
            targetObservableList.setAll(convertAll(source, converter));
        }
        else{
            target.clear();
            target.addAll(convertAll(source, converter));
        }
        source.addListener(contentBinding);
        return contentBinding;
    }

    private static <T, R> Collection<? extends R> convertAll(Collection<? extends T> collection, Function<T, R> converter){
        return collection.stream().map(converter).toList();
    }

    private static class ListContentBinding<T, R> implements ListChangeListener<T>, WeakListener {
        private final WeakReference<List<R>> listRef;
        private final Function<T, R> converter;

        public ListContentBinding(List<R> list, Function<T, R> converter) {
            this.listRef = new WeakReference<>(list);
            this.converter = converter;
        }

        @Override
        public void onChanged(Change<? extends T> change) {
            final List<R> list = listRef.get();
            if (list == null) {
                change.getList().removeListener(this);
            } else {
                while (change.next()) {
                    if (change.wasPermutated()) {
                        list.subList(change.getFrom(), change.getTo()).clear();
                        list.addAll(
                                change.getFrom(),
                                convertAll(change.getList().subList(change.getFrom(), change.getTo()), converter)
                        );
                    } else {
                        if (change.wasRemoved()) {
                            list.subList(change.getFrom(), change.getFrom() + change.getRemovedSize()).clear();
                        }
                        if (change.wasAdded()) {
                            list.addAll(
                                    change.getFrom(),
                                    convertAll(change.getAddedSubList(), converter)
                            );
                        }
                    }
                }
            }
        }

        @Override
        public boolean wasGarbageCollected() {
            return listRef.get() == null;
        }

        @Override
        public int hashCode() {
            final List<R> list = listRef.get();
            return (list == null) ? 0 : list.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            final List<R> list1 = listRef.get();
            if (list1 == null) {
                return false;
            }

            if (obj instanceof final ListContentBinding<?, ?> other) {
                final List<?> list2 = other.listRef.get();
                return list1 == list2;
            }
            return false;
        }
    }
}
