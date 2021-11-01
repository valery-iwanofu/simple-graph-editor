package org.sge.graph;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ListenerHelperTest {
    @Test
    void testRemoveListenerWhileInvoke() {
        var listeners = Mockito.mock(Callable.class);

        var listenerToRemove = (Runnable) listeners::call;

        var helper = new ListenerHelper<Runnable>();
        var listenerWhichRemove = (Runnable) () -> {
            helper.remove(listenerToRemove);
        };
        helper.add(listenerWhichRemove);
        helper.add(listenerToRemove);
        helper.each(Runnable::run);

        Mockito.verify(listeners, Mockito.never()).call();
    }

    @Test
    void testAddListenerWhileInvoke() {
        var listeners = Mockito.mock(Callable.class);

        var listenerToAdd = (Runnable) listeners::call;

        var helper = new ListenerHelper<Runnable>();
        var listenerWhichAdd = (Runnable) () -> {
            helper.add(listenerToAdd);
        };
        helper.add(listenerWhichAdd);
        helper.each(Runnable::run);

        Mockito.verify(listeners).call();
    }

    interface Callable {
        void call();
    }
}
