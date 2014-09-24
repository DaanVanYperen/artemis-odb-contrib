package net.mostlyoriginal.gwt;

import com.google.gwt.junit.client.GWTTestCase;
import net.mostlyoriginal.api.event.common.EventListener;
import net.mostlyoriginal.api.event.common.SubscribeAnnotationFinder;
import net.mostlyoriginal.gwt.system.EmptyTestSystem;
import net.mostlyoriginal.gwt.system.SubscribeAnnotationParameterTestSystem;
import net.mostlyoriginal.gwt.system.SubscribeAnnotationTestSystem;

import java.util.List;

/**
 * Tests @Subscribe annotation under GWT.
 *
 * @author Daan van Yperen
 */
public class SubscribeAnnotationFinderGwtTest extends GWTTestCase {

    @Override
    public String getModuleName() {
        return "net.mostlyoriginal.ContribTest";
    }

	public void test_FindListeners_MultipleListeners_ResolvesAllListeners() {
		List<EventListener> listeners = new SubscribeAnnotationFinder().resolve(new SubscribeAnnotationTestSystem());
		assertEquals(2, listeners.size());
	}

	public void test_FindListeners_SubscribeWithPriorityParameter_ResolvesParameter() {
		List<EventListener> listeners = new SubscribeAnnotationFinder().resolve(new SubscribeAnnotationParameterTestSystem());
		assertEquals(5, listeners.get(0).getPriority());
	}

	public void test_FindListeners_SubscribeWithIgnoreEventParameter_ResolvesParameter() {
		List<EventListener> listeners = new SubscribeAnnotationFinder().resolve(new SubscribeAnnotationParameterTestSystem());
		assertEquals(true, listeners.get(0).isSkipCancelledEvents());
	}

    public void test_FindListeners_NoListeners_EmptyList() {
        List<EventListener> listeners = new SubscribeAnnotationFinder().resolve(new EmptyTestSystem());
        assertTrue(listeners.isEmpty());
    }
}
