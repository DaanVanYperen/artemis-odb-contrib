package net.mostlyoriginal.api.event.dispatcher;

import net.mostlyoriginal.api.event.common.Event;
import net.mostlyoriginal.api.event.common.EventDispatchStrategy;

public class PollingEventDispatcherTest extends AbstractEventDispatcherTest {
	@Override
	protected EventDispatchStrategy createDispatcherInstance() {
		return new PollingEventDispatcher();
	}
	
	/** Dispatch wrapper. */
	@Override
	protected void dispatch(Event event) {
		try {
			dispatcher.dispatch(event);
			// this dispatcher processes after a world tick.
			dispatcher.process();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
