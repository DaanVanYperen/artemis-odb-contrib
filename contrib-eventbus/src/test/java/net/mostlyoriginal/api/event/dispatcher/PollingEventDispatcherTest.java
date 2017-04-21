package net.mostlyoriginal.api.event.dispatcher;

import net.mostlyoriginal.api.event.common.Event;
import net.mostlyoriginal.api.event.common.EventDispatchStrategy;

public class PollingEventDispatcherTest extends AbstractEventDispatcherTest {
	private boolean isProcessing = false;


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
			if (!isProcessing) {
				// prevent call of process() in subsequent event
				isProcessing = true;
				dispatcher.process();
				isProcessing = false;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
