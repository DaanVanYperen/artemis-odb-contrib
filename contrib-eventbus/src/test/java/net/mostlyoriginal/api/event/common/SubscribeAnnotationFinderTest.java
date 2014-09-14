package net.mostlyoriginal.api.event.common;

import net.mostlyoriginal.api.event.common.Event;
import net.mostlyoriginal.api.event.common.EventListener;
import net.mostlyoriginal.api.event.common.Subscribe;
import net.mostlyoriginal.api.event.common.SubscribeAnnotationFinder;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SubscribeAnnotationFinderTest {

    public static class Event2 implements Event {};
    public static class SimpleEntitySystem {
        public int count;
        @Subscribe
        public void testListener(Event event) { }
        @Subscribe
        public void testListener2(Event2 event) { }
        public void notARegisteredListener(Event event) { }
    }

    @Test
    public void FindListeners_MultipleListeners_ResolvesAllListeners() {
        List<EventListener> listeners = new SubscribeAnnotationFinder().resolve(new SimpleEntitySystem());
        assertEquals(2, listeners.size());
    }

    @Test
    public void FindListeners_NoListeners_EmptyList() {
        List<EventListener> listeners = new SubscribeAnnotationFinder().resolve(new Object());
        assertTrue(listeners.isEmpty());
    }
}